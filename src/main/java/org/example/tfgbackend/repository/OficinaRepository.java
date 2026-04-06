package org.example.tfgbackend.repository;

import org.example.tfgbackend.model.Oficina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OficinaRepository extends JpaRepository<Oficina, Long> {
    Optional<Oficina> findByUsuarioId(Long usuarioId);
    Optional<Oficina> findByUsuarioFirebaseUid(String firebaseUid);
}