package org.example.tfgbackend.repository;

import org.example.tfgbackend.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    Optional<Factura> findByNumeroFactura(String numeroFactura);

    List<Factura> findByClienteId(Long clienteId);

    List<Factura> findByPagada(boolean pagada);

    boolean existsByReparacionId(Long reparacionId);

    @Query("SELECT COALESCE(SUM(f.total),0) FROM Factura f " +
            "WHERE f.pagada = true AND f.fechaPago BETWEEN :inicio AND :fin")
    BigDecimal sumTotalPagadoEntreFechas(LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT COUNT(f) FROM Factura f WHERE f.fecha BETWEEN :inicio AND :fin")
    long countFacturasHoy(LocalDateTime inicio, LocalDateTime fin);
}
