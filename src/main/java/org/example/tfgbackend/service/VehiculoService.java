package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.request.VehiculoRequest;
import org.example.tfgbackend.dto.response.VehiculoResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.model.Cliente;
import org.example.tfgbackend.model.Vehiculo;
import org.example.tfgbackend.repository.ClienteRepository;
import org.example.tfgbackend.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehiculoService {
    @Autowired private VehiculoRepository vehiculoRepo;
    @Autowired private ClienteRepository  clienteRepo;

    public List<VehiculoResponse> getAll() {
        return vehiculoRepo.findAll().stream().map(this::toResponse).toList();
    }

    public List<VehiculoResponse> getByCliente(Long clienteId) {
        return vehiculoRepo.findByClienteId(clienteId).stream()
                .map(this::toResponse).toList();
    }

    public VehiculoResponse getById(Long id) {
        return toResponse(vehiculoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado")));
    }

    // ── NUEVO: buscar por matrícula ──
    public VehiculoResponse getByMatricula(String matricula) {
        return toResponse(vehiculoRepo.findByMatricula(matricula.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró vehículo con matrícula: " + matricula)));
    }

    @Transactional
    public VehiculoResponse crear(VehiculoRequest req) {
        Cliente cliente = clienteRepo.findById(req.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        if (vehiculoRepo.findByMatricula(req.getMatricula()).isPresent())
            throw new IllegalArgumentException("Ya existe un vehículo con esa matrícula");

        Vehiculo v = new Vehiculo();
        v.setCliente(cliente);
        v.setMarca(req.getMarca());
        v.setModelo(req.getModelo());
        v.setAnio(req.getAnio());
        v.setMatricula(req.getMatricula().toUpperCase());
        v.setKilometraje(req.getKilometraje());
        return toResponse(vehiculoRepo.save(v));
    }

    @Transactional
    public VehiculoResponse actualizar(Long id, VehiculoRequest req) {
        Vehiculo v = vehiculoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado"));
        v.setMarca(req.getMarca());
        v.setModelo(req.getModelo());
        v.setAnio(req.getAnio());
        v.setKilometraje(req.getKilometraje());
        return toResponse(vehiculoRepo.save(v));
    }

    private VehiculoResponse toResponse(Vehiculo v) {
        VehiculoResponse r = new VehiculoResponse();
        r.setId(v.getId());
        r.setClienteId(v.getCliente().getId());
        r.setClienteNombre(v.getCliente().getUsuario().getNombre()
                + " " + v.getCliente().getUsuario().getApellidos());
        r.setMarca(v.getMarca());
        r.setModelo(v.getModelo());
        r.setAnio(v.getAnio());
        r.setMatricula(v.getMatricula());
        r.setKilometraje(v.getKilometraje());
        return r;
    }
}
