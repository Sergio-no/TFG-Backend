package org.example.tfgbackend.controller;

import jakarta.validation.Valid;
import org.example.tfgbackend.dto.request.RegisterRequest;
import org.example.tfgbackend.dto.response.UsuarioResponse;
import org.example.tfgbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(
            @Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.ok(usuarioService.registrar(req));
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> getMe(
            @RequestHeader("X-Firebase-UID") String firebaseUid) {
        return ResponseEntity.ok(usuarioService.getByFirebaseUid(firebaseUid));
    }
}