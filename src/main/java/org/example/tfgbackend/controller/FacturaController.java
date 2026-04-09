package org.example.tfgbackend.controller;

import org.example.tfgbackend.dto.request.PagoRequest;
import org.example.tfgbackend.dto.response.FacturaResponse;
import org.example.tfgbackend.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired private FacturaService facturaService;

    @GetMapping
    public ResponseEntity<List<FacturaResponse>> getAll() {
        return ResponseEntity.ok(facturaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(facturaService.getById(id));
    }

    @PostMapping("/reparacion/{reparacionId}")
    public ResponseEntity<FacturaResponse> generar(
            @PathVariable Long reparacionId,
            @RequestHeader("X-Firebase-UID") String uid) {
        return ResponseEntity.ok(facturaService.generarDesdeReparacion(reparacionId, uid));
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<FacturaResponse> marcarPagada(
            @PathVariable Long id,
            @RequestBody PagoRequest req) {
        return ResponseEntity.ok(facturaService.marcarPagada(id, req));
    }
}