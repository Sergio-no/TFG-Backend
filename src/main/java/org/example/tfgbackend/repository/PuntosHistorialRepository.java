package org.example.tfgbackend.repository;

import org.example.tfgbackend.model.PuntosHistorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PuntosHistorialRepository extends JpaRepository<PuntosHistorial, Long> {
    List<PuntosHistorial> findByClienteIdOrderByFechaDesc(Long clienteId);
}
