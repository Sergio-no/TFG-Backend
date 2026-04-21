package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.request.CanjearRecompensaRequest;
import org.example.tfgbackend.dto.response.CanjearRecompensaResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.model.Cliente;
import org.example.tfgbackend.model.PuntosHistorial;
import org.example.tfgbackend.repository.ClienteRepository;
import org.example.tfgbackend.repository.PuntosHistorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CrmService {

    @Autowired private ClienteRepository clienteRepo;
    @Autowired private PuntosHistorialRepository puntosRepo;

    @Transactional
    public CanjearRecompensaResponse canjear(CanjearRecompensaRequest req) {
        // 1. Buscar cliente
        Cliente cliente = clienteRepo.findById(req.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        // 2. Verificar puntos suficientes
        if (cliente.getPuntosAcumulados() < req.getPuntosRequeridos()) {
            return new CanjearRecompensaResponse(
                    false,
                    cliente.getPuntosAcumulados(),
                    "Puntos insuficientes. Tienes " + cliente.getPuntosAcumulados()
                            + " pero necesitas " + req.getPuntosRequeridos()
            );
        }

        // 3. Descontar puntos
        cliente.setPuntosAcumulados(
                cliente.getPuntosAcumulados() - req.getPuntosRequeridos()
        );
        clienteRepo.save(cliente);

        // 4. Registrar en historial (puntos negativos = canjeo)
        PuntosHistorial ph = new PuntosHistorial();
        ph.setCliente(cliente);
        ph.setFactura(null); // No hay factura asociada a un canjeo
        ph.setPuntos(-req.getPuntosRequeridos());
        ph.setConcepto("Canjeo: " + req.getRecompensa());
        puntosRepo.save(ph);

        // 5. Respuesta
        return new CanjearRecompensaResponse(
                true,
                cliente.getPuntosAcumulados(),
                "¡" + req.getRecompensa() + " canjeada con éxito! "
                        + "Recibirás un email con tu código de descuento."
        );
    }
}