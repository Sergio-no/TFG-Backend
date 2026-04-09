package org.example.tfgbackend.controller;

import jakarta.validation.Valid;
import org.example.tfgbackend.dto.request.MecanicoRequest;
import org.example.tfgbackend.dto.response.MecanicoResponse;
import org.example.tfgbackend.service.MecanicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mecanicos")
public class MecanicoController {

    @Autowired private MecanicoService mecanicoService;

    @GetMapping
    public ResponseEntity<List<MecanicoResponse>> getAll() {
        return ResponseEntity.ok(mecanicoService.getAll());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<MecanicoResponse>> getActivos() {
        return ResponseEntity.ok(mecanicoService.getActivos());
    }

    @PostMapping
    public ResponseEntity<MecanicoResponse> crear(
            @Valid @RequestBody MecanicoRequest req) {
        return ResponseEntity.ok(mecanicoService.crear(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MecanicoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MecanicoRequest req) {
        return ResponseEntity.ok(mecanicoService.actualizar(id, req));
    }

    @PutMapping("/{id}/toggle")
    public ResponseEntity<MecanicoResponse> toggleActivo(@PathVariable Long id) {
        return ResponseEntity.ok(mecanicoService.toggleActivo(id));
    }
}