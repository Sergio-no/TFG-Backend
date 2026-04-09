package org.example.tfgbackend.controller;

import jakarta.validation.Valid;
import org.example.tfgbackend.dto.request.ValoracionRequest;
import org.example.tfgbackend.dto.response.ValoracionResponse;
import org.example.tfgbackend.service.ValoracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/valoraciones")
public class ValoracionController {

    @Autowired private ValoracionService valoracionService;

    @GetMapping
    public ResponseEntity<List<ValoracionResponse>> getAll() {
        return ResponseEntity.ok(valoracionService.getAll());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ValoracionResponse>> getByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(valoracionService.getByCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<ValoracionResponse> crear(
            @Valid @RequestBody ValoracionRequest req,
            @RequestHeader("X-Firebase-UID") String uid) {
        return ResponseEntity.ok(valoracionService.crear(req, uid));
    }
}
