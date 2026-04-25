package org.example.tfgbackend.controller;

import org.example.tfgbackend.dto.response.ClienteResponse;
import org.example.tfgbackend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> getAll() {
        return ResponseEntity.ok(clienteService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.getById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<ClienteResponse> getMiCliente(
            @RequestHeader("X-Firebase-UID") String firebaseUid) {
        return ResponseEntity.ok(clienteService.getByFirebaseUid(firebaseUid));
    }
}