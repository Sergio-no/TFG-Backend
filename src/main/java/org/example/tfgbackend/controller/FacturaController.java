package org.example.tfgbackend.controller;

import org.example.tfgbackend.dto.request.PagoRequest;
import org.example.tfgbackend.dto.response.FacturaResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.model.Factura;
import org.example.tfgbackend.repository.FacturaRepository;
import org.example.tfgbackend.service.FacturaPdfService;
import org.example.tfgbackend.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired private FacturaService facturaService;
    @Autowired private FacturaPdfService facturaPdfService;
    @Autowired private FacturaRepository facturaRepo;

    @GetMapping
    public ResponseEntity<List<FacturaResponse>> getAll() {
        return ResponseEntity.ok(facturaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(facturaService.getById(id));
    }

    /**
     * Descarga la factura como PDF.
     * GET /api/facturas/{id}/pdf
     */
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> descargarPdf(@PathVariable Long id) {
        Factura factura = facturaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada"));

        byte[] pdf = facturaPdfService.generarPdf(factura);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename(factura.getNumeroFactura() + ".pdf")
                        .build());

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
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

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<FacturaResponse>> getByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(facturaService.getByClienteId(clienteId));
    }
}
