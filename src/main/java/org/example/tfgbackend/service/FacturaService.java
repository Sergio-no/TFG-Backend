package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.request.PagoRequest;
import org.example.tfgbackend.dto.response.FacturaResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.model.*;
import org.example.tfgbackend.repository.*;
import org.example.tfgbackend.util.FacturaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FacturaService {
    @Autowired private FacturaRepository  facturaRepo;
    @Autowired private ReparacionRepository reparacionRepo;
    @Autowired private ClienteRepository  clienteRepo;
    @Autowired private OficinaRepository  oficinaRepo;
    @Autowired private PuntosHistorialRepository puntosRepo;

    public List<FacturaResponse> getAll() {
        return facturaRepo.findAll().stream().map(FacturaMapper::toResponse).toList();
    }

    public FacturaResponse getById(Long id) {
        return FacturaMapper.toResponse(facturaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada")));
    }

    @Transactional
    public FacturaResponse generarDesdeReparacion(Long reparacionId, String firebaseUid) {
        Reparacion r = reparacionRepo.findById(reparacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Reparación no encontrada"));

        if (facturaRepo.findAll().stream()
                .anyMatch(f -> f.getReparacion().getId().equals(reparacionId)))
            throw new IllegalStateException("Ya existe factura para esta reparación");

        Oficina oficina = oficinaRepo.findByUsuarioFirebaseUid(firebaseUid).orElse(null);

        String numero = "FAC-" + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM")) + "-" +
                String.format("%04d", facturaRepo.count() + 1);

        Factura f = new Factura();
        f.setCliente(r.getVehiculo().getCliente());
        f.setReparacion(r);
        f.setOficina(oficina);
        f.setTotal(r.getCosteTotal());
        f.setNumeroFactura(numero);
        f.setPagada(false);
        return FacturaMapper.toResponse(facturaRepo.save(f));
    }

    @Transactional
    public FacturaResponse marcarPagada(Long id, PagoRequest req) {
        Factura f = facturaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada"));
        if (f.isPagada()) throw new IllegalStateException("Factura ya pagada");

        f.setPagada(true);
        f.setMetodoPago(req.getMetodoPago());
        f.setFechaPago(LocalDateTime.now());
        f = facturaRepo.save(f);

        // Actualizar total gastado del cliente
        Cliente cliente = f.getCliente();
        cliente.setTotalGastado(cliente.getTotalGastado().add(f.getTotal()));

        // Acumular puntos: 1 punto por cada euro
        int puntos = f.getTotal().intValue();
        cliente.setPuntosAcumulados(cliente.getPuntosAcumulados() + puntos);
        clienteRepo.save(cliente);

        // Registrar en historial de puntos
        PuntosHistorial ph = new PuntosHistorial();
        ph.setCliente(cliente);
        ph.setFactura(f);
        ph.setPuntos(puntos);
        ph.setConcepto("Pago factura " + f.getNumeroFactura());
        puntosRepo.save(ph);

        return FacturaMapper.toResponse(f);
    }
}
