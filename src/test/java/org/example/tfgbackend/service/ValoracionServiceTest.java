package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.request.ValoracionRequest;
import org.example.tfgbackend.dto.response.ValoracionResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.model.*;
import org.example.tfgbackend.repository.ClienteRepository;
import org.example.tfgbackend.repository.ReparacionRepository;
import org.example.tfgbackend.repository.ValoracionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValoracionServiceTest {

    @Mock
    private ValoracionRepository mockValoracionRepo;
    @Mock
    private ReparacionRepository mockReparacionRepo;
    @Mock
    private ClienteRepository mockClienteRepo;

    @InjectMocks
    private ValoracionService valoracionServiceUnderTest;

    @Test
    void testGetAll() {
        // Setup
        // Configure ValoracionRepository.findAll(...).
        final Valoracion valoracion = new Valoracion();
        valoracion.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        valoracion.setCliente(cliente);
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        vehiculo.setCliente(cliente1);
        reparacion.setVehiculo(vehiculo);
        reparacion.setEstado("estado");
        valoracion.setReparacion(reparacion);
        valoracion.setPuntuacion((short) 0);
        valoracion.setComentario("c");
        valoracion.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<Valoracion> valoracions = List.of(valoracion);
        when(mockValoracionRepo.findAll()).thenReturn(valoracions);

        // Run the test
        final List<ValoracionResponse> result = valoracionServiceUnderTest.getAll();

        // Verify the results
    }

    @Test
    void testGetAll_ValoracionRepositoryReturnsNoItems() {
        // Setup
        when(mockValoracionRepo.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<ValoracionResponse> result = valoracionServiceUnderTest.getAll();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetByCliente() {
        // Setup
        // Configure ValoracionRepository.findByClienteId(...).
        final Valoracion valoracion = new Valoracion();
        valoracion.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        valoracion.setCliente(cliente);
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        vehiculo.setCliente(cliente1);
        reparacion.setVehiculo(vehiculo);
        reparacion.setEstado("estado");
        valoracion.setReparacion(reparacion);
        valoracion.setPuntuacion((short) 0);
        valoracion.setComentario("c");
        valoracion.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<Valoracion> valoracions = List.of(valoracion);
        when(mockValoracionRepo.findByClienteId(0L)).thenReturn(valoracions);

        // Run the test
        final List<ValoracionResponse> result = valoracionServiceUnderTest.getByCliente(0L);

        // Verify the results
    }

    @Test
    void testGetByCliente_ValoracionRepositoryReturnsNoItems() {
        // Setup
        when(mockValoracionRepo.findByClienteId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<ValoracionResponse> result = valoracionServiceUnderTest.getByCliente(0L);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testCrear() {
        // Setup
        final ValoracionRequest req = new ValoracionRequest();
        req.setReparacionId(0L);
        req.setPuntuacion((short) 0);
        req.setComentario("c");

        // Configure ClienteRepository.findByUsuarioFirebaseUid(...).
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente1.setUsuario(usuario);
        final Optional<Cliente> cliente = Optional.of(cliente1);
        when(mockClienteRepo.findByUsuarioFirebaseUid("firebaseUid")).thenReturn(cliente);

        // Configure ReparacionRepository.findById(...).
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente2 = new Cliente();
        cliente2.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente2.setUsuario(usuario1);
        vehiculo.setCliente(cliente2);
        reparacion1.setVehiculo(vehiculo);
        reparacion1.setEstado("estado");
        final Optional<Reparacion> reparacion = Optional.of(reparacion1);
        when(mockReparacionRepo.findById(0L)).thenReturn(reparacion);

        // Configure ValoracionRepository.findAll(...).
        final Valoracion valoracion = new Valoracion();
        valoracion.setId(0L);
        final Cliente cliente3 = new Cliente();
        cliente3.setId(0L);
        final Usuario usuario2 = new Usuario();
        usuario2.setNombre("nombre");
        usuario2.setApellidos("apellidos");
        cliente3.setUsuario(usuario2);
        valoracion.setCliente(cliente3);
        final Reparacion reparacion2 = new Reparacion();
        reparacion2.setId(0L);
        final Vehiculo vehiculo1 = new Vehiculo();
        final Cliente cliente4 = new Cliente();
        cliente4.setId(0L);
        final Usuario usuario3 = new Usuario();
        usuario3.setNombre("nombre");
        usuario3.setApellidos("apellidos");
        cliente4.setUsuario(usuario3);
        vehiculo1.setCliente(cliente4);
        reparacion2.setVehiculo(vehiculo1);
        reparacion2.setEstado("estado");
        valoracion.setReparacion(reparacion2);
        valoracion.setPuntuacion((short) 0);
        valoracion.setComentario("c");
        valoracion.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<Valoracion> valoracions = List.of(valoracion);
        when(mockValoracionRepo.findAll()).thenReturn(valoracions);

        // Configure ValoracionRepository.save(...).
        final Valoracion valoracion1 = new Valoracion();
        valoracion1.setId(0L);
        final Cliente cliente5 = new Cliente();
        cliente5.setId(0L);
        final Usuario usuario4 = new Usuario();
        usuario4.setNombre("nombre");
        usuario4.setApellidos("apellidos");
        cliente5.setUsuario(usuario4);
        valoracion1.setCliente(cliente5);
        final Reparacion reparacion3 = new Reparacion();
        reparacion3.setId(0L);
        final Vehiculo vehiculo2 = new Vehiculo();
        final Cliente cliente6 = new Cliente();
        cliente6.setId(0L);
        final Usuario usuario5 = new Usuario();
        usuario5.setNombre("nombre");
        usuario5.setApellidos("apellidos");
        cliente6.setUsuario(usuario5);
        vehiculo2.setCliente(cliente6);
        reparacion3.setVehiculo(vehiculo2);
        reparacion3.setEstado("estado");
        valoracion1.setReparacion(reparacion3);
        valoracion1.setPuntuacion((short) 0);
        valoracion1.setComentario("c");
        valoracion1.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockValoracionRepo.save(any(Valoracion.class))).thenReturn(valoracion1);

        // Run the test
        final ValoracionResponse result = valoracionServiceUnderTest.crear(req, "firebaseUid");

        // Verify the results
    }

    @Test
    void testCrear_ClienteRepositoryReturnsAbsent() {
        // Setup
        final ValoracionRequest req = new ValoracionRequest();
        req.setReparacionId(0L);
        req.setPuntuacion((short) 0);
        req.setComentario("c");

        when(mockClienteRepo.findByUsuarioFirebaseUid("firebaseUid")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> valoracionServiceUnderTest.crear(req, "firebaseUid"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testCrear_ReparacionRepositoryReturnsAbsent() {
        // Setup
        final ValoracionRequest req = new ValoracionRequest();
        req.setReparacionId(0L);
        req.setPuntuacion((short) 0);
        req.setComentario("c");

        // Configure ClienteRepository.findByUsuarioFirebaseUid(...).
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente1.setUsuario(usuario);
        final Optional<Cliente> cliente = Optional.of(cliente1);
        when(mockClienteRepo.findByUsuarioFirebaseUid("firebaseUid")).thenReturn(cliente);

        when(mockReparacionRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> valoracionServiceUnderTest.crear(req, "firebaseUid"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testCrear_ValoracionRepositoryFindAllReturnsNoItems() {
        // Setup
        final ValoracionRequest req = new ValoracionRequest();
        req.setReparacionId(0L);
        req.setPuntuacion((short) 0);
        req.setComentario("c");

        // Configure ClienteRepository.findByUsuarioFirebaseUid(...).
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente1.setUsuario(usuario);
        final Optional<Cliente> cliente = Optional.of(cliente1);
        when(mockClienteRepo.findByUsuarioFirebaseUid("firebaseUid")).thenReturn(cliente);

        // Configure ReparacionRepository.findById(...).
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente2 = new Cliente();
        cliente2.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente2.setUsuario(usuario1);
        vehiculo.setCliente(cliente2);
        reparacion1.setVehiculo(vehiculo);
        reparacion1.setEstado("estado");
        final Optional<Reparacion> reparacion = Optional.of(reparacion1);
        when(mockReparacionRepo.findById(0L)).thenReturn(reparacion);

        when(mockValoracionRepo.findAll()).thenReturn(Collections.emptyList());

        // Configure ValoracionRepository.save(...).
        final Valoracion valoracion = new Valoracion();
        valoracion.setId(0L);
        final Cliente cliente3 = new Cliente();
        cliente3.setId(0L);
        final Usuario usuario2 = new Usuario();
        usuario2.setNombre("nombre");
        usuario2.setApellidos("apellidos");
        cliente3.setUsuario(usuario2);
        valoracion.setCliente(cliente3);
        final Reparacion reparacion2 = new Reparacion();
        reparacion2.setId(0L);
        final Vehiculo vehiculo1 = new Vehiculo();
        final Cliente cliente4 = new Cliente();
        cliente4.setId(0L);
        final Usuario usuario3 = new Usuario();
        usuario3.setNombre("nombre");
        usuario3.setApellidos("apellidos");
        cliente4.setUsuario(usuario3);
        vehiculo1.setCliente(cliente4);
        reparacion2.setVehiculo(vehiculo1);
        reparacion2.setEstado("estado");
        valoracion.setReparacion(reparacion2);
        valoracion.setPuntuacion((short) 0);
        valoracion.setComentario("c");
        valoracion.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockValoracionRepo.save(any(Valoracion.class))).thenReturn(valoracion);

        // Run the test
        final ValoracionResponse result = valoracionServiceUnderTest.crear(req, "firebaseUid");

        // Verify the results
    }

    @Test
    void testCrear_ValoracionRepositorySaveThrowsOptimisticLockingFailureException() {
        // Setup
        final ValoracionRequest req = new ValoracionRequest();
        req.setReparacionId(0L);
        req.setPuntuacion((short) 0);
        req.setComentario("c");

        // Configure ClienteRepository.findByUsuarioFirebaseUid(...).
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente1.setUsuario(usuario);
        final Optional<Cliente> cliente = Optional.of(cliente1);
        when(mockClienteRepo.findByUsuarioFirebaseUid("firebaseUid")).thenReturn(cliente);

        // Configure ReparacionRepository.findById(...).
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente2 = new Cliente();
        cliente2.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente2.setUsuario(usuario1);
        vehiculo.setCliente(cliente2);
        reparacion1.setVehiculo(vehiculo);
        reparacion1.setEstado("estado");
        final Optional<Reparacion> reparacion = Optional.of(reparacion1);
        when(mockReparacionRepo.findById(0L)).thenReturn(reparacion);

        // Configure ValoracionRepository.findAll(...).
        final Valoracion valoracion = new Valoracion();
        valoracion.setId(0L);
        final Cliente cliente3 = new Cliente();
        cliente3.setId(0L);
        final Usuario usuario2 = new Usuario();
        usuario2.setNombre("nombre");
        usuario2.setApellidos("apellidos");
        cliente3.setUsuario(usuario2);
        valoracion.setCliente(cliente3);
        final Reparacion reparacion2 = new Reparacion();
        reparacion2.setId(0L);
        final Vehiculo vehiculo1 = new Vehiculo();
        final Cliente cliente4 = new Cliente();
        cliente4.setId(0L);
        final Usuario usuario3 = new Usuario();
        usuario3.setNombre("nombre");
        usuario3.setApellidos("apellidos");
        cliente4.setUsuario(usuario3);
        vehiculo1.setCliente(cliente4);
        reparacion2.setVehiculo(vehiculo1);
        reparacion2.setEstado("estado");
        valoracion.setReparacion(reparacion2);
        valoracion.setPuntuacion((short) 0);
        valoracion.setComentario("c");
        valoracion.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<Valoracion> valoracions = List.of(valoracion);
        when(mockValoracionRepo.findAll()).thenReturn(valoracions);

        when(mockValoracionRepo.save(any(Valoracion.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> valoracionServiceUnderTest.crear(req, "firebaseUid"))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }
}
