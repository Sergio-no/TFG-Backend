package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.request.MecanicoRequest;
import org.example.tfgbackend.dto.response.MecanicoResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.model.Mecanico;
import org.example.tfgbackend.repository.MecanicoRepository;
import org.example.tfgbackend.util.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MecanicoService {
    @Autowired private MecanicoRepository mecanicoRepo;

    public List<MecanicoResponse> getAll() {
        return mecanicoRepo.findAll().stream()
                .map(UsuarioMapper::toMecanicoResponse).toList();
    }

    public List<MecanicoResponse> getActivos() {
        return mecanicoRepo.findByActivoTallerTrue().stream()
                .map(UsuarioMapper::toMecanicoResponse).toList();
    }

    @Transactional
    public MecanicoResponse crear(MecanicoRequest req) {
        Mecanico m = new Mecanico();
        m.setNombre(req.getNombre());
        m.setApellidos(req.getApellidos());
        m.setEspecialidad(req.getEspecialidad());
        m.setTelefono(req.getTelefono());
        m.setActivoTaller(true);
        return UsuarioMapper.toMecanicoResponse(mecanicoRepo.save(m));
    }

    @Transactional
    public MecanicoResponse toggleActivo(Long id) {
        Mecanico m = mecanicoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mecánico no encontrado"));
        m.setActivoTaller(!m.isActivoTaller());
        return UsuarioMapper.toMecanicoResponse(mecanicoRepo.save(m));
    }

    @Transactional
    public MecanicoResponse actualizar(Long id, MecanicoRequest req) {
        Mecanico m = mecanicoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mecánico no encontrado"));
        m.setNombre(req.getNombre());
        m.setApellidos(req.getApellidos());
        m.setEspecialidad(req.getEspecialidad());
        m.setTelefono(req.getTelefono());
        return UsuarioMapper.toMecanicoResponse(mecanicoRepo.save(m));
    }
}
