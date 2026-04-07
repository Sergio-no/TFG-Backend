package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.request.AjusteStockRequest;
import org.example.tfgbackend.dto.request.PiezaRequest;
import org.example.tfgbackend.dto.response.PiezaResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.model.Pieza;
import org.example.tfgbackend.repository.PiezaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PiezaService {
    @Autowired private PiezaRepository piezaRepo;

    public List<PiezaResponse> getAll() {
        return piezaRepo.findAll().stream().map(this::toResponse).toList();
    }

    public List<PiezaResponse> getStockBajo() {
        return piezaRepo.findPiezasConStockBajo().stream().map(this::toResponse).toList();
    }

    @Transactional
    public PiezaResponse crear(PiezaRequest req) {
        Pieza p = new Pieza();
        p.setNombre(req.getNombre());
        p.setDescripcion(req.getDescripcion());
        p.setPrecioUnitario(req.getPrecioUnitario());
        p.setStockActual(req.getStockActual());
        p.setStockMinimo(req.getStockMinimo());
        return toResponse(piezaRepo.save(p));
    }

    @Transactional
    public PiezaResponse ajustarStock(Long id, AjusteStockRequest req) {
        Pieza p = piezaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pieza no encontrada"));
        p.setStockActual(Math.max(0, p.getStockActual() + req.getCantidad()));
        return toResponse(piezaRepo.save(p));
    }

    private PiezaResponse toResponse(Pieza p) {
        PiezaResponse r = new PiezaResponse();
        r.setId(p.getId());
        r.setNombre(p.getNombre());
        r.setDescripcion(p.getDescripcion());
        r.setPrecioUnitario(p.getPrecioUnitario().toPlainString());
        r.setStockActual(p.getStockActual());
        r.setStockMinimo(p.getStockMinimo());
        r.setStockBajo(p.getStockActual() < p.getStockMinimo());
        return r;
    }
}
