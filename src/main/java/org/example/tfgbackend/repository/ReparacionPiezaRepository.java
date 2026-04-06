package org.example.tfgbackend.repository;

import org.example.tfgbackend.model.ReparacionPieza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReparacionPiezaRepository extends JpaRepository<ReparacionPieza, Long> {
    List<ReparacionPieza> findByReparacionId(Long reparacionId);
}
