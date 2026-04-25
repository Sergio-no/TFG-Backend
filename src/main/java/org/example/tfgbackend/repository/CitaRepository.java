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

    /**
     * Busca citas NO canceladas en un rango de fechas.
     * Se usa para calcular las horas ocupadas de un día.
     */
    @Query("SELECT c FROM Cita c WHERE c.fecha BETWEEN :inicio AND :fin " +
            "AND c.estado != 'CANCELADA'")
    List<Cita> findCitasNoCanceladasEntre(LocalDateTime inicio, LocalDateTime fin);

    /**
     * Comprueba si ya existe una cita (no cancelada) a esa hora exacta.
     */
    @Query("SELECT COUNT(c) > 0 FROM Cita c WHERE c.fecha = :fecha AND c.estado != 'CANCELADA'")
    boolean existsCitaEnHora(LocalDateTime fecha);
}
