package org.example.tfgbackend.controller;

import jakarta.validation.Valid;
import org.example.tfgbackend.dto.request.CitaRequest;
import org.example.tfgbackend.dto.response.CitaResponse;
import org.example.tfgbackend.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired private CitaService citaService;

    @GetMapping
    public ResponseEntity<List<CitaResponse>> getAll() {
        return ResponseEntity.ok(citaService.getAll());
    }

    @GetMapping("/hoy")
    public ResponseEntity<List<CitaResponse>> getCitasHoy() {
        return ResponseEntity.ok(citaService.getCitasHoy());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.getById(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CitaResponse>> getByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(citaService.getByCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<CitaResponse> crear(@Valid @RequestBody CitaRequest req) {
        return ResponseEntity.ok(citaService.crear(req));
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<CitaResponse> confirmar(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.cambiarEstado(id, "CONFIRMADA"));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<CitaResponse> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.cambiarEstado(id, "CANCELADA"));
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<CitaResponse> finalizar(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.cambiarEstado(id, "FINALIZADA"));
    }
}