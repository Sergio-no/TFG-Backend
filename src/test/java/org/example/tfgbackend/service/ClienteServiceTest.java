package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.request.ClienteRequest;
import org.example.tfgbackend.dto.response.ClienteResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.model.Cliente;
import org.example.tfgbackend.model.Usuario;
import org.example.tfgbackend.repository.ClienteRepository;
import org.example.tfgbackend.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository mockClienteRepo;
    @Mock
    private UsuarioRepository mockUsuarioRepo;

    @InjectMocks
    private ClienteService clienteServiceUnderTest;

    @Test
    void testGetAll() {
        // Setup
        // Configure ClienteRepository.findAll(...).
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("n");
        usuario.setApellidos("a");
        usuario.setEmail("e");
        usuario.setTelefono("t");
        usuario.setFirebaseUid("uid");
        usuario.setRol("rol");
        usuario.setActivo(false);
        cliente.setUsuario(usuario);
        cliente.setPuntosAcumulados(0);
        cliente.setTotalGastado(new BigDecimal("0.00"));
        cliente.setFechaRegistro(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<Cliente> clientes = List.of(cliente);
        when(mockClienteRepo.findAll()).thenReturn(clientes);

        // Run the test
        final List<ClienteResponse> result = clienteServiceUnderTest.getAll();

        // Verify the results
    }

    @Test
    void testGetAll_ClienteRepositoryReturnsNoItems() {
        // Setup
        when(mockClienteRepo.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<ClienteResponse> result = clienteServiceUnderTest.getAll();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetById() {
        // Setup
        // Configure ClienteRepository.findById(...).
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("n");
        usuario.setApellidos("a");
        usuario.setEmail("e");
        usuario.setTelefono("t");
        usuario.setFirebaseUid("uid");
        usuario.setRol("rol");
        usuario.setActivo(false);
        cliente1.setUsuario(usuario);
        cliente1.setPuntosAcumulados(0);
        cliente1.setTotalGastado(new BigDecimal("0.00"));
        cliente1.setFechaRegistro(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<Cliente> cliente = Optional.of(cliente1);
        when(mockClienteRepo.findById(0L)).thenReturn(cliente);

        // Run the test
        final ClienteResponse result = clienteServiceUnderTest.getById(0L);

        // Verify the results
    }

    @Test
    void testGetById_ClienteRepositoryReturnsAbsent() {
        // Setup
        when(mockClienteRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> clienteServiceUnderTest.getById(0L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testGetByFirebaseUid() {
        // Setup
        // Configure ClienteRepository.findByUsuarioFirebaseUid(...).
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("n");
        usuario.setApellidos("a");
        usuario.setEmail("e");
        usuario.setTelefono("t");
        usuario.setFirebaseUid("uid");
        usuario.setRol("rol");
        usuario.setActivo(false);
        cliente1.setUsuario(usuario);
        cliente1.setPuntosAcumulados(0);
        cliente1.setTotalGastado(new BigDecimal("0.00"));
        cliente1.setFechaRegistro(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<Cliente> cliente = Optional.of(cliente1);
        when(mockClienteRepo.findByUsuarioFirebaseUid("uid")).thenReturn(cliente);

        // Run the test
        final ClienteResponse result = clienteServiceUnderTest.getByFirebaseUid("uid");

        // Verify the results
    }

    @Test
    void testGetByFirebaseUid_ClienteRepositoryReturnsAbsent() {
        // Setup
        when(mockClienteRepo.findByUsuarioFirebaseUid("uid")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> clienteServiceUnderTest.getByFirebaseUid("uid"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testCrear() {
        // Setup
        final ClienteRequest req = new ClienteRequest();
        req.setNombre("n");
        req.setApellidos("a");
        req.setEmail("e");
        req.setTelefono("t");

        when(mockUsuarioRepo.existsByEmail("e")).thenReturn(false);

        // Configure UsuarioRepository.save(...).
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("n");
        usuario.setApellidos("a");
        usuario.setEmail("e");
        usuario.setTelefono("t");
        usuario.setFirebaseUid("uid");
        usuario.setRol("rol");
        usuario.setActivo(false);
        when(mockUsuarioRepo.save(any(Usuario.class))).thenReturn(usuario);

        // Configure ClienteRepository.save(...).
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setId(0L);
        usuario1.setNombre("n");
        usuario1.setApellidos("a");
        usuario1.setEmail("e");
        usuario1.setTelefono("t");
        usuario1.setFirebaseUid("uid");
        usuario1.setRol("rol");
        usuario1.setActivo(false);
        cliente.setUsuario(usuario1);
        cliente.setPuntosAcumulados(0);
        cliente.setTotalGastado(new BigDecimal("0.00"));
        cliente.setFechaRegistro(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockClienteRepo.save(any(Cliente.class))).thenReturn(cliente);

        // Run the test
        final ClienteResponse result = clienteServiceUnderTest.crear(req);

        // Verify the results
    }

    @Test
    void testCrear_UsuarioRepositoryExistsByEmailReturnsTrue() {
        // Setup
        final ClienteRequest req = new ClienteRequest();
        req.setNombre("n");
        req.setApellidos("a");
        req.setEmail("e");
        req.setTelefono("t");

        when(mockUsuarioRepo.existsByEmail("e")).thenReturn(true);

        // Run the test
        assertThatThrownBy(() -> clienteServiceUnderTest.crear(req)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testCrear_UsuarioRepositorySaveThrowsOptimisticLockingFailureException() {
        // Setup
        final ClienteRequest req = new ClienteRequest();
        req.setNombre("n");
        req.setApellidos("a");
        req.setEmail("e");
        req.setTelefono("t");

        when(mockUsuarioRepo.existsByEmail("e")).thenReturn(false);
        when(mockUsuarioRepo.save(any(Usuario.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> clienteServiceUnderTest.crear(req))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testCrear_ClienteRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final ClienteRequest req = new ClienteRequest();
        req.setNombre("n");
        req.setApellidos("a");
        req.setEmail("e");
        req.setTelefono("t");

        when(mockUsuarioRepo.existsByEmail("e")).thenReturn(false);

        // Configure UsuarioRepository.save(...).
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("n");
        usuario.setApellidos("a");
        usuario.setEmail("e");
        usuario.setTelefono("t");
        usuario.setFirebaseUid("uid");
        usuario.setRol("rol");
        usuario.setActivo(false);
        when(mockUsuarioRepo.save(any(Usuario.class))).thenReturn(usuario);

        when(mockClienteRepo.save(any(Cliente.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> clienteServiceUnderTest.crear(req))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testActualizar() {
        // Setup
        final ClienteRequest req = new ClienteRequest();
        req.setNombre("n");
        req.setApellidos("a");
        req.setEmail("e");
        req.setTelefono("t");

        // Configure ClienteRepository.findById(...).
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("n");
        usuario.setApellidos("a");
        usuario.setEmail("e");
        usuario.setTelefono("t");
        usuario.setFirebaseUid("uid");
        usuario.setRol("rol");
        usuario.setActivo(false);
        cliente1.setUsuario(usuario);
        cliente1.setPuntosAcumulados(0);
        cliente1.setTotalGastado(new BigDecimal("0.00"));
        cliente1.setFechaRegistro(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<Cliente> cliente = Optional.of(cliente1);
        when(mockClienteRepo.findById(0L)).thenReturn(cliente);

        // Run the test
        final ClienteResponse result = clienteServiceUnderTest.actualizar(0L, req);

        // Verify the results
        verify(mockUsuarioRepo).save(any(Usuario.class));
    }

    @Test
    void testActualizar_ClienteRepositoryReturnsAbsent() {
        // Setup
        final ClienteRequest req = new ClienteRequest();
        req.setNombre("n");
        req.setApellidos("a");
        req.setEmail("e");
        req.setTelefono("t");

        when(mockClienteRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> clienteServiceUnderTest.actualizar(0L, req))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testActualizar_UsuarioRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final ClienteRequest req = new ClienteRequest();
        req.setNombre("n");
        req.setApellidos("a");
        req.setEmail("e");
        req.setTelefono("t");

        // Configure ClienteRepository.findById(...).
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("n");
        usuario.setApellidos("a");
        usuario.setEmail("e");
        usuario.setTelefono("t");
        usuario.setFirebaseUid("uid");
        usuario.setRol("rol");
        usuario.setActivo(false);
        cliente1.setUsuario(usuario);
        cliente1.setPuntosAcumulados(0);
        cliente1.setTotalGastado(new BigDecimal("0.00"));
        cliente1.setFechaRegistro(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<Cliente> cliente = Optional.of(cliente1);
        when(mockClienteRepo.findById(0L)).thenReturn(cliente);

        when(mockUsuarioRepo.save(any(Usuario.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> clienteServiceUnderTest.actualizar(0L, req))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testEliminar() {
        // Setup
        // Configure ClienteRepository.findById(...).
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("n");
        usuario.setApellidos("a");
        usuario.setEmail("e");
        usuario.setTelefono("t");
        usuario.setFirebaseUid("uid");
        usuario.setRol("rol");
        usuario.setActivo(false);
        cliente1.setUsuario(usuario);
        cliente1.setPuntosAcumulados(0);
        cliente1.setTotalGastado(new BigDecimal("0.00"));
        cliente1.setFechaRegistro(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<Cliente> cliente = Optional.of(cliente1);
        when(mockClienteRepo.findById(0L)).thenReturn(cliente);

        // Run the test
        clienteServiceUnderTest.eliminar(0L);

        // Verify the results
        verify(mockUsuarioRepo).save(any(Usuario.class));
    }

    @Test
    void testEliminar_ClienteRepositoryReturnsAbsent() {
        // Setup
        when(mockClienteRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> clienteServiceUnderTest.eliminar(0L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testEliminar_UsuarioRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        // Configure ClienteRepository.findById(...).
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("n");
        usuario.setApellidos("a");
        usuario.setEmail("e");
        usuario.setTelefono("t");
        usuario.setFirebaseUid("uid");
        usuario.setRol("rol");
        usuario.setActivo(false);
        cliente1.setUsuario(usuario);
        cliente1.setPuntosAcumulados(0);
        cliente1.setTotalGastado(new BigDecimal("0.00"));
        cliente1.setFechaRegistro(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<Cliente> cliente = Optional.of(cliente1);
        when(mockClienteRepo.findById(0L)).thenReturn(cliente);

        when(mockUsuarioRepo.save(any(Usuario.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> clienteServiceUnderTest.eliminar(0L))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }
}
