package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.request.PagoRequest;
import org.example.tfgbackend.dto.response.FacturaResponse;
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
import java.time.LocalDateTime;
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
class FacturaServiceTest {

    @Mock
    private FacturaRepository mockFacturaRepo;
    @Mock
    private ReparacionRepository mockReparacionRepo;
    @Mock
    private ClienteRepository mockClienteRepo;
    @Mock
    private OficinaRepository mockOficinaRepo;
    @Mock
    private PuntosHistorialRepository mockPuntosRepo;
    @Mock
    private NotificationService mockNotificationService;

    @InjectMocks
    private FacturaService facturaServiceUnderTest;

    @Test
    void testGetAll() {
        // Setup
        // Configure FacturaRepository.findAll(...).
        final Factura factura = new Factura();
        factura.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        cliente.setPuntosAcumulados(0);
        cliente.setTotalGastado(new BigDecimal("0.00"));
        factura.setCliente(cliente);
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        cliente1.setPuntosAcumulados(0);
        cliente1.setTotalGastado(new BigDecimal("0.00"));
        vehiculo.setCliente(cliente1);
        reparacion.setVehiculo(vehiculo);
        reparacion.setCosteTotal(new BigDecimal("0.00"));
        factura.setReparacion(reparacion);
        final Oficina oficina = new Oficina();
        factura.setOficina(oficina);
        factura.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura.setTotal(new BigDecimal("0.00"));
        factura.setPagada(false);
        factura.setNumeroFactura("n");
        factura.setMetodoPago("m");
        factura.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<Factura> facturas = List.of(factura);
        when(mockFacturaRepo.findAll()).thenReturn(facturas);

        // Run the test
        final List<FacturaResponse> result = facturaServiceUnderTest.getAll();

        // Verify the results
    }

    @Test
    void testGetAll_FacturaRepositoryReturnsNoItems() {
        // Setup
        when(mockFacturaRepo.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<FacturaResponse> result = facturaServiceUnderTest.getAll();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetById() {
        // Setup
        // Configure FacturaRepository.findById(...).
        final Factura factura1 = new Factura();
        factura1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        cliente.setPuntosAcumulados(0);
        cliente.setTotalGastado(new BigDecimal("0.00"));
        factura1.setCliente(cliente);
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        cliente1.setPuntosAcumulados(0);
        cliente1.setTotalGastado(new BigDecimal("0.00"));
        vehiculo.setCliente(cliente1);
        reparacion.setVehiculo(vehiculo);
        reparacion.setCosteTotal(new BigDecimal("0.00"));
        factura1.setReparacion(reparacion);
        final Oficina oficina = new Oficina();
        factura1.setOficina(oficina);
        factura1.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura1.setTotal(new BigDecimal("0.00"));
        factura1.setPagada(false);
        factura1.setNumeroFactura("n");
        factura1.setMetodoPago("m");
        factura1.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<Factura> factura = Optional.of(factura1);
        when(mockFacturaRepo.findById(0L)).thenReturn(factura);

        // Run the test
        final FacturaResponse result = facturaServiceUnderTest.getById(0L);

        // Verify the results
    }

    @Test
    void testGetById_FacturaRepositoryReturnsAbsent() {
        // Setup
        when(mockFacturaRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> facturaServiceUnderTest.getById(0L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testGetByCliente() {
        // Setup
        // Configure FacturaRepository.findByClienteId(...).
        final Factura factura = new Factura();
        factura.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        cliente.setPuntosAcumulados(0);
        cliente.setTotalGastado(new BigDecimal("0.00"));
        factura.setCliente(cliente);
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        cliente1.setPuntosAcumulados(0);
        cliente1.setTotalGastado(new BigDecimal("0.00"));
        vehiculo.setCliente(cliente1);
        reparacion.setVehiculo(vehiculo);
        reparacion.setCosteTotal(new BigDecimal("0.00"));
        factura.setReparacion(reparacion);
        final Oficina oficina = new Oficina();
        factura.setOficina(oficina);
        factura.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura.setTotal(new BigDecimal("0.00"));
        factura.setPagada(false);
        factura.setNumeroFactura("n");
        factura.setMetodoPago("m");
        factura.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<Factura> facturas = List.of(factura);
        when(mockFacturaRepo.findByClienteId(0L)).thenReturn(facturas);

        // Run the test
        final List<FacturaResponse> result = facturaServiceUnderTest.getByCliente(0L);

        // Verify the results
    }

    @Test
    void testGetByCliente_FacturaRepositoryReturnsNoItems() {
        // Setup
        when(mockFacturaRepo.findByClienteId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<FacturaResponse> result = facturaServiceUnderTest.getByCliente(0L);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGenerarDesdeReparacion() {
        // Setup
        // Configure ReparacionRepository.findById(...).
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        cliente.setPuntosAcumulados(0);
        cliente.setTotalGastado(new BigDecimal("0.00"));
        vehiculo.setCliente(cliente);
        reparacion1.setVehiculo(vehiculo);
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        final Optional<Reparacion> reparacion = Optional.of(reparacion1);
        when(mockReparacionRepo.findById(0L)).thenReturn(reparacion);

        when(mockFacturaRepo.existsByReparacionId(0L)).thenReturn(false);

        // Configure OficinaRepository.findByUsuarioFirebaseUid(...).
        final Oficina oficina1 = new Oficina();
        oficina1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setId(0L);
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        oficina1.setUsuario(usuario1);
        final Optional<Oficina> oficina = Optional.of(oficina1);
        when(mockOficinaRepo.findByUsuarioFirebaseUid("firebaseUid")).thenReturn(oficina);

        when(mockFacturaRepo.count()).thenReturn(0L);

        // Configure FacturaRepository.save(...).
        final Factura factura = new Factura();
        factura.setId(0L);
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario2 = new Usuario();
        usuario2.setNombre("nombre");
        usuario2.setApellidos("apellidos");
        cliente1.setUsuario(usuario2);
        cliente1.setPuntosAcumulados(0);
        cliente1.setTotalGastado(new BigDecimal("0.00"));
        factura.setCliente(cliente1);
        final Reparacion reparacion2 = new Reparacion();
        reparacion2.setId(0L);
        final Vehiculo vehiculo1 = new Vehiculo();
        final Cliente cliente2 = new Cliente();
        cliente2.setId(0L);
        final Usuario usuario3 = new Usuario();
        usuario3.setNombre("nombre");
        usuario3.setApellidos("apellidos");
        cliente2.setUsuario(usuario3);
        cliente2.setPuntosAcumulados(0);
        cliente2.setTotalGastado(new BigDecimal("0.00"));
        vehiculo1.setCliente(cliente2);
        reparacion2.setVehiculo(vehiculo1);
        reparacion2.setCosteTotal(new BigDecimal("0.00"));
        factura.setReparacion(reparacion2);
        final Oficina oficina2 = new Oficina();
        factura.setOficina(oficina2);
        factura.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura.setTotal(new BigDecimal("0.00"));
        factura.setPagada(false);
        factura.setNumeroFactura("n");
        factura.setMetodoPago("m");
        factura.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockFacturaRepo.save(any(Factura.class))).thenReturn(factura);

        // Run the test
        final FacturaResponse result = facturaServiceUnderTest.generarDesdeReparacion(0L, "firebaseUid");

        // Verify the results
        verify(mockNotificationService).notificarFacturaGenerada(any(Usuario.class), eq("numeroFactura"), eq("total"));
    }

    @Test
    void testGenerarDesdeReparacion_ReparacionRepositoryReturnsAbsent() {
        // Setup
        when(mockReparacionRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> facturaServiceUnderTest.generarDesdeReparacion(0L, "firebaseUid"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testGenerarDesdeReparacion_FacturaRepositoryExistsByReparacionIdReturnsTrue() {
        // Setup
        // Configure ReparacionRepository.findById(...).
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        cliente.setPuntosAcumulados(0);
        cliente.setTotalGastado(new BigDecimal("0.00"));
        vehiculo.setCliente(cliente);
        reparacion1.setVehiculo(vehiculo);
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        final Optional<Reparacion> reparacion = Optional.of(reparacion1);
        when(mockReparacionRepo.findById(0L)).thenReturn(reparacion);

        when(mockFacturaRepo.existsByReparacionId(0L)).thenReturn(true);

        // Run the test
        assertThatThrownBy(() -> facturaServiceUnderTest.generarDesdeReparacion(0L, "firebaseUid"))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void testGenerarDesdeReparacion_OficinaRepositoryReturnsAbsent() {
        // Setup
        // Configure ReparacionRepository.findById(...).
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        cliente.setPuntosAcumulados(0);
        cliente.setTotalGastado(new BigDecimal("0.00"));
        vehiculo.setCliente(cliente);
        reparacion1.setVehiculo(vehiculo);
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        final Optional<Reparacion> reparacion = Optional.of(reparacion1);
        when(mockReparacionRepo.findById(0L)).thenReturn(reparacion);

        when(mockFacturaRepo.existsByReparacionId(0L)).thenReturn(false);
        when(mockOficinaRepo.findByUsuarioFirebaseUid("firebaseUid")).thenReturn(Optional.empty());
        when(mockFacturaRepo.count()).thenReturn(0L);

        // Configure FacturaRepository.save(...).
        final Factura factura = new Factura();
        factura.setId(0L);
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        cliente1.setPuntosAcumulados(0);
        cliente1.setTotalGastado(new BigDecimal("0.00"));
        factura.setCliente(cliente1);
        final Reparacion reparacion2 = new Reparacion();
        reparacion2.setId(0L);
        final Vehiculo vehiculo1 = new Vehiculo();
        final Cliente cliente2 = new Cliente();
        cliente2.setId(0L);
        final Usuario usuario2 = new Usuario();
        usuario2.setNombre("nombre");
        usuario2.setApellidos("apellidos");
        cliente2.setUsuario(usuario2);
        cliente2.setPuntosAcumulados(0);
        cliente2.setTotalGastado(new BigDecimal("0.00"));
        vehiculo1.setCliente(cliente2);
        reparacion2.setVehiculo(vehiculo1);
        reparacion2.setCosteTotal(new BigDecimal("0.00"));
        factura.setReparacion(reparacion2);
        final Oficina oficina = new Oficina();
        factura.setOficina(oficina);
        factura.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura.setTotal(new BigDecimal("0.00"));
        factura.setPagada(false);
        factura.setNumeroFactura("n");
        factura.setMetodoPago("m");
        factura.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockFacturaRepo.save(any(Factura.class))).thenReturn(factura);

        // Run the test
        final FacturaResponse result = facturaServiceUnderTest.generarDesdeReparacion(0L, "firebaseUid");

        // Verify the results
        verify(mockNotificationService).notificarFacturaGenerada(any(Usuario.class), eq("numeroFactura"), eq("total"));
    }

    @Test
    void testGenerarDesdeReparacion_FacturaRepositorySaveThrowsOptimisticLockingFailureException() {
        // Setup
        // Configure ReparacionRepository.findById(...).
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        cliente.setPuntosAcumulados(0);
        cliente.setTotalGastado(new BigDecimal("0.00"));
        vehiculo.setCliente(cliente);
        reparacion1.setVehiculo(vehiculo);
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        final Optional<Reparacion> reparacion = Optional.of(reparacion1);
        when(mockReparacionRepo.findById(0L)).thenReturn(reparacion);

        when(mockFacturaRepo.existsByReparacionId(0L)).thenReturn(false);

        // Configure OficinaRepository.findByUsuarioFirebaseUid(...).
        final Oficina oficina1 = new Oficina();
        oficina1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setId(0L);
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        oficina1.setUsuario(usuario1);
        final Optional<Oficina> oficina = Optional.of(oficina1);
        when(mockOficinaRepo.findByUsuarioFirebaseUid("firebaseUid")).thenReturn(oficina);

        when(mockFacturaRepo.count()).thenReturn(0L);
        when(mockFacturaRepo.save(any(Factura.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> facturaServiceUnderTest.generarDesdeReparacion(0L, "firebaseUid"))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testGetByClienteId() {
        // Setup
        // Configure FacturaRepository.findByClienteId(...).
        final Factura factura = new Factura();
        factura.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        cliente.setPuntosAcumulados(0);
        cliente.setTotalGastado(new BigDecimal("0.00"));
        factura.setCliente(cliente);
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        cliente1.setPuntosAcumulados(0);
        cliente1.setTotalGastado(new BigDecimal("0.00"));
        vehiculo.setCliente(cliente1);
        reparacion.setVehiculo(vehiculo);
        reparacion.setCosteTotal(new BigDecimal("0.00"));
        factura.setReparacion(reparacion);
        final Oficina oficina = new Oficina();
        factura.setOficina(oficina);
        factura.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura.setTotal(new BigDecimal("0.00"));
        factura.setPagada(false);
        factura.setNumeroFactura("n");
        factura.setMetodoPago("m");
        factura.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<Factura> facturas = List.of(factura);
        when(mockFacturaRepo.findByClienteId(0L)).thenReturn(facturas);

        // Run the test
        final List<FacturaResponse> result = facturaServiceUnderTest.getByClienteId(0L);

        // Verify the results
    }

    @Test
    void testGetByClienteId_FacturaRepositoryReturnsNoItems() {
        // Setup
        when(mockFacturaRepo.findByClienteId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<FacturaResponse> result = facturaServiceUnderTest.getByClienteId(0L);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testMarcarPagada() {
        // Setup
        final PagoRequest req = new PagoRequest();
        req.setMetodoPago("m");

        // Configure FacturaRepository.findById(...).
        final Factura factura1 = new Factura();
        factura1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        cliente.setPuntosAcumulados(0);
        cliente.setTotalGastado(new BigDecimal("0.00"));
        factura1.setCliente(cliente);
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        cliente1.setPuntosAcumulados(0);
        cliente1.setTotalGastado(new BigDecimal("0.00"));
        vehiculo.setCliente(cliente1);
        reparacion.setVehiculo(vehiculo);
        reparacion.setCosteTotal(new BigDecimal("0.00"));
        factura1.setReparacion(reparacion);
        final Oficina oficina = new Oficina();
        factura1.setOficina(oficina);
        factura1.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura1.setTotal(new BigDecimal("0.00"));
        factura1.setPagada(false);
        factura1.setNumeroFactura("n");
        factura1.setMetodoPago("m");
        factura1.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<Factura> factura = Optional.of(factura1);
        when(mockFacturaRepo.findById(0L)).thenReturn(factura);

        // Configure FacturaRepository.save(...).
        final Factura factura2 = new Factura();
        factura2.setId(0L);
        final Cliente cliente2 = new Cliente();
        cliente2.setId(0L);
        final Usuario usuario2 = new Usuario();
        usuario2.setNombre("nombre");
        usuario2.setApellidos("apellidos");
        cliente2.setUsuario(usuario2);
        cliente2.setPuntosAcumulados(0);
        cliente2.setTotalGastado(new BigDecimal("0.00"));
        factura2.setCliente(cliente2);
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Vehiculo vehiculo1 = new Vehiculo();
        final Cliente cliente3 = new Cliente();
        cliente3.setId(0L);
        final Usuario usuario3 = new Usuario();
        usuario3.setNombre("nombre");
        usuario3.setApellidos("apellidos");
        cliente3.setUsuario(usuario3);
        cliente3.setPuntosAcumulados(0);
        cliente3.setTotalGastado(new BigDecimal("0.00"));
        vehiculo1.setCliente(cliente3);
        reparacion1.setVehiculo(vehiculo1);
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        factura2.setReparacion(reparacion1);
        final Oficina oficina1 = new Oficina();
        factura2.setOficina(oficina1);
        factura2.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura2.setTotal(new BigDecimal("0.00"));
        factura2.setPagada(false);
        factura2.setNumeroFactura("n");
        factura2.setMetodoPago("m");
        factura2.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockFacturaRepo.save(any(Factura.class))).thenReturn(factura2);

        // Run the test
        final FacturaResponse result = facturaServiceUnderTest.marcarPagada(0L, req);

        // Verify the results
        verify(mockClienteRepo).save(any(Cliente.class));
        verify(mockPuntosRepo).save(any(PuntosHistorial.class));
        verify(mockNotificationService).notificarPagoConfirmado(any(Usuario.class), eq("n"), eq("total"));
    }

    @Test
    void testMarcarPagada_FacturaRepositoryFindByIdReturnsAbsent() {
        // Setup
        final PagoRequest req = new PagoRequest();
        req.setMetodoPago("m");

        when(mockFacturaRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> facturaServiceUnderTest.marcarPagada(0L, req))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testMarcarPagada_FacturaRepositorySaveThrowsOptimisticLockingFailureException() {
        // Setup
        final PagoRequest req = new PagoRequest();
        req.setMetodoPago("m");

        // Configure FacturaRepository.findById(...).
        final Factura factura1 = new Factura();
        factura1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        cliente.setPuntosAcumulados(0);
        cliente.setTotalGastado(new BigDecimal("0.00"));
        factura1.setCliente(cliente);
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        cliente1.setPuntosAcumulados(0);
        cliente1.setTotalGastado(new BigDecimal("0.00"));
        vehiculo.setCliente(cliente1);
        reparacion.setVehiculo(vehiculo);
        reparacion.setCosteTotal(new BigDecimal("0.00"));
        factura1.setReparacion(reparacion);
        final Oficina oficina = new Oficina();
        factura1.setOficina(oficina);
        factura1.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura1.setTotal(new BigDecimal("0.00"));
        factura1.setPagada(false);
        factura1.setNumeroFactura("n");
        factura1.setMetodoPago("m");
        factura1.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<Factura> factura = Optional.of(factura1);
        when(mockFacturaRepo.findById(0L)).thenReturn(factura);

        when(mockFacturaRepo.save(any(Factura.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> facturaServiceUnderTest.marcarPagada(0L, req))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testMarcarPagada_ClienteRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final PagoRequest req = new PagoRequest();
        req.setMetodoPago("m");

        // Configure FacturaRepository.findById(...).
        final Factura factura1 = new Factura();
        factura1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        cliente.setPuntosAcumulados(0);
        cliente.setTotalGastado(new BigDecimal("0.00"));
        factura1.setCliente(cliente);
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        cliente1.setPuntosAcumulados(0);
        cliente1.setTotalGastado(new BigDecimal("0.00"));
        vehiculo.setCliente(cliente1);
        reparacion.setVehiculo(vehiculo);
        reparacion.setCosteTotal(new BigDecimal("0.00"));
        factura1.setReparacion(reparacion);
        final Oficina oficina = new Oficina();
        factura1.setOficina(oficina);
        factura1.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura1.setTotal(new BigDecimal("0.00"));
        factura1.setPagada(false);
        factura1.setNumeroFactura("n");
        factura1.setMetodoPago("m");
        factura1.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<Factura> factura = Optional.of(factura1);
        when(mockFacturaRepo.findById(0L)).thenReturn(factura);

        // Configure FacturaRepository.save(...).
        final Factura factura2 = new Factura();
        factura2.setId(0L);
        final Cliente cliente2 = new Cliente();
        cliente2.setId(0L);
        final Usuario usuario2 = new Usuario();
        usuario2.setNombre("nombre");
        usuario2.setApellidos("apellidos");
        cliente2.setUsuario(usuario2);
        cliente2.setPuntosAcumulados(0);
        cliente2.setTotalGastado(new BigDecimal("0.00"));
        factura2.setCliente(cliente2);
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Vehiculo vehiculo1 = new Vehiculo();
        final Cliente cliente3 = new Cliente();
        cliente3.setId(0L);
        final Usuario usuario3 = new Usuario();
        usuario3.setNombre("nombre");
        usuario3.setApellidos("apellidos");
        cliente3.setUsuario(usuario3);
        cliente3.setPuntosAcumulados(0);
        cliente3.setTotalGastado(new BigDecimal("0.00"));
        vehiculo1.setCliente(cliente3);
        reparacion1.setVehiculo(vehiculo1);
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        factura2.setReparacion(reparacion1);
        final Oficina oficina1 = new Oficina();
        factura2.setOficina(oficina1);
        factura2.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura2.setTotal(new BigDecimal("0.00"));
        factura2.setPagada(false);
        factura2.setNumeroFactura("n");
        factura2.setMetodoPago("m");
        factura2.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockFacturaRepo.save(any(Factura.class))).thenReturn(factura2);

        when(mockClienteRepo.save(any(Cliente.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> facturaServiceUnderTest.marcarPagada(0L, req))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testMarcarPagada_PuntosHistorialRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final PagoRequest req = new PagoRequest();
        req.setMetodoPago("m");

        // Configure FacturaRepository.findById(...).
        final Factura factura1 = new Factura();
        factura1.setId(0L);
        final Cliente cliente = new Cliente();
        cliente.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        cliente.setUsuario(usuario);
        cliente.setPuntosAcumulados(0);
        cliente.setTotalGastado(new BigDecimal("0.00"));
        factura1.setCliente(cliente);
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Vehiculo vehiculo = new Vehiculo();
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario1 = new Usuario();
        usuario1.setNombre("nombre");
        usuario1.setApellidos("apellidos");
        cliente1.setUsuario(usuario1);
        cliente1.setPuntosAcumulados(0);
        cliente1.setTotalGastado(new BigDecimal("0.00"));
        vehiculo.setCliente(cliente1);
        reparacion.setVehiculo(vehiculo);
        reparacion.setCosteTotal(new BigDecimal("0.00"));
        factura1.setReparacion(reparacion);
        final Oficina oficina = new Oficina();
        factura1.setOficina(oficina);
        factura1.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura1.setTotal(new BigDecimal("0.00"));
        factura1.setPagada(false);
        factura1.setNumeroFactura("n");
        factura1.setMetodoPago("m");
        factura1.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<Factura> factura = Optional.of(factura1);
        when(mockFacturaRepo.findById(0L)).thenReturn(factura);

        // Configure FacturaRepository.save(...).
        final Factura factura2 = new Factura();
        factura2.setId(0L);
        final Cliente cliente2 = new Cliente();
        cliente2.setId(0L);
        final Usuario usuario2 = new Usuario();
        usuario2.setNombre("nombre");
        usuario2.setApellidos("apellidos");
        cliente2.setUsuario(usuario2);
        cliente2.setPuntosAcumulados(0);
        cliente2.setTotalGastado(new BigDecimal("0.00"));
        factura2.setCliente(cliente2);
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Vehiculo vehiculo1 = new Vehiculo();
        final Cliente cliente3 = new Cliente();
        cliente3.setId(0L);
        final Usuario usuario3 = new Usuario();
        usuario3.setNombre("nombre");
        usuario3.setApellidos("apellidos");
        cliente3.setUsuario(usuario3);
        cliente3.setPuntosAcumulados(0);
        cliente3.setTotalGastado(new BigDecimal("0.00"));
        vehiculo1.setCliente(cliente3);
        reparacion1.setVehiculo(vehiculo1);
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        factura2.setReparacion(reparacion1);
        final Oficina oficina1 = new Oficina();
        factura2.setOficina(oficina1);
        factura2.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        factura2.setTotal(new BigDecimal("0.00"));
        factura2.setPagada(false);
        factura2.setNumeroFactura("n");
        factura2.setMetodoPago("m");
        factura2.setFechaPago(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockFacturaRepo.save(any(Factura.class))).thenReturn(factura2);

        when(mockPuntosRepo.save(any(PuntosHistorial.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> facturaServiceUnderTest.marcarPagada(0L, req))
                .isInstanceOf(OptimisticLockingFailureException.class);
        verify(mockClienteRepo).save(any(Cliente.class));
    }
}
