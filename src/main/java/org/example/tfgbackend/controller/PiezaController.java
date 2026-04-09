package org.example.tfgbackend.controller;

import jakarta.validation.Valid;
import org.example.tfgbackend.dto.request.*;
import org.example.tfgbackend.dto.response.PiezaResponse;
import org.example.tfgbackend.service.PiezaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/piezas")
public class PiezaController {

    @Autowired private PiezaService piezaService;

    @GetMapping
    public ResponseEntity<List<PiezaResponse>> getAll() {
        return ResponseEntity.ok(piezaService.getAll());
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<List<PiezaResponse>> getStockBajo() {
        return ResponseEntity.ok(piezaService.getStockBajo());
    }

    @PostMapping
    public ResponseEntity<PiezaResponse> crear(@Valid @RequestBody PiezaRequest req) {
        return ResponseEntity.ok(piezaService.crear(req));
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<PiezaResponse> ajustarStock(
            @PathVariable Long id,
            @RequestBody AjusteStockRequest req) {
        return ResponseEntity.ok(piezaService.ajustarStock(id, req));
    }
}