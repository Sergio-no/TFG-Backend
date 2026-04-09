package org.example.tfgbackend.controller;

import jakarta.validation.Valid;
import org.example.tfgbackend.dto.request.VehiculoRequest;
import org.example.tfgbackend.dto.response.VehiculoResponse;
import org.example.tfgbackend.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    @Autowired private VehiculoService vehiculoService;

    @GetMapping
    public ResponseEntity<List<VehiculoResponse>> getAll() {
        return ResponseEntity.ok(vehiculoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculoService.getById(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<VehiculoResponse>> getByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(vehiculoService.getByCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<VehiculoResponse> crear(@Valid @RequestBody VehiculoRequest req) {
        return ResponseEntity.ok(vehiculoService.crear(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody VehiculoRequest req) {
        return ResponseEntity.ok(vehiculoService.actualizar(id, req));
    }
}