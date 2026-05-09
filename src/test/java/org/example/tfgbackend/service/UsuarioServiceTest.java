package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.request.RegisterRequest;
import org.example.tfgbackend.dto.request.UpdateProfileRequest;
import org.example.tfgbackend.dto.response.UsuarioResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.model.Cliente;
import org.example.tfgbackend.model.Oficina;
import org.example.tfgbackend.model.Usuario;
import org.example.tfgbackend.repository.ClienteRepository;
import org.example.tfgbackend.repository.OficinaRepository;
import org.example.tfgbackend.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository mockUsuarioRepo;
    @Mock
    private ClienteRepository mockClienteRepo;
    @Mock
    private OficinaRepository mockOficinaRepo;

    @InjectMocks
    private UsuarioService usuarioServiceUnderTest;

    @Test
    void testRegistrar() {
        // Setup
        final RegisterRequest req = new RegisterRequest();
        req.setFirebaseUid("firebaseUid");
        req.setNombre("n");
        req.setApellidos("a");
        req.setEmail("e");
        req.setTelefono("t");
        req.setRol("r");

        when(mockUsuarioRepo.existsByEmail("e")).thenReturn(false);

        // Configure UsuarioRepository.save(...).
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("n");
        usuario.setApellidos("a");
        usuario.setEmail("e");
        usuario.setTelefono("t");
        usuario.setFirebaseUid("firebaseUid");
        usuario.setRol("r");
        usuario.setActivo(false);
        when(mockUsuarioRepo.save(any(Usuario.class))).thenReturn(usuario);

        // Run the test
        final UsuarioResponse result = usuarioServiceUnderTest.registrar(req);

        // Verify the results
        verify(mockClienteRepo).save(any(Cliente.class));
        verify(mockOficinaRepo).save(any(Oficina.class));
    }

    @Test
    void testRegistrar_UsuarioRepositoryExistsByEmailReturnsTrue() {
        // Setup
        final RegisterRequest req = new RegisterRequest();
        req.setFirebaseUid("firebaseUid");
        req.setNombre("n");
        req.setApellidos("a");
        req.setEmail("e");
        req.setTelefono("t");
        req.setRol("r");

        when(mockUsuarioRepo.existsByEmail("e")).thenReturn(true);

        // Run the test
        assertThatThrownBy(() -> usuarioServiceUnderTest.registrar(req)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testRegistrar_UsuarioRepositorySaveThrowsOptimisticLockingFailureException() {
        // Setup
        final RegisterRequest req = new RegisterRequest();
        req.setFirebaseUid("firebaseUid");
        req.setNombre("n");
        req.setApellidos("a");
        req.setEmail("e");
        req.setTelefono("t");
        req.setRol("r");

        when(mockUsuarioRepo.existsByEmail("e")).thenReturn(false);
        when(mockUsuarioRepo.save(any(Usuario.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> usuarioServiceUnderTest.registrar(req))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testRegistrar_ClienteRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final RegisterRequest req = new RegisterRequest();
        req.setFirebaseUid("firebaseUid");
        req.setNombre("n");
        req.setApellidos("a");
        req.setEmail("e");
        req.setTelefono("t");
        req.setRol("r");

        when(mockUsuarioRepo.existsByEmail("e")).thenReturn(false);

        // Configure UsuarioRepository.save(...).
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("n");
        usuario.setApellidos("a");
        usuario.setEmail("e");
        usuario.setTelefono("t");
        usuario.setFirebaseUid("firebaseUid");
        usuario.setRol("r");
        usuario.setActivo(false);
        when(mockUsuarioRepo.save(any(Usuario.class))).thenReturn(usuario);

        when(mockClienteRepo.save(any(Cliente.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> usuarioServiceUnderTest.registrar(req))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testRegistrar_OficinaRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final RegisterRequest req = new RegisterRequest();
        req.setFirebaseUid("firebaseUid");
        req.setNombre("n");
        req.setApellidos("a");
        req.setEmail("e");
        req.setTelefono("t");
        req.setRol("r");

        when(mockUsuarioRepo.existsByEmail("e")).thenReturn(false);

        // Configure UsuarioRepository.save(...).
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("n");
        usuario.setApellidos("a");
        usuario.setEmail("e");
        usuario.setTelefono("t");
        usuario.setFirebaseUid("firebaseUid");
        usuario.setRol("r");
        usuario.setActivo(false);
        when(mockUsuarioRepo.save(any(Usuario.class))).thenReturn(usuario);

        when(mockOficinaRepo.save(any(Oficina.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> usuarioServiceUnderTest.registrar(req))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testUpdateProfile() {
        // Setup
        final UpdateProfileRequest req = new UpdateProfileRequest();
        req.setNombre("n");
        req.setApellidos("a");
        req.setTelefono("t");

        // Configure UsuarioRepository.findByFirebaseUid(...).
        final Usuario usuario1 = new Usuario();
        usuario1.setId(0L);
        usuario1.setNombre("n");
        usuario1.setApellidos("a");
        usuario1.setEmail("e");
        usuario1.setTelefono("t");
        usuario1.setFirebaseUid("firebaseUid");
        usuario1.setRol("r");
        usuario1.setActivo(false);
        final Optional<Usuario> usuario = Optional.of(usuario1);
        when(mockUsuarioRepo.findByFirebaseUid("firebaseUid")).thenReturn(usuario);

        // Configure UsuarioRepository.save(...).
        final Usuario usuario2 = new Usuario();
        usuario2.setId(0L);
        usuario2.setNombre("n");
        usuario2.setApellidos("a");
        usuario2.setEmail("e");
        usuario2.setTelefono("t");
        usuario2.setFirebaseUid("firebaseUid");
        usuario2.setRol("r");
        usuario2.setActivo(false);
        when(mockUsuarioRepo.save(any(Usuario.class))).thenReturn(usuario2);

        // Run the test
        final UsuarioResponse result = usuarioServiceUnderTest.updateProfile("firebaseUid", req);

        // Verify the results
    }

    @Test
    void testUpdateProfile_UsuarioRepositoryFindByFirebaseUidReturnsAbsent() {
        // Setup
        final UpdateProfileRequest req = new UpdateProfileRequest();
        req.setNombre("n");
        req.setApellidos("a");
        req.setTelefono("t");

        when(mockUsuarioRepo.findByFirebaseUid("firebaseUid")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> usuarioServiceUnderTest.updateProfile("firebaseUid", req))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testUpdateProfile_UsuarioRepositorySaveThrowsOptimisticLockingFailureException() {
        // Setup
        final UpdateProfileRequest req = new UpdateProfileRequest();
        req.setNombre("n");
        req.setApellidos("a");
        req.setTelefono("t");

        // Configure UsuarioRepository.findByFirebaseUid(...).
        final Usuario usuario1 = new Usuario();
        usuario1.setId(0L);
        usuario1.setNombre("n");
        usuario1.setApellidos("a");
        usuario1.setEmail("e");
        usuario1.setTelefono("t");
        usuario1.setFirebaseUid("firebaseUid");
        usuario1.setRol("r");
        usuario1.setActivo(false);
        final Optional<Usuario> usuario = Optional.of(usuario1);
        when(mockUsuarioRepo.findByFirebaseUid("firebaseUid")).thenReturn(usuario);

        when(mockUsuarioRepo.save(any(Usuario.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> usuarioServiceUnderTest.updateProfile("firebaseUid", req))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testGetByFirebaseUid() {
        // Setup
        // Configure UsuarioRepository.findByFirebaseUid(...).
        final Usuario usuario1 = new Usuario();
        usuario1.setId(0L);
        usuario1.setNombre("n");
        usuario1.setApellidos("a");
        usuario1.setEmail("e");
        usuario1.setTelefono("t");
        usuario1.setFirebaseUid("firebaseUid");
        usuario1.setRol("r");
        usuario1.setActivo(false);
        final Optional<Usuario> usuario = Optional.of(usuario1);
        when(mockUsuarioRepo.findByFirebaseUid("uid")).thenReturn(usuario);

        // Run the test
        final UsuarioResponse result = usuarioServiceUnderTest.getByFirebaseUid("uid");

        // Verify the results
    }

    @Test
    void testGetByFirebaseUid_UsuarioRepositoryReturnsAbsent() {
        // Setup
        when(mockUsuarioRepo.findByFirebaseUid("uid")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> usuarioServiceUnderTest.getByFirebaseUid("uid"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testGetById() {
        // Setup
        // Configure UsuarioRepository.findById(...).
        final Usuario usuario1 = new Usuario();
        usuario1.setId(0L);
        usuario1.setNombre("n");
        usuario1.setApellidos("a");
        usuario1.setEmail("e");
        usuario1.setTelefono("t");
        usuario1.setFirebaseUid("firebaseUid");
        usuario1.setRol("r");
        usuario1.setActivo(false);
        final Optional<Usuario> usuario = Optional.of(usuario1);
        when(mockUsuarioRepo.findById(0L)).thenReturn(usuario);

        // Run the test
        final UsuarioResponse result = usuarioServiceUnderTest.getById(0L);

        // Verify the results
    }

    @Test
    void testGetById_UsuarioRepositoryReturnsAbsent() {
        // Setup
        when(mockUsuarioRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> usuarioServiceUnderTest.getById(0L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
