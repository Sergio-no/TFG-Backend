package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.request.ValoracionRequest;
import org.example.tfgbackend.dto.response.ValoracionResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.model.Cliente;
import org.example.tfgbackend.model.Reparacion;
import org.example.tfgbackend.model.Valoracion;
import org.example.tfgbackend.repository.ClienteRepository;
import org.example.tfgbackend.repository.ReparacionRepository;
import org.example.tfgbackend.repository.ValoracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ValoracionService {
    @Autowired private ValoracionRepository  valoracionRepo;
    @Autowired private ReparacionRepository  reparacionRepo;
    @Autowired private ClienteRepository     clienteRepo;

    public List<ValoracionResponse> getAll() {
        return valoracionRepo.findAll().stream().map(this::toResponse).toList();
    }

    public List<ValoracionResponse> getByCliente(Long clienteId) {
        return valoracionRepo.findByClienteId(clienteId).stream()
                .map(this::toResponse).toList();
    }

    @Transactional
    public ValoracionResponse crear(ValoracionRequest req, String firebaseUid) {
        Cliente cliente = clienteRepo.findByUsuarioFirebaseUid(firebaseUid)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        Reparacion reparacion = reparacionRepo.findById(req.getReparacionId())
                .orElseThrow(() -> new ResourceNotFoundException("Reparación no encontrada"));

        // Verificar que la reparación pertenece al cliente
        if (!reparacion.getVehiculo().getCliente().getId().equals(cliente.getId()))
            throw new IllegalArgumentException("Esta reparación no pertenece al cliente");

        // Verificar que la reparación está terminada
        if (!"TERMINADA".equals(reparacion.getEstado()))
            throw new IllegalArgumentException("Solo se pueden valorar reparaciones terminadas");

        // Verificar que no existe ya una valoración
        if (valoracionRepo.findAll().stream()
                .anyMatch(v -> v.getReparacion().getId().equals(req.getReparacionId())))
            throw new IllegalArgumentException("Esta reparación ya ha sido valorada");

        Valoracion v = new Valoracion();
        v.setCliente(cliente);
        v.setReparacion(reparacion);
        v.setPuntuacion(req.getPuntuacion());
        v.setComentario(req.getComentario());
        return toResponse(valoracionRepo.save(v));
    }

    private ValoracionResponse toResponse(Valoracion v) {
        ValoracionResponse r = new ValoracionResponse();
        r.setId(v.getId());
        r.setClienteNombre(v.getCliente().getUsuario().getNombre()
                + " " + v.getCliente().getUsuario().getApellidos());
        r.setReparacionId(v.getReparacion().getId());
        r.setPuntuacion(v.getPuntuacion());
        r.setComentario(v.getComentario());
        r.setFecha(v.getFecha().toString());
        return r;
    }
}
