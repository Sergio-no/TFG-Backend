package org.example.tfgbackend.controller;

import org.example.tfgbackend.dto.response.EstadisticasResponse;
import org.example.tfgbackend.service.EstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {

    @Autowired private EstadisticasService estadisticasService;

    @GetMapping
    public ResponseEntity<EstadisticasResponse> getEstadisticas() {
        return ResponseEntity.ok(estadisticasService.getEstadisticas());
    }
}