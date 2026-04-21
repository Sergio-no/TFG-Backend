package org.example.tfgbackend.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.EphemeralKey;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.EphemeralKeyCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.example.tfgbackend.config.StripeConfig;
import org.example.tfgbackend.dto.request.PaymentConfirmRequest;
import org.example.tfgbackend.dto.request.PaymentIntentRequest;
import org.example.tfgbackend.dto.response.FacturaResponse;
import org.example.tfgbackend.dto.response.PaymentIntentResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.model.Cliente;
import org.example.tfgbackend.model.Factura;
import org.example.tfgbackend.model.PuntosHistorial;
import org.example.tfgbackend.repository.ClienteRepository;
import org.example.tfgbackend.repository.FacturaRepository;
import org.example.tfgbackend.repository.PuntosHistorialRepository;
import org.example.tfgbackend.util.FacturaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class PagoService {

    @Autowired private FacturaRepository facturaRepo;
    @Autowired private ClienteRepository clienteRepo;
    @Autowired private PuntosHistorialRepository puntosRepo;
    @Autowired private StripeConfig stripeConfig;

    public PaymentIntentResponse createPaymentIntent(PaymentIntentRequest req) {
        // 1. Buscar factura
        Factura factura = facturaRepo.findById(req.getFacturaId())
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada"));

        if (factura.isPagada()) {
            throw new IllegalStateException("Esta factura ya está pagada");
        }

        Cliente cliente = factura.getCliente();

        try {
            // 2. Crear o recuperar Stripe Customer
            String stripeCustomerId = cliente.getStripeCustomerId();
            if (stripeCustomerId == null || stripeCustomerId.isBlank()) {
                CustomerCreateParams customerParams = CustomerCreateParams.builder()
                        .setEmail(cliente.getUsuario().getEmail())
                        .setName(cliente.getUsuario().getNombre()
                                + " " + cliente.getUsuario().getApellidos())
                        .putMetadata("clienteId", String.valueOf(cliente.getId()))
                        .build();

                Customer customer = Customer.create(customerParams);
                stripeCustomerId = customer.getId();

                // Guardar el ID de Stripe en la BD
                cliente.setStripeCustomerId(stripeCustomerId);
                clienteRepo.save(cliente);
            }

            // 3. Crear EphemeralKey
            EphemeralKeyCreateParams ekParams = EphemeralKeyCreateParams.builder()
                    .setCustomer(stripeCustomerId)
                    .setStripeVersion("2024-04-10")
                    .build();
            EphemeralKey ephemeralKey = EphemeralKey.create(ekParams);

            // 4. Crear PaymentIntent
            // Convertir euros a céntimos: 150.50 € → 15050
            long amountCents = factura.getTotal()
                    .multiply(BigDecimal.valueOf(100))
                    .longValue();

            PaymentIntentCreateParams piParams = PaymentIntentCreateParams.builder()
                    .setAmount(amountCents)
                    .setCurrency("eur")
                    .setCustomer(stripeCustomerId)
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                    .setEnabled(true)
                                    .build()
                    )
                    .putMetadata("factura_id", String.valueOf(factura.getId()))
                    .putMetadata("numero_factura", factura.getNumeroFactura())
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(piParams);

            // 5. Devolver respuesta
            return new PaymentIntentResponse(
                    paymentIntent.getClientSecret(),
                    ephemeralKey.getSecret(),
                    stripeCustomerId,
                    stripeConfig.getPublishableKey()
            );

        } catch (StripeException e) {
            throw new RuntimeException("Error con Stripe: " + e.getMessage(), e);
        }
    }


    @Transactional
    public FacturaResponse confirmarPago(PaymentConfirmRequest req) {
        Factura factura = facturaRepo.findById(req.getFacturaId())
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada"));

        if (factura.isPagada()) {
            // Ya está pagada (quizás el webhook llegó antes), devolver sin error
            return FacturaMapper.toResponse(factura);
        }

        // Marcar como pagada
        factura.setPagada(true);
        factura.setMetodoPago("STRIPE");
        factura.setFechaPago(LocalDateTime.now());
        factura = facturaRepo.save(factura);

        // Actualizar total gastado del cliente
        Cliente cliente = factura.getCliente();
        cliente.setTotalGastado(cliente.getTotalGastado().add(factura.getTotal()));

        // Acumular puntos: 1 punto por cada euro
        int puntos = factura.getTotal().intValue();
        cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() + puntos);
        clienteRepo.save(cliente);

        // Registrar en historial de puntos
        PuntosHistorial ph = new PuntosHistorial();
        ph.setCliente(cliente);
        ph.setFactura(factura);
        ph.setPuntos(puntos);
        ph.setConcepto("Pago online factura " + factura.getNumeroFactura());
        puntosRepo.save(ph);

        return FacturaMapper.toResponse(factura);
    }
}