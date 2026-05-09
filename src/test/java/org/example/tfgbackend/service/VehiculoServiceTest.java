package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.request.VehiculoRequest;
import org.example.tfgbackend.dto.response.VehiculoResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.model.*;
import org.example.tfgbackend.repository.CitaRepository;
import org.example.tfgbackend.repository.ClienteRepository;
import org.example.tfgbackend.repository.ReparacionRepository;
import org.example.tfgbackend.repository.VehiculoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculoServiceTest {

    @Mock
    private VehiculoRepository mockVehiculoRepo;
    @Mock
    private ClienteRepository mockClienteRepo;
    @Mock
    private CitaRepository mockCitaRepo;
    @Mock
    private ReparacionRepository mockReparacionRepo;

    @InjectMocks
    private VehiculoService vehiculoServiceUnderTest;

    @Test
    void testGetAll() {
        // Setup
        // Configure VehiculoRepository.findAll(...).
        final Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo.setCliente(cliente);
        vehiculo.setMarca("m");
        vehiculo.setModelo("m");
        vehiculo.setAnio((short) 0);
        vehiculo.setMatricula("m");
        vehiculo.setKilometraje(0);
        final List<Vehiculo> vehiculos = List.of(vehiculo);
        when(mockVehiculoRepo.findAll()).thenReturn(vehiculos);

        // Run the test
        final List<VehiculoResponse> result = vehiculoServiceUnderTest.getAll();

        // Verify the results
    }

    @Test
    void testGetAll_VehiculoRepositoryReturnsNoItems() {
        // Setup
        when(mockVehiculoRepo.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<VehiculoResponse> result = vehiculoServiceUnderTest.getAll();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetByCliente() {
        // Setup
        // Configure VehiculoRepository.findByClienteId(...).
        final Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo.setCliente(cliente);
        vehiculo.setMarca("m");
        vehiculo.setModelo("m");
        vehiculo.setAnio((short) 0);
        vehiculo.setMatricula("m");
        vehiculo.setKilometraje(0);
        final List<Vehiculo> vehiculos = List.of(vehiculo);
        when(mockVehiculoRepo.findByClienteId(0L)).thenReturn(vehiculos);

        // Run the test
        final List<VehiculoResponse> result = vehiculoServiceUnderTest.getByCliente(0L);

        // Verify the results
    }

    @Test
    void testGetByCliente_VehiculoRepositoryReturnsNoItems() {
        // Setup
        when(mockVehiculoRepo.findByClienteId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<VehiculoResponse> result = vehiculoServiceUnderTest.getByCliente(0L);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetById() {
        // Setup
        // Configure VehiculoRepository.findById(...).
        final Vehiculo vehiculo1 = new Vehiculo();
        vehiculo1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo1.setCliente(cliente);
        vehiculo1.setMarca("m");
        vehiculo1.setModelo("m");
        vehiculo1.setAnio((short) 0);
        vehiculo1.setMatricula("m");
        vehiculo1.setKilometraje(0);
        final Optional<Vehiculo> vehiculo = Optional.of(vehiculo1);
        when(mockVehiculoRepo.findById(0L)).thenReturn(vehiculo);

        // Run the test
        final VehiculoResponse result = vehiculoServiceUnderTest.getById(0L);

        // Verify the results
    }

    @Test
    void testGetById_VehiculoRepositoryReturnsAbsent() {
        // Setup
        when(mockVehiculoRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> vehiculoServiceUnderTest.getById(0L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testGetByMatricula() {
        // Setup
        // Configure VehiculoRepository.findByMatricula(...).
        final Vehiculo vehiculo1 = new Vehiculo();
        vehiculo1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo1.setCliente(cliente);
        vehiculo1.setMarca("m");
        vehiculo1.setModelo("m");
        vehiculo1.setAnio((short) 0);
        vehiculo1.setMatricula("m");
        vehiculo1.setKilometraje(0);
        final Optional<Vehiculo> vehiculo = Optional.of(vehiculo1);
        when(mockVehiculoRepo.findByMatricula("matricula")).thenReturn(vehiculo);

        // Run the test
        final VehiculoResponse result = vehiculoServiceUnderTest.getByMatricula("matricula");

        // Verify the results
    }

    @Test
    void testGetByMatricula_VehiculoRepositoryReturnsAbsent() {
        // Setup
        when(mockVehiculoRepo.findByMatricula("matricula")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> vehiculoServiceUnderTest.getByMatricula("matricula"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testCrear_ThrowsIllegalArgumentException() {
        // Setup
        final VehiculoRequest req = new VehiculoRequest();
        req.setClienteId(0L);
        req.setMarca("m");
        req.setModelo("m");
        req.setAnio((short) 0);
        req.setMatricula("matricula");
        req.setKilometraje(0);

        // Configure ClienteRepository.findById(...).
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente1.setUsuario(usuario);
        final Optional<Cliente> cliente = Optional.of(cliente1);
        when(mockClienteRepo.findById(0L)).thenReturn(cliente);

        // Configure VehiculoRepository.findByMatricula(...).
        final Vehiculo vehiculo1 = new Vehiculo();
        vehiculo1.setId(0L);
        final Cliente cliente2 = new Cliente();
        cliente2.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente2.setUsuario(usuario1);
        vehiculo1.setCliente(cliente2);
        vehiculo1.setMarca("m");
        vehiculo1.setModelo("m");
        vehiculo1.setAnio((short) 0);
        vehiculo1.setMatricula("m");
        vehiculo1.setKilometraje(0);
        final Optional<Vehiculo> vehiculo = Optional.of(vehiculo1);
        when(mockVehiculoRepo.findByMatricula("matricula")).thenReturn(vehiculo);

        // Run the test
        assertThatThrownBy(() -> vehiculoServiceUnderTest.crear(req)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testCrear_ClienteRepositoryReturnsAbsent() {
        // Setup
        final VehiculoRequest req = new VehiculoRequest();
        req.setClienteId(0L);
        req.setMarca("m");
        req.setModelo("m");
        req.setAnio((short) 0);
        req.setMatricula("matricula");
        req.setKilometraje(0);

        when(mockClienteRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> vehiculoServiceUnderTest.crear(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testCrear_VehiculoRepositoryFindByMatriculaReturnsAbsent() {
        // Setup
        final VehiculoRequest req = new VehiculoRequest();
        req.setClienteId(0L);
        req.setMarca("m");
        req.setModelo("m");
        req.setAnio((short) 0);
        req.setMatricula("matricula");
        req.setKilometraje(0);

        // Configure ClienteRepository.findById(...).
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente1.setUsuario(usuario);
        final Optional<Cliente> cliente = Optional.of(cliente1);
        when(mockClienteRepo.findById(0L)).thenReturn(cliente);

        when(mockVehiculoRepo.findByMatricula("matricula")).thenReturn(Optional.empty());

        // Configure VehiculoRepository.save(...).
        final Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(0L);
        final Cliente cliente2 = new Cliente();
        cliente2.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente2.setUsuario(usuario1);
        vehiculo.setCliente(cliente2);
        vehiculo.setMarca("m");
        vehiculo.setModelo("m");
        vehiculo.setAnio((short) 0);
        vehiculo.setMatricula("m");
        vehiculo.setKilometraje(0);
        when(mockVehiculoRepo.save(any(Vehiculo.class))).thenReturn(vehiculo);

        // Run the test
        final VehiculoResponse result = vehiculoServiceUnderTest.crear(req);

        // Verify the results
    }

    @Test
    void testCrear_VehiculoRepositorySaveThrowsOptimisticLockingFailureException() {
        // Setup
        final VehiculoRequest req = new VehiculoRequest();
        req.setClienteId(0L);
        req.setMarca("m");
        req.setModelo("m");
        req.setAnio((short) 0);
        req.setMatricula("matricula");
        req.setKilometraje(0);

        // Configure ClienteRepository.findById(...).
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente1.setUsuario(usuario);
        final Optional<Cliente> cliente = Optional.of(cliente1);
        when(mockClienteRepo.findById(0L)).thenReturn(cliente);

        when(mockVehiculoRepo.findByMatricula("matricula")).thenReturn(Optional.empty());
        when(mockVehiculoRepo.save(any(Vehiculo.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> vehiculoServiceUnderTest.crear(req))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testActualizar() {
        // Setup
        final VehiculoRequest req = new VehiculoRequest();
        req.setClienteId(0L);
        req.setMarca("m");
        req.setModelo("m");
        req.setAnio((short) 0);
        req.setMatricula("matricula");
        req.setKilometraje(0);

        // Configure VehiculoRepository.findById(...).
        final Vehiculo vehiculo1 = new Vehiculo();
        vehiculo1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo1.setCliente(cliente);
        vehiculo1.setMarca("m");
        vehiculo1.setModelo("m");
        vehiculo1.setAnio((short) 0);
        vehiculo1.setMatricula("m");
        vehiculo1.setKilometraje(0);
        final Optional<Vehiculo> vehiculo = Optional.of(vehiculo1);
        when(mockVehiculoRepo.findById(0L)).thenReturn(vehiculo);

        // Configure VehiculoRepository.save(...).
        final Vehiculo vehiculo2 = new Vehiculo();
        vehiculo2.setId(0L);
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        vehiculo2.setCliente(cliente1);
        vehiculo2.setMarca("m");
        vehiculo2.setModelo("m");
        vehiculo2.setAnio((short) 0);
        vehiculo2.setMatricula("m");
        vehiculo2.setKilometraje(0);
        when(mockVehiculoRepo.save(any(Vehiculo.class))).thenReturn(vehiculo2);

        // Run the test
        final VehiculoResponse result = vehiculoServiceUnderTest.actualizar(0L, req);

        // Verify the results
    }

    @Test
    void testActualizar_VehiculoRepositoryFindByIdReturnsAbsent() {
        // Setup
        final VehiculoRequest req = new VehiculoRequest();
        req.setClienteId(0L);
        req.setMarca("m");
        req.setModelo("m");
        req.setAnio((short) 0);
        req.setMatricula("matricula");
        req.setKilometraje(0);

        when(mockVehiculoRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> vehiculoServiceUnderTest.actualizar(0L, req))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testActualizar_VehiculoRepositorySaveThrowsOptimisticLockingFailureException() {
        // Setup
        final VehiculoRequest req = new VehiculoRequest();
        req.setClienteId(0L);
        req.setMarca("m");
        req.setModelo("m");
        req.setAnio((short) 0);
        req.setMatricula("matricula");
        req.setKilometraje(0);

        // Configure VehiculoRepository.findById(...).
        final Vehiculo vehiculo1 = new Vehiculo();
        vehiculo1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo1.setCliente(cliente);
        vehiculo1.setMarca("m");
        vehiculo1.setModelo("m");
        vehiculo1.setAnio((short) 0);
        vehiculo1.setMatricula("m");
        vehiculo1.setKilometraje(0);
        final Optional<Vehiculo> vehiculo = Optional.of(vehiculo1);
        when(mockVehiculoRepo.findById(0L)).thenReturn(vehiculo);

        when(mockVehiculoRepo.save(any(Vehiculo.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> vehiculoServiceUnderTest.actualizar(0L, req))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testEliminar() {
        // Setup
        // Configure VehiculoRepository.findById(...).
        final Vehiculo vehiculo1 = new Vehiculo();
        vehiculo1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo1.setCliente(cliente);
        vehiculo1.setMarca("m");
        vehiculo1.setModelo("m");
        vehiculo1.setAnio((short) 0);
        vehiculo1.setMatricula("m");
        vehiculo1.setKilometraje(0);
        final Optional<Vehiculo> vehiculo = Optional.of(vehiculo1);
        when(mockVehiculoRepo.findById(0L)).thenReturn(vehiculo);

        // Configure CitaRepository.findAll(...).
        final Cita cita = new Cita();
        final Vehiculo vehiculo2 = new Vehiculo();
        vehiculo2.setId(0L);
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        vehiculo2.setCliente(cliente1);
        vehiculo2.setMarca("m");
        vehiculo2.setModelo("m");
        vehiculo2.setAnio((short) 0);
        vehiculo2.setMatricula("m");
        vehiculo2.setKilometraje(0);
        cita.setVehiculo(vehiculo2);
        cita.setEstado("estado");
        final List<Cita> citas = List.of(cita);
        when(mockCitaRepo.findAll()).thenReturn(citas);

        // Configure ReparacionRepository.findAll(...).
        final Reparacion reparacion = new Reparacion();
        final Vehiculo vehiculo3 = new Vehiculo();
        vehiculo3.setId(0L);
        final Cliente cliente2 = new Cliente();
        cliente2.setId(0L);
        final Usuario usuario2 = new Usuario();
        usuario2.setNombre("nombre");
        usuario2.setApellidos("apellidos");
        cliente2.setUsuario(usuario2);
        vehiculo3.setCliente(cliente2);
        vehiculo3.setMarca("m");
        vehiculo3.setModelo("m");
        vehiculo3.setAnio((short) 0);
        vehiculo3.setMatricula("m");
        vehiculo3.setKilometraje(0);
        reparacion.setVehiculo(vehiculo3);
        final List<Reparacion> reparacions = List.of(reparacion);
        when(mockReparacionRepo.findAll()).thenReturn(reparacions);

        // Run the test
        vehiculoServiceUnderTest.eliminar(0L);

        // Verify the results
        verify(mockCitaRepo).delete(any(Cita.class));
        verify(mockVehiculoRepo).delete(any(Vehiculo.class));
    }

    @Test
    void testEliminar_VehiculoRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockVehiculoRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> vehiculoServiceUnderTest.eliminar(0L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testEliminar_CitaRepositoryFindAllReturnsNoItems() {
        // Setup
        // Configure VehiculoRepository.findById(...).
        final Vehiculo vehiculo1 = new Vehiculo();
        vehiculo1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo1.setCliente(cliente);
        vehiculo1.setMarca("m");
        vehiculo1.setModelo("m");
        vehiculo1.setAnio((short) 0);
        vehiculo1.setMatricula("m");
        vehiculo1.setKilometraje(0);
        final Optional<Vehiculo> vehiculo = Optional.of(vehiculo1);
        when(mockVehiculoRepo.findById(0L)).thenReturn(vehiculo);

        when(mockCitaRepo.findAll()).thenReturn(Collections.emptyList());

        // Configure ReparacionRepository.findAll(...).
        final Reparacion reparacion = new Reparacion();
        final Vehiculo vehiculo2 = new Vehiculo();
        vehiculo2.setId(0L);
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        vehiculo2.setCliente(cliente1);
        vehiculo2.setMarca("m");
        vehiculo2.setModelo("m");
        vehiculo2.setAnio((short) 0);
        vehiculo2.setMatricula("m");
        vehiculo2.setKilometraje(0);
        reparacion.setVehiculo(vehiculo2);
        final List<Reparacion> reparacions = List.of(reparacion);
        when(mockReparacionRepo.findAll()).thenReturn(reparacions);

        // Run the test
        vehiculoServiceUnderTest.eliminar(0L);

        // Verify the results
        verify(mockCitaRepo).delete(any(Cita.class));
        verify(mockVehiculoRepo).delete(any(Vehiculo.class));
    }

    @Test
    void testEliminar_ReparacionRepositoryReturnsNoItems() {
        // Setup
        // Configure VehiculoRepository.findById(...).
        final Vehiculo vehiculo1 = new Vehiculo();
        vehiculo1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo1.setCliente(cliente);
        vehiculo1.setMarca("m");
        vehiculo1.setModelo("m");
        vehiculo1.setAnio((short) 0);
        vehiculo1.setMatricula("m");
        vehiculo1.setKilometraje(0);
        final Optional<Vehiculo> vehiculo = Optional.of(vehiculo1);
        when(mockVehiculoRepo.findById(0L)).thenReturn(vehiculo);

        // Configure CitaRepository.findAll(...).
        final Cita cita = new Cita();
        final Vehiculo vehiculo2 = new Vehiculo();
        vehiculo2.setId(0L);
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        vehiculo2.setCliente(cliente1);
        vehiculo2.setMarca("m");
        vehiculo2.setModelo("m");
        vehiculo2.setAnio((short) 0);
        vehiculo2.setMatricula("m");
        vehiculo2.setKilometraje(0);
        cita.setVehiculo(vehiculo2);
        cita.setEstado("estado");
        final List<Cita> citas = List.of(cita);
        when(mockCitaRepo.findAll()).thenReturn(citas);

        when(mockReparacionRepo.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        vehiculoServiceUnderTest.eliminar(0L);

        // Verify the results
        verify(mockCitaRepo).delete(any(Cita.class));
        verify(mockVehiculoRepo).delete(any(Vehiculo.class));
    }

    @Test
    void testEliminar_CitaRepositoryDeleteThrowsOptimisticLockingFailureException() {
        // Setup
        // Configure VehiculoRepository.findById(...).
        final Vehiculo vehiculo1 = new Vehiculo();
        vehiculo1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo1.setCliente(cliente);
        vehiculo1.setMarca("m");
        vehiculo1.setModelo("m");
        vehiculo1.setAnio((short) 0);
        vehiculo1.setMatricula("m");
        vehiculo1.setKilometraje(0);
        final Optional<Vehiculo> vehiculo = Optional.of(vehiculo1);
        when(mockVehiculoRepo.findById(0L)).thenReturn(vehiculo);

        // Configure CitaRepository.findAll(...).
        final Cita cita = new Cita();
        final Vehiculo vehiculo2 = new Vehiculo();
        vehiculo2.setId(0L);
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        vehiculo2.setCliente(cliente1);
        vehiculo2.setMarca("m");
        vehiculo2.setModelo("m");
        vehiculo2.setAnio((short) 0);
        vehiculo2.setMatricula("m");
        vehiculo2.setKilometraje(0);
        cita.setVehiculo(vehiculo2);
        cita.setEstado("estado");
        final List<Cita> citas = List.of(cita);
        when(mockCitaRepo.findAll()).thenReturn(citas);

        // Configure ReparacionRepository.findAll(...).
        final Reparacion reparacion = new Reparacion();
        final Vehiculo vehiculo3 = new Vehiculo();
        vehiculo3.setId(0L);
        final Cliente cliente2 = new Cliente();
        cliente2.setId(0L);
        final Usuario usuario2 = new Usuario();
        usuario2.setNombre("nombre");
        usuario2.setApellidos("apellidos");
        cliente2.setUsuario(usuario2);
        vehiculo3.setCliente(cliente2);
        vehiculo3.setMarca("m");
        vehiculo3.setModelo("m");
        vehiculo3.setAnio((short) 0);
        vehiculo3.setMatricula("m");
        vehiculo3.setKilometraje(0);
        reparacion.setVehiculo(vehiculo3);
        final List<Reparacion> reparacions = List.of(reparacion);
        when(mockReparacionRepo.findAll()).thenReturn(reparacions);

        doThrow(OptimisticLockingFailureException.class).when(mockCitaRepo).delete(any(Cita.class));

        // Run the test
        assertThatThrownBy(() -> vehiculoServiceUnderTest.eliminar(0L))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testEliminar_VehiculoRepositoryDeleteThrowsOptimisticLockingFailureException() {
        // Setup
        // Configure VehiculoRepository.findById(...).
        final Vehiculo vehiculo1 = new Vehiculo();
        vehiculo1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo1.setCliente(cliente);
        vehiculo1.setMarca("m");
        vehiculo1.setModelo("m");
        vehiculo1.setAnio((short) 0);
        vehiculo1.setMatricula("m");
        vehiculo1.setKilometraje(0);
        final Optional<Vehiculo> vehiculo = Optional.of(vehiculo1);
        when(mockVehiculoRepo.findById(0L)).thenReturn(vehiculo);

        // Configure CitaRepository.findAll(...).
        final Cita cita = new Cita();
        final Vehiculo vehiculo2 = new Vehiculo();
        vehiculo2.setId(0L);
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        vehiculo2.setCliente(cliente1);
        vehiculo2.setMarca("m");
        vehiculo2.setModelo("m");
        vehiculo2.setAnio((short) 0);
        vehiculo2.setMatricula("m");
        vehiculo2.setKilometraje(0);
        cita.setVehiculo(vehiculo2);
        cita.setEstado("estado");
        final List<Cita> citas = List.of(cita);
        when(mockCitaRepo.findAll()).thenReturn(citas);

        // Configure ReparacionRepository.findAll(...).
        final Reparacion reparacion = new Reparacion();
        final Vehiculo vehiculo3 = new Vehiculo();
        vehiculo3.setId(0L);
        final Cliente cliente2 = new Cliente();
        cliente2.setId(0L);
        final Usuario usuario2 = new Usuario();
        usuario2.setNombre("nombre");
        usuario2.setApellidos("apellidos");
        cliente2.setUsuario(usuario2);
        vehiculo3.setCliente(cliente2);
        vehiculo3.setMarca("m");
        vehiculo3.setModelo("m");
        vehiculo3.setAnio((short) 0);
        vehiculo3.setMatricula("m");
        vehiculo3.setKilometraje(0);
        reparacion.setVehiculo(vehiculo3);
        final List<Reparacion> reparacions = List.of(reparacion);
        when(mockReparacionRepo.findAll()).thenReturn(reparacions);

        doThrow(OptimisticLockingFailureException.class).when(mockVehiculoRepo).delete(any(Vehiculo.class));

        // Run the test
        assertThatThrownBy(() -> vehiculoServiceUnderTest.eliminar(0L))
                .isInstanceOf(OptimisticLockingFailureException.class);
        verify(mockCitaRepo).delete(any(Cita.class));
    }
}
