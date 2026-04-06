package org.example.tfgbackend.repository;

import org.example.tfgbackend.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByClienteId(Long clienteId);

    List<Cita> findByEstado(String estado);

    @Query("SELECT c FROM Cita c WHERE c.fecha BETWEEN :inicio AND :fin ORDER BY c.fecha ASC")
    List<Cita> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT c FROM Cita c WHERE c.fecha BETWEEN :inicio AND :fin " +
            "AND c.estado != 'CANCELADA' ORDER BY c.fecha ASC")
    List<Cita> findCitasHoy(LocalDateTime inicio, LocalDateTime fin);
}
