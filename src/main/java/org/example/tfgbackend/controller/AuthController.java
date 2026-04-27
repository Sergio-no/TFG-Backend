package org.example.tfgbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.tfgbackend.dto.request.FcmTokenRequest;
import org.example.tfgbackend.dto.request.RegisterRequest;
import org.example.tfgbackend.dto.request.UpdateProfileRequest;
import org.example.tfgbackend.dto.response.UsuarioResponse;
import org.example.tfgbackend.model.Usuario;
import org.example.tfgbackend.repository.UsuarioRepository;
import org.example.tfgbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private UsuarioService usuarioService;
    @Autowired private UsuarioRepository usuarioRepo;

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(
            @Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.ok(usuarioService.registrar(req));
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> me(HttpServletRequest request) {
        String uid = (String) request.getAttribute("firebaseUid");
        if (uid == null || uid.isBlank()) {
            uid = request.getHeader("X-Firebase-UID");
        }
        return ResponseEntity.ok(usuarioService.getByFirebaseUid(uid));
    }

    @PutMapping("/profile")
    public ResponseEntity<UsuarioResponse> updateProfile(
            HttpServletRequest request,
            @Valid @RequestBody UpdateProfileRequest req) {
        String uid = (String) request.getAttribute("firebaseUid");
        if (uid == null || uid.isBlank()) {
            uid = request.getHeader("X-Firebase-UID");
        }
        return ResponseEntity.ok(usuarioService.updateProfile(uid, req));
    }

    @PutMapping("/fcm-token")
    public ResponseEntity<Map<String, String>> registrarFcmToken(
            @RequestHeader("X-Firebase-UID") String firebaseUid,
            @Valid @RequestBody FcmTokenRequest req) {

        Usuario usuario = usuarioRepo.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setFcmToken(req.getFcmToken());
        usuarioRepo.save(usuario);

        System.out.println("FCM: Token registrado para usuario " + usuario.getId());

        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}