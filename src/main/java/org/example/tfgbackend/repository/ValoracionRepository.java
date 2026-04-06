package org.example.tfgbackend.repository;

import org.example.tfgbackend.model.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {
    List<Valoracion> findByClienteId(Long clienteId);

    @Query("SELECT AVG(v.puntuacion) FROM Valoracion v")
    Double findMediaPuntuacion();

    List<Valoracion> findTop10ByOrderByFechaDesc();
}
