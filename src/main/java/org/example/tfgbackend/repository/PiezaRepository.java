package org.example.tfgbackend.repository;

import org.example.tfgbackend.model.Pieza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PiezaRepository extends JpaRepository<Pieza, Long> {
    @Query("SELECT p FROM Pieza p WHERE p.stockActual < p.stockMinimo")
    List<Pieza> findPiezasConStockBajo();

    List<Pieza> findByNombreContainingIgnoreCase(String nombre);
}
