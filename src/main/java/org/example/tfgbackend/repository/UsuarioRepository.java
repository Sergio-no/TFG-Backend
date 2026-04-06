package org.example.tfgbackend.repository;

import org.example.tfgbackend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByFirebaseUid(String firebaseUid);

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}
