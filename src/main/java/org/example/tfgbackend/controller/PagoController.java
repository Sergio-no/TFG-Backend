package org.example.tfgbackend.controller;

import jakarta.validation.Valid;
import org.example.tfgbackend.dto.request.PaymentConfirmRequest;
import org.example.tfgbackend.dto.request.PaymentIntentRequest;
import org.example.tfgbackend.dto.response.FacturaResponse;
import org.example.tfgbackend.dto.response.PaymentIntentResponse;
import org.example.tfgbackend.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired private PagoService pagoService;

    @PostMapping("/create-payment-intent")
    public ResponseEntity<PaymentIntentResponse> createPaymentIntent(
            @Valid @RequestBody PaymentIntentRequest req) {
        return ResponseEntity.ok(pagoService.createPaymentIntent(req));
    }

    @PostMapping("/confirmar")
    public ResponseEntity<FacturaResponse> confirmarPago(
            @Valid @RequestBody PaymentConfirmRequest req) {
        return ResponseEntity.ok(pagoService.confirmarPago(req));
    }
}