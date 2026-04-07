package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.request.RegisterRequest;
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

@Service
public class UsuarioService {
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private ClienteRepository clienteRepo;
    @Autowired private OficinaRepository oficinaRepo;

    @Transactional
    public UsuarioResponse registrar(RegisterRequest req) {
        if (usuarioRepo.existsByEmail(req.getEmail()))
            throw new IllegalArgumentException("Email ya registrado");

        // Sustituye el bloque de creación de usuario por esto:
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
