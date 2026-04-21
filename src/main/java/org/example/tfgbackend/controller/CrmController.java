package org.example.tfgbackend.controller;

import jakarta.validation.Valid;
import org.example.tfgbackend.dto.request.CanjearRecompensaRequest;
import org.example.tfgbackend.dto.response.CanjearRecompensaResponse;
import org.example.tfgbackend.service.CrmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crm")
public class CrmController {

    @Autowired private CrmService crmService;

     //Canjea una recompensa del programa de puntos.
     //Descuenta los puntos del cliente y registra el canjeo
    @PostMapping("/canjear")
    public ResponseEntity<CanjearRecompensaResponse> canjear(
            @Valid @RequestBody CanjearRecompensaRequest req) {
        CanjearRecompensaResponse resp = crmService.canjear(req);
        if (resp.isExito()) {
            return ResponseEntity.ok(resp);
        } else {
            return ResponseEntity.badRequest().body(resp);
        }
    }
}