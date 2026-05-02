package org.example.tfgbackend.controller;

import jakarta.validation.Valid;
import org.example.tfgbackend.dto.request.CitaRequest;
import org.example.tfgbackend.dto.response.CitaResponse;
import org.example.tfgbackend.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/semana")
    public ResponseEntity<List<CitaResponse>> getCitasSemana() {
        return ResponseEntity.ok(citaService.getCitasSemana());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.getById(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CitaResponse>> getByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(citaService.getByCliente(clienteId));
    }

    /**
     * Devuelve las horas disponibles para un día concreto.
     * Ejemplo: GET /api/citas/horas-disponibles?fecha=2026-04-10
     */
    @GetMapping("/horas-disponibles")
    public ResponseEntity<List<String>> getHorasDisponibles(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(citaService.getHorasDisponibles(fecha));
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
