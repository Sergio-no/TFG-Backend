package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.request.AddPiezaReparacionRequest;
import org.example.tfgbackend.dto.request.ReparacionRequest;
import org.example.tfgbackend.dto.response.ReparacionResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.model.*;
import org.example.tfgbackend.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReparacionServiceTest {

    @Mock
    private ReparacionRepository mockReparacionRepo;
    @Mock
    private VehiculoRepository mockVehiculoRepo;
    @Mock
    private MecanicoRepository mockMecanicoRepo;
    @Mock
    private CitaRepository mockCitaRepo;
    @Mock
    private PiezaRepository mockPiezaRepo;
    @Mock
    private ReparacionPiezaRepository mockRepPiezaRepo;
    @Mock
    private FacturaRepository mockFacturaRepo;
    @Mock
    private NotificationService mockNotificationService;

    @InjectMocks
    private ReparacionService reparacionServiceUnderTest;

    @Test
    void testGetAll() {
        // Setup
        // Configure ReparacionRepository.findAll(...).
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo.setCliente(cliente);
        vehiculo.setMarca("marca");
        vehiculo.setModelo("modelo");
        vehiculo.setMatricula("m");
        reparacion.setVehiculo(vehiculo);
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("nombre");
        mecanico.setApellidos("apellidos");
        reparacion.setMecanico(mecanico);
        final Cita cita = new Cita();
        cita.setId(0L);
        reparacion.setCita(cita);
        reparacion.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion.setEstado("estado");
        reparacion.setCosteTotal(new BigDecimal("0.00"));
        final List<Reparacion> reparacions = List.of(reparacion);
        when(mockReparacionRepo.findAll()).thenReturn(reparacions);

        // Run the test
        final List<ReparacionResponse> result = reparacionServiceUnderTest.getAll();

        // Verify the results
    }

    @Test
    void testGetAll_ReparacionRepositoryReturnsNoItems() {
        // Setup
        when(mockReparacionRepo.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<ReparacionResponse> result = reparacionServiceUnderTest.getAll();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetActivas() {
        // Setup
        // Configure ReparacionRepository.findActivas(...).
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo.setCliente(cliente);
        vehiculo.setMarca("marca");
        vehiculo.setModelo("modelo");
        vehiculo.setMatricula("m");
        reparacion.setVehiculo(vehiculo);
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("nombre");
        mecanico.setApellidos("apellidos");
        reparacion.setMecanico(mecanico);
        final Cita cita = new Cita();
        cita.setId(0L);
        reparacion.setCita(cita);
        reparacion.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion.setEstado("estado");
        reparacion.setCosteTotal(new BigDecimal("0.00"));
        final List<Reparacion> reparacions = List.of(reparacion);
        when(mockReparacionRepo.findActivas()).thenReturn(reparacions);

        // Run the test
        final List<ReparacionResponse> result = reparacionServiceUnderTest.getActivas();

        // Verify the results
    }

    @Test
    void testGetActivas_ReparacionRepositoryReturnsNoItems() {
        // Setup
        when(mockReparacionRepo.findActivas()).thenReturn(Collections.emptyList());

        // Run the test
        final List<ReparacionResponse> result = reparacionServiceUnderTest.getActivas();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetById() {
        // Setup
        // Configure ReparacionRepository.findById(...).
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo.setCliente(cliente);
        vehiculo.setMarca("marca");
        vehiculo.setModelo("modelo");
        vehiculo.setMatricula("m");
        reparacion1.setVehiculo(vehiculo);
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("nombre");
        mecanico.setApellidos("apellidos");
        reparacion1.setMecanico(mecanico);
        final Cita cita = new Cita();
        cita.setId(0L);
        reparacion1.setCita(cita);
        reparacion1.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion1.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion1.setEstado("estado");
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        final Optional<Reparacion> reparacion = Optional.of(reparacion1);
        when(mockReparacionRepo.findById(0L)).thenReturn(reparacion);

        // Run the test
        final ReparacionResponse result = reparacionServiceUnderTest.getById(0L);

        // Verify the results
    }

    @Test
    void testGetById_ReparacionRepositoryReturnsAbsent() {
        // Setup
        when(mockReparacionRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> reparacionServiceUnderTest.getById(0L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testGetByCliente() {
        // Setup
        // Configure ReparacionRepository.findByVehiculoClienteId(...).
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo.setCliente(cliente);
        vehiculo.setMarca("marca");
        vehiculo.setModelo("modelo");
        vehiculo.setMatricula("m");
        reparacion.setVehiculo(vehiculo);
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("nombre");
        mecanico.setApellidos("apellidos");
        reparacion.setMecanico(mecanico);
        final Cita cita = new Cita();
        cita.setId(0L);
        reparacion.setCita(cita);
        reparacion.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion.setEstado("estado");
        reparacion.setCosteTotal(new BigDecimal("0.00"));
        final List<Reparacion> reparacions = List.of(reparacion);
        when(mockReparacionRepo.findByVehiculoClienteId(0L)).thenReturn(reparacions);

        // Run the test
        final List<ReparacionResponse> result = reparacionServiceUnderTest.getByCliente(0L);

        // Verify the results
    }

    @Test
    void testGetByCliente_ReparacionRepositoryReturnsNoItems() {
        // Setup
        when(mockReparacionRepo.findByVehiculoClienteId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<ReparacionResponse> result = reparacionServiceUnderTest.getByCliente(0L);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testCrear() {
        // Setup
        final ReparacionRequest req = new ReparacionRequest();
        req.setVehiculoId(0L);
        req.setMecanicoId(0L);
        req.setCitaId(0L);
        req.setCosteInicial(new BigDecimal("0.00"));

        // Configure VehiculoRepository.findById(...).
        final Vehiculo vehiculo1 = new Vehiculo();
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo1.setCliente(cliente);
        vehiculo1.setMarca("marca");
        vehiculo1.setModelo("modelo");
        vehiculo1.setMatricula("m");
        final Optional<Vehiculo> vehiculo = Optional.of(vehiculo1);
        when(mockVehiculoRepo.findById(0L)).thenReturn(vehiculo);

        // Configure MecanicoRepository.findById(...).
        final Mecanico mecanico1 = new Mecanico();
        mecanico1.setId(0L);
        mecanico1.setNombre("nombre");
        mecanico1.setApellidos("apellidos");
        mecanico1.setEspecialidad("e");
        mecanico1.setTelefono("telefono");
        final Optional<Mecanico> mecanico = Optional.of(mecanico1);
        when(mockMecanicoRepo.findById(0L)).thenReturn(mecanico);

        // Configure CitaRepository.findById(...).
        final Cita cita1 = new Cita();
        cita1.setId(0L);
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setId(0L);
        cliente1.setUsuario(usuario1);
        cita1.setCliente(cliente1);
        final Optional<Cita> cita = Optional.of(cita1);
        when(mockCitaRepo.findById(0L)).thenReturn(cita);

        // Configure ReparacionRepository.save(...).
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Vehiculo vehiculo2 = new Vehiculo();
        final Cliente cliente2 = new Cliente();
        final Usuario usuario2 = new Usuario();
        usuario2.setNombre("nombre");
        usuario2.setApellidos("apellidos");
        cliente2.setUsuario(usuario2);
        vehiculo2.setCliente(cliente2);
        vehiculo2.setMarca("marca");
        vehiculo2.setModelo("modelo");
        vehiculo2.setMatricula("m");
        reparacion.setVehiculo(vehiculo2);
        final Mecanico mecanico2 = new Mecanico();
        mecanico2.setId(0L);
        mecanico2.setNombre("nombre");
        mecanico2.setApellidos("apellidos");
        reparacion.setMecanico(mecanico2);
        final Cita cita2 = new Cita();
        cita2.setId(0L);
        reparacion.setCita(cita2);
        reparacion.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion.setEstado("estado");
        reparacion.setCosteTotal(new BigDecimal("0.00"));
        when(mockReparacionRepo.save(any(Reparacion.class))).thenReturn(reparacion);

        // Run the test
        final ReparacionResponse result = reparacionServiceUnderTest.crear(req);

        // Verify the results
        verify(mockNotificationService).notificarReparacionCambioEstado(any(Usuario.class), eq("PRESENTADA"),
                eq("vehiculo"));
    }

    @Test
    void testCrear_VehiculoRepositoryReturnsAbsent() {
        // Setup
        final ReparacionRequest req = new ReparacionRequest();
        req.setVehiculoId(0L);
        req.setMecanicoId(0L);
        req.setCitaId(0L);
        req.setCosteInicial(new BigDecimal("0.00"));

        when(mockVehiculoRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> reparacionServiceUnderTest.crear(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testCrear_MecanicoRepositoryReturnsAbsent() {
        // Setup
        final ReparacionRequest req = new ReparacionRequest();
        req.setVehiculoId(0L);
        req.setMecanicoId(0L);
        req.setCitaId(0L);
        req.setCosteInicial(new BigDecimal("0.00"));

        // Configure VehiculoRepository.findById(...).
        final Vehiculo vehiculo1 = new Vehiculo();
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo1.setCliente(cliente);
        vehiculo1.setMarca("marca");
        vehiculo1.setModelo("modelo");
        vehiculo1.setMatricula("m");
        final Optional<Vehiculo> vehiculo = Optional.of(vehiculo1);
        when(mockVehiculoRepo.findById(0L)).thenReturn(vehiculo);

        when(mockMecanicoRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> reparacionServiceUnderTest.crear(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testCrear_CitaRepositoryReturnsAbsent() {
        // Setup
        final ReparacionRequest req = new ReparacionRequest();
        req.setVehiculoId(0L);
        req.setMecanicoId(0L);
        req.setCitaId(0L);
        req.setCosteInicial(new BigDecimal("0.00"));

        // Configure VehiculoRepository.findById(...).
        final Vehiculo vehiculo1 = new Vehiculo();
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo1.setCliente(cliente);
        vehiculo1.setMarca("marca");
        vehiculo1.setModelo("modelo");
        vehiculo1.setMatricula("m");
        final Optional<Vehiculo> vehiculo = Optional.of(vehiculo1);
        when(mockVehiculoRepo.findById(0L)).thenReturn(vehiculo);

        // Configure MecanicoRepository.findById(...).
        final Mecanico mecanico1 = new Mecanico();
        mecanico1.setId(0L);
        mecanico1.setNombre("nombre");
        mecanico1.setApellidos("apellidos");
        mecanico1.setEspecialidad("e");
        mecanico1.setTelefono("telefono");
        final Optional<Mecanico> mecanico = Optional.of(mecanico1);
        when(mockMecanicoRepo.findById(0L)).thenReturn(mecanico);

        when(mockCitaRepo.findById(0L)).thenReturn(Optional.empty());

        // Configure ReparacionRepository.save(...).
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Vehiculo vehiculo2 = new Vehiculo();
        final Cliente cliente1 = new Cliente();
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        vehiculo2.setCliente(cliente1);
        vehiculo2.setMarca("marca");
        vehiculo2.setModelo("modelo");
        vehiculo2.setMatricula("m");
        reparacion.setVehiculo(vehiculo2);
        final Mecanico mecanico2 = new Mecanico();
        mecanico2.setId(0L);
        mecanico2.setNombre("nombre");
        mecanico2.setApellidos("apellidos");
        reparacion.setMecanico(mecanico2);
        final Cita cita = new Cita();
        cita.setId(0L);
        reparacion.setCita(cita);
        reparacion.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion.setEstado("estado");
        reparacion.setCosteTotal(new BigDecimal("0.00"));
        when(mockReparacionRepo.save(any(Reparacion.class))).thenReturn(reparacion);

        // Run the test
        final ReparacionResponse result = reparacionServiceUnderTest.crear(req);

        // Verify the results
        verify(mockNotificationService).notificarReparacionCambioEstado(any(Usuario.class), eq("PRESENTADA"),
                eq("vehiculo"));
    }

    @Test
    void testCrear_ReparacionRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final ReparacionRequest req = new ReparacionRequest();
        req.setVehiculoId(0L);
        req.setMecanicoId(0L);
        req.setCitaId(0L);
        req.setCosteInicial(new BigDecimal("0.00"));

        // Configure VehiculoRepository.findById(...).
        final Vehiculo vehiculo1 = new Vehiculo();
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo1.setCliente(cliente);
        vehiculo1.setMarca("marca");
        vehiculo1.setModelo("modelo");
        vehiculo1.setMatricula("m");
        final Optional<Vehiculo> vehiculo = Optional.of(vehiculo1);
        when(mockVehiculoRepo.findById(0L)).thenReturn(vehiculo);

        // Configure MecanicoRepository.findById(...).
        final Mecanico mecanico1 = new Mecanico();
        mecanico1.setId(0L);
        mecanico1.setNombre("nombre");
        mecanico1.setApellidos("apellidos");
        mecanico1.setEspecialidad("e");
        mecanico1.setTelefono("telefono");
        final Optional<Mecanico> mecanico = Optional.of(mecanico1);
        when(mockMecanicoRepo.findById(0L)).thenReturn(mecanico);

        // Configure CitaRepository.findById(...).
        final Cita cita1 = new Cita();
        cita1.setId(0L);
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setId(0L);
        cliente1.setUsuario(usuario1);
        cita1.setCliente(cliente1);
        final Optional<Cita> cita = Optional.of(cita1);
        when(mockCitaRepo.findById(0L)).thenReturn(cita);

        when(mockReparacionRepo.save(any(Reparacion.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> reparacionServiceUnderTest.crear(req))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testCambiarEstado() {
        // Setup
        // Configure ReparacionRepository.findById(...).
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo.setCliente(cliente);
        vehiculo.setMarca("marca");
        vehiculo.setModelo("modelo");
        vehiculo.setMatricula("m");
        reparacion1.setVehiculo(vehiculo);
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("nombre");
        mecanico.setApellidos("apellidos");
        reparacion1.setMecanico(mecanico);
        final Cita cita = new Cita();
        cita.setId(0L);
        reparacion1.setCita(cita);
        reparacion1.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion1.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion1.setEstado("estado");
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        final Optional<Reparacion> reparacion = Optional.of(reparacion1);
        when(mockReparacionRepo.findById(0L)).thenReturn(reparacion);

        // Configure ReparacionRepository.save(...).
        final Reparacion reparacion2 = new Reparacion();
        reparacion2.setId(0L);
        final Vehiculo vehiculo1 = new Vehiculo();
        final Cliente cliente1 = new Cliente();
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        vehiculo1.setCliente(cliente1);
        vehiculo1.setMarca("marca");
        vehiculo1.setModelo("modelo");
        vehiculo1.setMatricula("m");
        reparacion2.setVehiculo(vehiculo1);
        final Mecanico mecanico1 = new Mecanico();
        mecanico1.setId(0L);
        mecanico1.setNombre("nombre");
        mecanico1.setApellidos("apellidos");
        reparacion2.setMecanico(mecanico1);
        final Cita cita1 = new Cita();
        cita1.setId(0L);
        reparacion2.setCita(cita1);
        reparacion2.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion2.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion2.setEstado("estado");
        reparacion2.setCosteTotal(new BigDecimal("0.00"));
        when(mockReparacionRepo.save(any(Reparacion.class))).thenReturn(reparacion2);

        // Configure FacturaRepository.findAll(...).
        final Factura factura = new Factura();
        final Cliente cliente2 = new Cliente();
        final Usuario usuario2 = new Usuario();
        usuario2.setNombre("nombre");
        usuario2.setApellidos("apellidos");
        cliente2.setUsuario(usuario2);
        factura.setCliente(cliente2);
        final Reparacion reparacion3 = new Reparacion();
        reparacion3.setId(0L);
        final Vehiculo vehiculo2 = new Vehiculo();
        final Cliente cliente3 = new Cliente();
        final Usuario usuario3 = new Usuario();
        usuario3.setNombre("nombre");
        usuario3.setApellidos("apellidos");
        cliente3.setUsuario(usuario3);
        vehiculo2.setCliente(cliente3);
        vehiculo2.setMarca("marca");
        vehiculo2.setModelo("modelo");
        vehiculo2.setMatricula("m");
        reparacion3.setVehiculo(vehiculo2);
        final Mecanico mecanico2 = new Mecanico();
        mecanico2.setId(0L);
        mecanico2.setNombre("nombre");
        mecanico2.setApellidos("apellidos");
        reparacion3.setMecanico(mecanico2);
        final Cita cita2 = new Cita();
        cita2.setId(0L);
        reparacion3.setCita(cita2);
        reparacion3.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion3.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion3.setEstado("estado");
        reparacion3.setCosteTotal(new BigDecimal("0.00"));
        factura.setReparacion(reparacion3);
        factura.setTotal(new BigDecimal("0.00"));
        factura.setPagada(false);
        factura.setNumeroFactura("n");
        final List<Factura> facturas = List.of(factura);
        when(mockFacturaRepo.findAll()).thenReturn(facturas);

        when(mockFacturaRepo.count()).thenReturn(0L);

        // Run the test
        final ReparacionResponse result = reparacionServiceUnderTest.cambiarEstado(0L, "estado");

        // Verify the results
        verify(mockFacturaRepo).save(any(Factura.class));
        verify(mockNotificationService).notificarFacturaGenerada(any(Usuario.class), eq("numeroFactura"), eq("total"));
        verify(mockNotificationService).notificarReparacionCambioEstado(any(Usuario.class), eq("estado"),
                eq("vehiculo"));
    }

    @Test
    void testCambiarEstado_ReparacionRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockReparacionRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> reparacionServiceUnderTest.cambiarEstado(0L, "estado"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testCambiarEstado_ReparacionRepositorySaveThrowsOptimisticLockingFailureException() {
        // Setup
        // Configure ReparacionRepository.findById(...).
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo.setCliente(cliente);
        vehiculo.setMarca("marca");
        vehiculo.setModelo("modelo");
        vehiculo.setMatricula("m");
        reparacion1.setVehiculo(vehiculo);
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("nombre");
        mecanico.setApellidos("apellidos");
        reparacion1.setMecanico(mecanico);
        final Cita cita = new Cita();
        cita.setId(0L);
        reparacion1.setCita(cita);
        reparacion1.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion1.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion1.setEstado("estado");
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        final Optional<Reparacion> reparacion = Optional.of(reparacion1);
        when(mockReparacionRepo.findById(0L)).thenReturn(reparacion);

        when(mockReparacionRepo.save(any(Reparacion.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> reparacionServiceUnderTest.cambiarEstado(0L, "estado"))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testCambiarEstado_FacturaRepositoryFindAllReturnsNoItems() {
        // Setup
        // Configure ReparacionRepository.findById(...).
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo.setCliente(cliente);
        vehiculo.setMarca("marca");
        vehiculo.setModelo("modelo");
        vehiculo.setMatricula("m");
        reparacion1.setVehiculo(vehiculo);
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("nombre");
        mecanico.setApellidos("apellidos");
        reparacion1.setMecanico(mecanico);
        final Cita cita = new Cita();
        cita.setId(0L);
        reparacion1.setCita(cita);
        reparacion1.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion1.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion1.setEstado("estado");
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        final Optional<Reparacion> reparacion = Optional.of(reparacion1);
        when(mockReparacionRepo.findById(0L)).thenReturn(reparacion);

        // Configure ReparacionRepository.save(...).
        final Reparacion reparacion2 = new Reparacion();
        reparacion2.setId(0L);
        final Vehiculo vehiculo1 = new Vehiculo();
        final Cliente cliente1 = new Cliente();
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        vehiculo1.setCliente(cliente1);
        vehiculo1.setMarca("marca");
        vehiculo1.setModelo("modelo");
        vehiculo1.setMatricula("m");
        reparacion2.setVehiculo(vehiculo1);
        final Mecanico mecanico1 = new Mecanico();
        mecanico1.setId(0L);
        mecanico1.setNombre("nombre");
        mecanico1.setApellidos("apellidos");
        reparacion2.setMecanico(mecanico1);
        final Cita cita1 = new Cita();
        cita1.setId(0L);
        reparacion2.setCita(cita1);
        reparacion2.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion2.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion2.setEstado("estado");
        reparacion2.setCosteTotal(new BigDecimal("0.00"));
        when(mockReparacionRepo.save(any(Reparacion.class))).thenReturn(reparacion2);

        when(mockFacturaRepo.findAll()).thenReturn(Collections.emptyList());
        when(mockFacturaRepo.count()).thenReturn(0L);

        // Run the test
        final ReparacionResponse result = reparacionServiceUnderTest.cambiarEstado(0L, "estado");

        // Verify the results
        verify(mockFacturaRepo).save(any(Factura.class));
        verify(mockNotificationService).notificarFacturaGenerada(any(Usuario.class), eq("numeroFactura"), eq("total"));
        verify(mockNotificationService).notificarReparacionCambioEstado(any(Usuario.class), eq("estado"),
                eq("vehiculo"));
    }

    @Test
    void testCambiarEstado_FacturaRepositorySaveThrowsOptimisticLockingFailureException() {
        // Setup
        // Configure ReparacionRepository.findById(...).
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo.setCliente(cliente);
        vehiculo.setMarca("marca");
        vehiculo.setModelo("modelo");
        vehiculo.setMatricula("m");
        reparacion1.setVehiculo(vehiculo);
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("nombre");
        mecanico.setApellidos("apellidos");
        reparacion1.setMecanico(mecanico);
        final Cita cita = new Cita();
        cita.setId(0L);
        reparacion1.setCita(cita);
        reparacion1.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion1.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion1.setEstado("estado");
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        final Optional<Reparacion> reparacion = Optional.of(reparacion1);
        when(mockReparacionRepo.findById(0L)).thenReturn(reparacion);

        // Configure ReparacionRepository.save(...).
        final Reparacion reparacion2 = new Reparacion();
        reparacion2.setId(0L);
        final Vehiculo vehiculo1 = new Vehiculo();
        final Cliente cliente1 = new Cliente();
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        vehiculo1.setCliente(cliente1);
        vehiculo1.setMarca("marca");
        vehiculo1.setModelo("modelo");
        vehiculo1.setMatricula("m");
        reparacion2.setVehiculo(vehiculo1);
        final Mecanico mecanico1 = new Mecanico();
        mecanico1.setId(0L);
        mecanico1.setNombre("nombre");
        mecanico1.setApellidos("apellidos");
        reparacion2.setMecanico(mecanico1);
        final Cita cita1 = new Cita();
        cita1.setId(0L);
        reparacion2.setCita(cita1);
        reparacion2.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion2.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion2.setEstado("estado");
        reparacion2.setCosteTotal(new BigDecimal("0.00"));
        when(mockReparacionRepo.save(any(Reparacion.class))).thenReturn(reparacion2);

        // Configure FacturaRepository.findAll(...).
        final Factura factura = new Factura();
        final Cliente cliente2 = new Cliente();
        final Usuario usuario2 = new Usuario();
        usuario2.setNombre("nombre");
        usuario2.setApellidos("apellidos");
        cliente2.setUsuario(usuario2);
        factura.setCliente(cliente2);
        final Reparacion reparacion3 = new Reparacion();
        reparacion3.setId(0L);
        final Vehiculo vehiculo2 = new Vehiculo();
        final Cliente cliente3 = new Cliente();
        final Usuario usuario3 = new Usuario();
        usuario3.setNombre("nombre");
        usuario3.setApellidos("apellidos");
        cliente3.setUsuario(usuario3);
        vehiculo2.setCliente(cliente3);
        vehiculo2.setMarca("marca");
        vehiculo2.setModelo("modelo");
        vehiculo2.setMatricula("m");
        reparacion3.setVehiculo(vehiculo2);
        final Mecanico mecanico2 = new Mecanico();
        mecanico2.setId(0L);
        mecanico2.setNombre("nombre");
        mecanico2.setApellidos("apellidos");
        reparacion3.setMecanico(mecanico2);
        final Cita cita2 = new Cita();
        cita2.setId(0L);
        reparacion3.setCita(cita2);
        reparacion3.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion3.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion3.setEstado("estado");
        reparacion3.setCosteTotal(new BigDecimal("0.00"));
        factura.setReparacion(reparacion3);
        factura.setTotal(new BigDecimal("0.00"));
        factura.setPagada(false);
        factura.setNumeroFactura("n");
        final List<Factura> facturas = List.of(factura);
        when(mockFacturaRepo.findAll()).thenReturn(facturas);

        when(mockFacturaRepo.count()).thenReturn(0L);
        when(mockFacturaRepo.save(any(Factura.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> reparacionServiceUnderTest.cambiarEstado(0L, "estado"))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testAddPieza() {
        // Setup
        final AddPiezaReparacionRequest req = new AddPiezaReparacionRequest();
        req.setCantidadUsada(0);
        req.setPiezaId(0L);

        // Configure ReparacionRepository.findById(...).
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo.setCliente(cliente);
        vehiculo.setMarca("marca");
        vehiculo.setModelo("modelo");
        vehiculo.setMatricula("m");
        reparacion1.setVehiculo(vehiculo);
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("nombre");
        mecanico.setApellidos("apellidos");
        reparacion1.setMecanico(mecanico);
        final Cita cita = new Cita();
        cita.setId(0L);
        reparacion1.setCita(cita);
        reparacion1.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion1.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion1.setEstado("estado");
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        final Optional<Reparacion> reparacion = Optional.of(reparacion1);
        when(mockReparacionRepo.findById(0L)).thenReturn(reparacion);

        // Configure PiezaRepository.findById(...).
        final Pieza pieza1 = new Pieza();
        pieza1.setId(0L);
        pieza1.setNombre("nombre");
        pieza1.setDescripcion("d");
        pieza1.setPrecioUnitario(new BigDecimal("0.00"));
        pieza1.setStockActual(0);
        final Optional<Pieza> pieza = Optional.of(pieza1);
        when(mockPiezaRepo.findById(0L)).thenReturn(pieza);

        // Configure ReparacionRepository.save(...).
        final Reparacion reparacion2 = new Reparacion();
        reparacion2.setId(0L);
        final Vehiculo vehiculo1 = new Vehiculo();
        final Cliente cliente1 = new Cliente();
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        vehiculo1.setCliente(cliente1);
        vehiculo1.setMarca("marca");
        vehiculo1.setModelo("modelo");
        vehiculo1.setMatricula("m");
        reparacion2.setVehiculo(vehiculo1);
        final Mecanico mecanico1 = new Mecanico();
        mecanico1.setId(0L);
        mecanico1.setNombre("nombre");
        mecanico1.setApellidos("apellidos");
        reparacion2.setMecanico(mecanico1);
        final Cita cita1 = new Cita();
        cita1.setId(0L);
        reparacion2.setCita(cita1);
        reparacion2.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion2.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion2.setEstado("estado");
        reparacion2.setCosteTotal(new BigDecimal("0.00"));
        when(mockReparacionRepo.save(any(Reparacion.class))).thenReturn(reparacion2);

        // Run the test
        final ReparacionResponse result = reparacionServiceUnderTest.addPieza(0L, req);

        // Verify the results
        verify(mockPiezaRepo).save(any(Pieza.class));
        verify(mockRepPiezaRepo).save(any(ReparacionPieza.class));
    }

    @Test
    void testAddPieza_ReparacionRepositoryFindByIdReturnsAbsent() {
        // Setup
        final AddPiezaReparacionRequest req = new AddPiezaReparacionRequest();
        req.setCantidadUsada(0);
        req.setPiezaId(0L);

        when(mockReparacionRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> reparacionServiceUnderTest.addPieza(0L, req))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testAddPieza_PiezaRepositoryFindByIdReturnsAbsent() {
        // Setup
        final AddPiezaReparacionRequest req = new AddPiezaReparacionRequest();
        req.setCantidadUsada(0);
        req.setPiezaId(0L);

        // Configure ReparacionRepository.findById(...).
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo.setCliente(cliente);
        vehiculo.setMarca("marca");
        vehiculo.setModelo("modelo");
        vehiculo.setMatricula("m");
        reparacion1.setVehiculo(vehiculo);
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("nombre");
        mecanico.setApellidos("apellidos");
        reparacion1.setMecanico(mecanico);
        final Cita cita = new Cita();
        cita.setId(0L);
        reparacion1.setCita(cita);
        reparacion1.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion1.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion1.setEstado("estado");
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        final Optional<Reparacion> reparacion = Optional.of(reparacion1);
        when(mockReparacionRepo.findById(0L)).thenReturn(reparacion);

        when(mockPiezaRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> reparacionServiceUnderTest.addPieza(0L, req))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testAddPieza_PiezaRepositorySaveThrowsOptimisticLockingFailureException() {
        // Setup
        final AddPiezaReparacionRequest req = new AddPiezaReparacionRequest();
        req.setCantidadUsada(0);
        req.setPiezaId(0L);

        // Configure ReparacionRepository.findById(...).
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo.setCliente(cliente);
        vehiculo.setMarca("marca");
        vehiculo.setModelo("modelo");
        vehiculo.setMatricula("m");
        reparacion1.setVehiculo(vehiculo);
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("nombre");
        mecanico.setApellidos("apellidos");
        reparacion1.setMecanico(mecanico);
        final Cita cita = new Cita();
        cita.setId(0L);
        reparacion1.setCita(cita);
        reparacion1.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion1.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion1.setEstado("estado");
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        final Optional<Reparacion> reparacion = Optional.of(reparacion1);
        when(mockReparacionRepo.findById(0L)).thenReturn(reparacion);

        // Configure PiezaRepository.findById(...).
        final Pieza pieza1 = new Pieza();
        pieza1.setId(0L);
        pieza1.setNombre("nombre");
        pieza1.setDescripcion("d");
        pieza1.setPrecioUnitario(new BigDecimal("0.00"));
        pieza1.setStockActual(0);
        final Optional<Pieza> pieza = Optional.of(pieza1);
        when(mockPiezaRepo.findById(0L)).thenReturn(pieza);

        when(mockPiezaRepo.save(any(Pieza.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> reparacionServiceUnderTest.addPieza(0L, req))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testAddPieza_ReparacionPiezaRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final AddPiezaReparacionRequest req = new AddPiezaReparacionRequest();
        req.setCantidadUsada(0);
        req.setPiezaId(0L);

        // Configure ReparacionRepository.findById(...).
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo.setCliente(cliente);
        vehiculo.setMarca("marca");
        vehiculo.setModelo("modelo");
        vehiculo.setMatricula("m");
        reparacion1.setVehiculo(vehiculo);
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("nombre");
        mecanico.setApellidos("apellidos");
        reparacion1.setMecanico(mecanico);
        final Cita cita = new Cita();
        cita.setId(0L);
        reparacion1.setCita(cita);
        reparacion1.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion1.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion1.setEstado("estado");
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        final Optional<Reparacion> reparacion = Optional.of(reparacion1);
        when(mockReparacionRepo.findById(0L)).thenReturn(reparacion);

        // Configure PiezaRepository.findById(...).
        final Pieza pieza1 = new Pieza();
        pieza1.setId(0L);
        pieza1.setNombre("nombre");
        pieza1.setDescripcion("d");
        pieza1.setPrecioUnitario(new BigDecimal("0.00"));
        pieza1.setStockActual(0);
        final Optional<Pieza> pieza = Optional.of(pieza1);
        when(mockPiezaRepo.findById(0L)).thenReturn(pieza);

        when(mockRepPiezaRepo.save(any(ReparacionPieza.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> reparacionServiceUnderTest.addPieza(0L, req))
                .isInstanceOf(OptimisticLockingFailureException.class);
        verify(mockPiezaRepo).save(any(Pieza.class));
    }

    @Test
    void testAddPieza_ReparacionRepositorySaveThrowsOptimisticLockingFailureException() {
        // Setup
        final AddPiezaReparacionRequest req = new AddPiezaReparacionRequest();
        req.setCantidadUsada(0);
        req.setPiezaId(0L);

        // Configure ReparacionRepository.findById(...).
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        vehiculo.setCliente(cliente);
        vehiculo.setMarca("marca");
        vehiculo.setModelo("modelo");
        vehiculo.setMatricula("m");
        reparacion1.setVehiculo(vehiculo);
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("nombre");
        mecanico.setApellidos("apellidos");
        reparacion1.setMecanico(mecanico);
        final Cita cita = new Cita();
        cita.setId(0L);
        reparacion1.setCita(cita);
        reparacion1.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion1.setFechaFin(LocalDate.of(2020, 1, 1));
        reparacion1.setEstado("estado");
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        final Optional<Reparacion> reparacion = Optional.of(reparacion1);
        when(mockReparacionRepo.findById(0L)).thenReturn(reparacion);

        // Configure PiezaRepository.findById(...).
        final Pieza pieza1 = new Pieza();
        pieza1.setId(0L);
        pieza1.setNombre("nombre");
        pieza1.setDescripcion("d");
        pieza1.setPrecioUnitario(new BigDecimal("0.00"));
        pieza1.setStockActual(0);
        final Optional<Pieza> pieza = Optional.of(pieza1);
        when(mockPiezaRepo.findById(0L)).thenReturn(pieza);

        when(mockReparacionRepo.save(any(Reparacion.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> reparacionServiceUnderTest.addPieza(0L, req))
                .isInstanceOf(OptimisticLockingFailureException.class);
        verify(mockPiezaRepo).save(any(Pieza.class));
        verify(mockRepPiezaRepo).save(any(ReparacionPieza.class));
    }
}
