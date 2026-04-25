package org.example.tfgbackend.controller;

import org.example.tfgbackend.dto.response.UsuarioResponse;
import org.example.tfgbackend.model.Usuario;
import org.example.tfgbackend.repository.UsuarioRepository;
import org.example.tfgbackend.util.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired private UsuarioRepository usuarioRepo;

    /**
     * Lista todos los usuarios con rol OFICINA o JEFE (empleados del taller).
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> getAll() {
        List<UsuarioResponse> empleados = usuarioRepo.findAll().stream()
                .filter(u -> "OFICINA".equals(u.getRol()) || "JEFE".equals(u.getRol()))
                .filter(Usuario::isActivo)
                .map(UsuarioMapper::toResponse)
                .toList();
        return ResponseEntity.ok(empleados);
    }

    /**
     * Desactivar un empleado (soft delete).
     */
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<UsuarioResponse> desactivar(@PathVariable Long id) {
        Usuario u = usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        u.setActivo(false);
        return ResponseEntity.ok(UsuarioMapper.toResponse(usuarioRepo.save(u)));
    }

    /**
     * Reactivar un empleado.
     */
    @PutMapping("/{id}/activar")
    public ResponseEntity<UsuarioResponse> activar(@PathVariable Long id) {
        Usuario u = usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        u.setActivo(true);
        return ResponseEntity.ok(UsuarioMapper.toResponse(usuarioRepo.save(u)));
    }
}

