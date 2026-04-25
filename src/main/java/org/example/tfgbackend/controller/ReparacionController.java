package org.example.tfgbackend.controller;

import jakarta.validation.Valid;
import org.example.tfgbackend.dto.request.*;
import org.example.tfgbackend.dto.response.ReparacionResponse;
import org.example.tfgbackend.service.ReparacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reparaciones")
public class ReparacionController {

    @Autowired private ReparacionService reparacionService;

    @GetMapping
    public ResponseEntity<List<ReparacionResponse>> getAll() {
        return ResponseEntity.ok(reparacionService.getAll());
    }

    @GetMapping("/activas")
    public ResponseEntity<List<ReparacionResponse>> getActivas() {
        return ResponseEntity.ok(reparacionService.getActivas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReparacionResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reparacionService.getById(id));
    }

    /** Reparaciones de un cliente concreto (usado por Android) */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ReparacionResponse>> getByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(reparacionService.getByCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<ReparacionResponse> crear(
            @Valid @RequestBody ReparacionRequest req) {
        return ResponseEntity.ok(reparacionService.crear(req));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ReparacionResponse> cambiarEstado(
            @PathVariable Long id,
            @RequestBody EstadoRequest req) {
        return ResponseEntity.ok(reparacionService.cambiarEstado(id, req.getEstado()));
    }

    @PostMapping("/{id}/piezas")
    public ResponseEntity<ReparacionResponse> addPieza(
            @PathVariable Long id,
            @Valid @RequestBody AddPiezaReparacionRequest req) {
        return ResponseEntity.ok(reparacionService.addPieza(id, req));
    }

    /** Cliente acepta la reparación presentada (Android) */
    @PutMapping("/{id}/aceptar")
    public ResponseEntity<ReparacionResponse> aceptar(@PathVariable Long id) {
        return ResponseEntity.ok(reparacionService.cambiarEstado(id, "EN_PROCESO"));
    }

    /** Cliente rechaza la reparación presentada (Android) */
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<ReparacionResponse> rechazar(@PathVariable Long id) {
        return ResponseEntity.ok(reparacionService.cambiarEstado(id, "RECHAZADA"));
    }
}