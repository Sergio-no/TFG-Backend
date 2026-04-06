package org.example.tfgbackend.repository;

import org.example.tfgbackend.model.Mecanico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MecanicoRepository extends JpaRepository<Mecanico, Long> {
    List<Mecanico> findByActivoTallerTrue();
}