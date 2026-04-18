package org.example.tfgbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.tfgbackend.dto.request.RegisterRequest;
import org.example.tfgbackend.dto.request.UpdateProfileRequest;
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
    public ResponseEntity<UsuarioResponse> me(HttpServletRequest request) {
        // Primero intentar leer del atributo inyectado por FirebaseTokenFilter
        String uid = (String) request.getAttribute("firebaseUid");
        // Fallback: leer del header X-Firebase-UID (usado por Android)
        if (uid == null || uid.isBlank()) {
            uid = request.getHeader("X-Firebase-UID");
        }
        return ResponseEntity.ok(usuarioService.getByFirebaseUid(uid));
    }

    /** Actualizar datos personales del usuario (usado por Android) */
    @PutMapping("/profile")
    public ResponseEntity<UsuarioResponse> updateProfile(
            HttpServletRequest request,
            @Valid @RequestBody UpdateProfileRequest req) {
        // Mismo mecanismo que /me para obtener el UID
        String uid = (String) request.getAttribute("firebaseUid");
        if (uid == null || uid.isBlank()) {
            uid = request.getHeader("X-Firebase-UID");
        }
        return ResponseEntity.ok(usuarioService.updateProfile(uid, req));
    }
}