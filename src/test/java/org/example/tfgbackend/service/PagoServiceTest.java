package org.example.tfgbackend.service;

import org.example.tfgbackend.config.StripeConfig;
import org.example.tfgbackend.dto.request.PaymentConfirmRequest;
import org.example.tfgbackend.dto.request.PaymentIntentRequest;
import org.example.tfgbackend.dto.response.FacturaResponse;
import org.example.tfgbackend.dto.response.PaymentIntentResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.model.*;
import org.example.tfgbackend.repository.ClienteRepository;
import org.example.tfgbackend.repository.FacturaRepository;
import org.example.tfgbackend.repository.PuntosHistorialRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private FacturaRepository mockFacturaRepo;
    @Mock
    private ClienteRepository mockClienteRepo;
    @Mock
    private PuntosHistorialRepository mockPuntosRepo;
    @Mock
    private StripeConfig mockStripeConfig;

    @InjectMocks
    private PagoService pagoServiceUnderTest;

    @Test
    void testCreatePaymentIntent() {
        // Setup
        final PaymentIntentRequest req = new PaymentIntentRequest();
        req.setFacturaId(0L);

        // Configure FacturaRepository.findById(...).
        final Factura factura1 = new Factura();
        factura1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        usuario.setEmail("email");
        cliente.setUsuario(usuario);
        cliente.setPuntosAcumulados(0);
        cliente.setTotalGastado(new BigDecimal("0.00"));
        cliente.setStripeCustomerId("customerId");
        factura1.setCliente(cliente);
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        factura1.setReparacion(reparacion);
        factura1.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura1.setTotal(new BigDecimal("0.00"));
        factura1.setPagada(false);
        factura1.setNumeroFactura("n");
        factura1.setMetodoPago("m");
        factura1.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<Factura> factura = Optional.of(factura1);
        when(mockFacturaRepo.findById(0L)).thenReturn(factura);

        // Configure StripeConfig.getPublishableKey(...).
        final String s = "pk_test_51TOdUjIVtUeNrtx6TniOuMQtHKYlexvpySLpWuZUGuJ19UHVMOoFWZVuXBFFwAUrk5Ba7cFDkWWuT0xFPFh4Ktxq00rICVNP99";
        when(mockStripeConfig.getPublishableKey()).thenReturn(s);

        // Run the test
        final PaymentIntentResponse result = pagoServiceUnderTest.createPaymentIntent(req);

        // Verify the results
        verify(mockClienteRepo).save(any(Cliente.class));
    }

    @Test
    void testCreatePaymentIntent_FacturaRepositoryReturnsAbsent() {
        // Setup
        final PaymentIntentRequest req = new PaymentIntentRequest();
        req.setFacturaId(0L);

        when(mockFacturaRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> pagoServiceUnderTest.createPaymentIntent(req))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testCreatePaymentIntent_ClienteRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final PaymentIntentRequest req = new PaymentIntentRequest();
        req.setFacturaId(0L);

        // Configure FacturaRepository.findById(...).
        final Factura factura1 = new Factura();
        factura1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        usuario.setEmail("email");
        cliente.setUsuario(usuario);
        cliente.setPuntosAcumulados(0);
        cliente.setTotalGastado(new BigDecimal("0.00"));
        cliente.setStripeCustomerId("customerId");
        factura1.setCliente(cliente);
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        factura1.setReparacion(reparacion);
        factura1.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura1.setTotal(new BigDecimal("0.00"));
        factura1.setPagada(false);
        factura1.setNumeroFactura("n");
        factura1.setMetodoPago("m");
        factura1.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<Factura> factura = Optional.of(factura1);
        when(mockFacturaRepo.findById(0L)).thenReturn(factura);

        when(mockClienteRepo.save(any(Cliente.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> pagoServiceUnderTest.createPaymentIntent(req))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testConfirmarPago() {
        // Setup
        final PaymentConfirmRequest req = new PaymentConfirmRequest();
        req.setFacturaId(0L);
        req.setPaymentIntentId("paymentIntentId");

        // Configure FacturaRepository.findById(...).
        final Factura factura1 = new Factura();
        factura1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        usuario.setEmail("email");
        cliente.setUsuario(usuario);
        cliente.setPuntosAcumulados(0);
        cliente.setTotalGastado(new BigDecimal("0.00"));
        cliente.setStripeCustomerId("customerId");
        factura1.setCliente(cliente);
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        factura1.setReparacion(reparacion);
        factura1.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura1.setTotal(new BigDecimal("0.00"));
        factura1.setPagada(false);
        factura1.setNumeroFactura("n");
        factura1.setMetodoPago("m");
        factura1.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<Factura> factura = Optional.of(factura1);
        when(mockFacturaRepo.findById(0L)).thenReturn(factura);

        // Configure FacturaRepository.save(...).
        final Factura factura2 = new Factura();
        factura2.setId(0L);
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        usuario1.setEmail("email");
        cliente1.setUsuario(usuario1);
        cliente1.setPuntosAcumulados(0);
        cliente1.setTotalGastado(new BigDecimal("0.00"));
        cliente1.setStripeCustomerId("customerId");
        factura2.setCliente(cliente1);
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        factura2.setReparacion(reparacion1);
        factura2.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura2.setTotal(new BigDecimal("0.00"));
        factura2.setPagada(false);
        factura2.setNumeroFactura("n");
        factura2.setMetodoPago("m");
        factura2.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockFacturaRepo.save(any(Factura.class))).thenReturn(factura2);

        // Run the test
        final FacturaResponse result = pagoServiceUnderTest.confirmarPago(req);

        // Verify the results
        verify(mockClienteRepo).save(any(Cliente.class));
        verify(mockPuntosRepo).save(any(PuntosHistorial.class));
    }

    @Test
    void testConfirmarPago_FacturaRepositoryFindByIdReturnsAbsent() {
        // Setup
        final PaymentConfirmRequest req = new PaymentConfirmRequest();
        req.setFacturaId(0L);
        req.setPaymentIntentId("paymentIntentId");

        when(mockFacturaRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> pagoServiceUnderTest.confirmarPago(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testConfirmarPago_FacturaRepositorySaveThrowsOptimisticLockingFailureException() {
        // Setup
        final PaymentConfirmRequest req = new PaymentConfirmRequest();
        req.setFacturaId(0L);
        req.setPaymentIntentId("paymentIntentId");

        // Configure FacturaRepository.findById(...).
        final Factura factura1 = new Factura();
        factura1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        usuario.setEmail("email");
        cliente.setUsuario(usuario);
        cliente.setPuntosAcumulados(0);
        cliente.setTotalGastado(new BigDecimal("0.00"));
        cliente.setStripeCustomerId("customerId");
        factura1.setCliente(cliente);
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        factura1.setReparacion(reparacion);
        factura1.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura1.setTotal(new BigDecimal("0.00"));
        factura1.setPagada(false);
        factura1.setNumeroFactura("n");
        factura1.setMetodoPago("m");
        factura1.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<Factura> factura = Optional.of(factura1);
        when(mockFacturaRepo.findById(0L)).thenReturn(factura);

        when(mockFacturaRepo.save(any(Factura.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> pagoServiceUnderTest.confirmarPago(req))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testConfirmarPago_ClienteRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final PaymentConfirmRequest req = new PaymentConfirmRequest();
        req.setFacturaId(0L);
        req.setPaymentIntentId("paymentIntentId");

        // Configure FacturaRepository.findById(...).
        final Factura factura1 = new Factura();
        factura1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        usuario.setEmail("email");
        cliente.setUsuario(usuario);
        cliente.setPuntosAcumulados(0);
        cliente.setTotalGastado(new BigDecimal("0.00"));
        cliente.setStripeCustomerId("customerId");
        factura1.setCliente(cliente);
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        factura1.setReparacion(reparacion);
        factura1.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura1.setTotal(new BigDecimal("0.00"));
        factura1.setPagada(false);
        factura1.setNumeroFactura("n");
        factura1.setMetodoPago("m");
        factura1.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<Factura> factura = Optional.of(factura1);
        when(mockFacturaRepo.findById(0L)).thenReturn(factura);

        // Configure FacturaRepository.save(...).
        final Factura factura2 = new Factura();
        factura2.setId(0L);
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        usuario1.setEmail("email");
        cliente1.setUsuario(usuario1);
        cliente1.setPuntosAcumulados(0);
        cliente1.setTotalGastado(new BigDecimal("0.00"));
        cliente1.setStripeCustomerId("customerId");
        factura2.setCliente(cliente1);
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        factura2.setReparacion(reparacion1);
        factura2.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura2.setTotal(new BigDecimal("0.00"));
        factura2.setPagada(false);
        factura2.setNumeroFactura("n");
        factura2.setMetodoPago("m");
        factura2.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockFacturaRepo.save(any(Factura.class))).thenReturn(factura2);

        when(mockClienteRepo.save(any(Cliente.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> pagoServiceUnderTest.confirmarPago(req))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testConfirmarPago_PuntosHistorialRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final PaymentConfirmRequest req = new PaymentConfirmRequest();
        req.setFacturaId(0L);
        req.setPaymentIntentId("paymentIntentId");

        // Configure FacturaRepository.findById(...).
        final Factura factura1 = new Factura();
        factura1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        usuario.setEmail("email");
        cliente.setUsuario(usuario);
        cliente.setPuntosAcumulados(0);
        cliente.setTotalGastado(new BigDecimal("0.00"));
        cliente.setStripeCustomerId("customerId");
        factura1.setCliente(cliente);
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        factura1.setReparacion(reparacion);
        factura1.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura1.setTotal(new BigDecimal("0.00"));
        factura1.setPagada(false);
        factura1.setNumeroFactura("n");
        factura1.setMetodoPago("m");
        factura1.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<Factura> factura = Optional.of(factura1);
        when(mockFacturaRepo.findById(0L)).thenReturn(factura);

        // Configure FacturaRepository.save(...).
        final Factura factura2 = new Factura();
        factura2.setId(0L);
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        usuario1.setEmail("email");
        cliente1.setUsuario(usuario1);
        cliente1.setPuntosAcumulados(0);
        cliente1.setTotalGastado(new BigDecimal("0.00"));
        cliente1.setStripeCustomerId("customerId");
        factura2.setCliente(cliente1);
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        factura2.setReparacion(reparacion1);
        factura2.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura2.setTotal(new BigDecimal("0.00"));
        factura2.setPagada(false);
        factura2.setNumeroFactura("n");
        factura2.setMetodoPago("m");
        factura2.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockFacturaRepo.save(any(Factura.class))).thenReturn(factura2);

        when(mockPuntosRepo.save(any(PuntosHistorial.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> pagoServiceUnderTest.confirmarPago(req))
                .isInstanceOf(OptimisticLockingFailureException.class);
        verify(mockClienteRepo).save(any(Cliente.class));
    }
}
