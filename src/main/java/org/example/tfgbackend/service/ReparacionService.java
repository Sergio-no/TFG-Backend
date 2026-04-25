package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.request.*;
import org.example.tfgbackend.dto.response.ReparacionResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.model.*;
import org.example.tfgbackend.repository.*;
import org.example.tfgbackend.util.ReparacionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReparacionService {
    @Autowired private ReparacionRepository      reparacionRepo;
    @Autowired private VehiculoRepository        vehiculoRepo;
    @Autowired private MecanicoRepository        mecanicoRepo;
    @Autowired private CitaRepository            citaRepo;
    @Autowired private PiezaRepository           piezaRepo;
    @Autowired private ReparacionPiezaRepository repPiezaRepo;
    @Autowired private FacturaRepository         facturaRepo;   // NUEVO

    public List<ReparacionResponse> getAll() {
        return reparacionRepo.findAll().stream()
                .map(ReparacionMapper::toResponse).toList();
    }

    public List<ReparacionResponse> getActivas() {
        return reparacionRepo.findActivas().stream()
                .map(ReparacionMapper::toResponse).toList();
    }

    public ReparacionResponse getById(Long id) {
        return ReparacionMapper.toResponse(reparacionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reparación no encontrada")));
    }

    /** Reparaciones filtradas por clienteId (seguro, sin filtrar por nombre) */
    public List<ReparacionResponse> getByCliente(Long clienteId) {
        return reparacionRepo.findByVehiculoClienteId(clienteId).stream()
                .map(ReparacionMapper::toResponse).toList();
    }

    @Transactional
    public ReparacionResponse crear(ReparacionRequest req) {
        Vehiculo  v = vehiculoRepo.findById(req.getVehiculoId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado"));
        Mecanico  m = mecanicoRepo.findById(req.getMecanicoId())
                .orElseThrow(() -> new ResourceNotFoundException("Mecánico no encontrado"));
        Cita cita = req.getCitaId() != null
                ? citaRepo.findById(req.getCitaId()).orElse(null) : null;

        Reparacion r = new Reparacion();
        r.setVehiculo(v);
        r.setMecanico(m);
        r.setCita(cita);
        r.setFechaInicio(LocalDate.now());
        r.setEstado("EN_PROCESO");

        // NUEVO: usar costeInicial si se proporciona
        if (req.getCosteInicial() != null && req.getCosteInicial().compareTo(BigDecimal.ZERO) > 0) {
            r.setCosteTotal(req.getCosteInicial());
        } else {
            r.setCosteTotal(BigDecimal.ZERO);
        }

        return ReparacionMapper.toResponse(reparacionRepo.save(r));
    }

    @Transactional
    public ReparacionResponse cambiarEstado(Long id, String estado) {
        Reparacion r = reparacionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reparación no encontrada"));
        r.setEstado(estado);
        if ("TERMINADA".equals(estado) || "CONFIRMADA".equals(estado))
            r.setFechaFin(LocalDate.now());

        r = reparacionRepo.save(r);

        // ── NUEVO: auto-crear factura al confirmar ──
        if ("CONFIRMADA".equals(estado)) {
            crearFacturaAutomatica(r);
        }

        return ReparacionMapper.toResponse(r);
    }

    /**
     * Crea una factura automáticamente para la reparación confirmada,
     * siempre y cuando no exista ya una factura para esa reparación.
     */
    private void crearFacturaAutomatica(Reparacion r) {
        boolean yaExiste = facturaRepo.findAll().stream()
                .anyMatch(f -> f.getReparacion().getId().equals(r.getId()));

        if (yaExiste) return;

        String numero = "FAC-" + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM")) + "-" +
                String.format("%04d", facturaRepo.count() + 1);

        Factura f = new Factura();
        f.setCliente(r.getVehiculo().getCliente());
        f.setReparacion(r);
        f.setTotal(r.getCosteTotal());
        f.setNumeroFactura(numero);
        f.setPagada(false);
        // oficina queda null — se puede asignar al pagar
        facturaRepo.save(f);
    }

    @Transactional
    public ReparacionResponse addPieza(Long reparacionId, AddPiezaReparacionRequest req) {
        Reparacion r = reparacionRepo.findById(reparacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Reparación no encontrada"));
        Pieza p = piezaRepo.findById(req.getPiezaId())
                .orElseThrow(() -> new ResourceNotFoundException("Pieza no encontrada"));

        if (p.getStockActual() < req.getCantidadUsada())
            throw new IllegalArgumentException("Stock insuficiente");

        p.setStockActual(p.getStockActual() - req.getCantidadUsada());
        piezaRepo.save(p);

        ReparacionPieza rp = new ReparacionPieza();
        rp.setReparacion(r);
        rp.setPieza(p);
        rp.setCantidadUsada(req.getCantidadUsada());
        rp.setPrecioMomento(p.getPrecioUnitario());
        repPiezaRepo.save(rp);

        BigDecimal incremento = p.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(req.getCantidadUsada()));
        r.setCosteTotal(r.getCosteTotal().add(incremento));
        return ReparacionMapper.toResponse(reparacionRepo.save(r));
    }
}