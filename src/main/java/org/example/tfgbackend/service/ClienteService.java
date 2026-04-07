package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.response.ClienteResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.repository.ClienteRepository;
import org.example.tfgbackend.util.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired private ClienteRepository clienteRepo;

    public List<ClienteResponse> getAll() {
        return clienteRepo.findAll().stream()
                .map(UsuarioMapper::toClienteResponse).toList();
    }

    public ClienteResponse getById(Long id) {
        return UsuarioMapper.toClienteResponse(clienteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado")));
    }

    public ClienteResponse getByFirebaseUid(String uid) {
        return UsuarioMapper.toClienteResponse(
                clienteRepo.findByUsuarioFirebaseUid(uid)
                        .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado")));
    }
}
