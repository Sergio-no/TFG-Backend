package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.request.CitaRequest;
import org.example.tfgbackend.dto.response.CitaResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.model.*;
import org.example.tfgbackend.repository.*;
import org.example.tfgbackend.util.CitaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaService {
    @Autowired private CitaRepository     citaRepo;
    @Autowired private ClienteRepository  clienteRepo;
    @Autowired private VehiculoRepository vehiculoRepo;

    public List<CitaResponse> getAll() {
        return citaRepo.findAll().stream().map(CitaMapper::toResponse).toList();
    }

    public List<CitaResponse> getCitasHoy() {
        LocalDateTime inicio = LocalDate.now().atStartOfDay();
        LocalDateTime fin    = inicio.plusDays(1).minusSeconds(1);
        return citaRepo.findCitasHoy(inicio, fin).stream()
                .map(CitaMapper::toResponse).toList();
    }

    public List<CitaResponse> getByCliente(Long clienteId) {
        return citaRepo.findByClienteId(clienteId).stream()
                .map(CitaMapper::toResponse).toList();
    }

    public CitaResponse getById(Long id) {
        return CitaMapper.toResponse(citaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada")));
    }

    @Transactional
    public CitaResponse crear(CitaRequest req) {
        Cliente  cliente  = clienteRepo.findById(req.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        Vehiculo vehiculo = vehiculoRepo.findById(req.getVehiculoId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado"));

        Cita cita = new Cita();
        cita.setCliente(cliente);
        cita.setVehiculo(vehiculo);
        cita.setFecha(req.getFecha());
        cita.setDescripcion(req.getDescripcion());
        cita.setEstado("PENDIENTE");
        return CitaMapper.toResponse(citaRepo.save(cita));
    }

    @Transactional
    public CitaResponse cambiarEstado(Long id, String nuevoEstado) {
        Cita cita = citaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));
        cita.setEstado(nuevoEstado);
        return CitaMapper.toResponse(citaRepo.save(cita));
    }
}
