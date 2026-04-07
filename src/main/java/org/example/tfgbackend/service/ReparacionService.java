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
import java.util.List;

@Service
public class ReparacionService {
    @Autowired private ReparacionRepository      reparacionRepo;
    @Autowired private VehiculoRepository        vehiculoRepo;
    @Autowired private MecanicoRepository        mecanicoRepo;
    @Autowired private CitaRepository            citaRepo;
    @Autowired private PiezaRepository           piezaRepo;
    @Autowired private ReparacionPiezaRepository repPiezaRepo;

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
        r.setCosteTotal(BigDecimal.ZERO);
        return ReparacionMapper.toResponse(reparacionRepo.save(r));
    }

    @Transactional
    public ReparacionResponse cambiarEstado(Long id, String estado) {
        Reparacion r = reparacionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reparación no encontrada"));
        r.setEstado(estado);
        if ("TERMINADA".equals(estado) || "CONFIRMADA".equals(estado))
            r.setFechaFin(LocalDate.now());
        return ReparacionMapper.toResponse(reparacionRepo.save(r));
    }

    @Transactional
    public ReparacionResponse addPieza(Long reparacionId, AddPiezaReparacionRequest req) {
        Reparacion r = reparacionRepo.findById(reparacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Reparación no encontrada"));
        Pieza p = piezaRepo.findById(req.getPiezaId())
                .orElseThrow(() -> new ResourceNotFoundException("Pieza no encontrada"));

        if (p.getStockActual() < req.getCantidadUsada())
            throw new IllegalArgumentException("Stock insuficiente");

        // Descontar stock
        p.setStockActual(p.getStockActual() - req.getCantidadUsada());
        piezaRepo.save(p);

        // Registrar uso
        ReparacionPieza rp = new ReparacionPieza();
        rp.setReparacion(r);
        rp.setPieza(p);
        rp.setCantidadUsada(req.getCantidadUsada());
        rp.setPrecioMomento(p.getPrecioUnitario());
        repPiezaRepo.save(rp);

        // Actualizar coste total
        BigDecimal incremento = p.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(req.getCantidadUsada()));
        r.setCosteTotal(r.getCosteTotal().add(incremento));
        return ReparacionMapper.toResponse(reparacionRepo.save(r));
    }
}
