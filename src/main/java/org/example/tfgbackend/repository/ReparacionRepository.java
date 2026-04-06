package org.example.tfgbackend.repository;

import org.example.tfgbackend.model.Reparacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReparacionRepository extends JpaRepository<Reparacion, Long> {
    List<Reparacion> findByEstado(String estado);

    List<Reparacion> findByVehiculoClienteId(Long clienteId);

    List<Reparacion> findByMecanicoId(Long mecanicoId);

    @Query("SELECT r FROM Reparacion r WHERE r.estado IN ('EN_PROCESO','TERMINADA')")
    List<Reparacion> findActivas();

    @Query("SELECT COUNT(r) FROM Reparacion r WHERE r.estado = 'EN_PROCESO'")
    long countActivas();
}
