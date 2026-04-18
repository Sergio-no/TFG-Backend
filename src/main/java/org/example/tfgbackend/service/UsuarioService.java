package org.example.tfgbackend.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import org.example.tfgbackend.dto.request.RegisterRequest;
import org.example.tfgbackend.dto.request.UpdateProfileRequest;
import org.example.tfgbackend.dto.response.UsuarioResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.model.Cliente;
import org.example.tfgbackend.model.Oficina;
import org.example.tfgbackend.model.Usuario;
import org.example.tfgbackend.repository.*;
import org.example.tfgbackend.util.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class UsuarioService {
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private ClienteRepository clienteRepo;
    @Autowired private OficinaRepository oficinaRepo;

    @Transactional
    public UsuarioResponse registrar(RegisterRequest req) {
        if (usuarioRepo.existsByEmail(req.getEmail()))
            throw new IllegalArgumentException("Email ya registrado");

        Usuario u = new Usuario();
        u.setFirebaseUid(req.getFirebaseUid());
        u.setNombre(req.getNombre());
        u.setApellidos(req.getApellidos());
        u.setEmail(req.getEmail());
        u.setTelefono(req.getTelefono());
        u.setRol(req.getRol());
        u = usuarioRepo.save(u);

        if ("CLIENTE".equals(u.getRol())) {
            Cliente c = new Cliente();
            c.setUsuario(u);
            clienteRepo.save(c);
        } else if ("OFICINA".equals(u.getRol()) || "JEFE".equals(u.getRol())) {
            Oficina o = new Oficina();
            o.setUsuario(u);
            oficinaRepo.save(o);
        }

        // Fijar custom claim en Firebase para que el rol viaje en el idToken
        try {
            FirebaseAuth.getInstance().setCustomUserClaims(
                    req.getFirebaseUid(), Map.of("rol", u.getRol()));
        } catch (FirebaseAuthException e) {
            System.err.println("Error fijando custom claim: " + e.getMessage());
        }

        return UsuarioMapper.toResponse(u);
    }

    /** Actualizar nombre, apellidos y teléfono */
    @Transactional
    public UsuarioResponse updateProfile(String firebaseUid, UpdateProfileRequest req) {
        Usuario u = usuarioRepo.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        u.setNombre(req.getNombre());
        u.setApellidos(req.getApellidos());
        u.setTelefono(req.getTelefono());
        u = usuarioRepo.save(u);

        return UsuarioMapper.toResponse(u);
    }

    public UsuarioResponse getByFirebaseUid(String uid) {
        return UsuarioMapper.toResponse(
                usuarioRepo.findByFirebaseUid(uid)
                        .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"))
        );
    }

    public UsuarioResponse getById(Long id) {
        return UsuarioMapper.toResponse(
                usuarioRepo.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"))
        );
    }
}