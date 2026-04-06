package org.example.tfgbackend.repository;

import org.example.tfgbackend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByUsuarioId(Long usuarioId);

    Optional<Cliente> findByUsuarioFirebaseUid(String firebaseUid);
}
