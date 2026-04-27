package org.example.tfgbackend.repository;

import org.example.tfgbackend.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    // Todas las notificaciones de un usuario, más recientes primero
    List<Notificacion> findByUsuarioIdOrderByFechaDesc(Long usuarioId);

    // Cuenta las no leídas de un usuario
    long countByUsuarioIdAndLeidaFalse(Long usuarioId);

    // Marca todas como leídas para un usuario
    @Modifying
    @Query("UPDATE Notificacion n SET n.leida = true WHERE n.usuario.id = :usuarioId AND n.leida = false")
    void marcarTodasLeidas(Long usuarioId);
}