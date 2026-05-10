package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.response.EstadisticasResponse;
import org.example.tfgbackend.model.*;
import org.example.tfgbackend.repository.FacturaRepository;
import org.example.tfgbackend.repository.MecanicoRepository;
import org.example.tfgbackend.repository.ReparacionRepository;
import org.example.tfgbackend.repository.ValoracionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstadisticasServiceTest {

    @Mock
    private FacturaRepository mockFacturaRepo;
    @Mock
    private ReparacionRepository mockReparacionRepo;
    @Mock
    private ValoracionRepository mockValoracionRepo;
    @Mock
    private MecanicoRepository mockMecanicoRepo;

    @InjectMocks
    private EstadisticasService estadisticasServiceUnderTest;

    @Test
    void testGetEstadisticas() {
        // Setup
        when(mockFacturaRepo.sumTotalPagadoEntreFechas(LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0))).thenReturn(new BigDecimal("0.00"));

        // Configure ReparacionRepository.findAll(...).
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("nombre");
        mecanico.setApellidos("apellidos");
        reparacion.setMecanico(mecanico);
        reparacion.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion.setCosteTotal(new BigDecimal("0.00"));
        final List<Reparacion> reparacions = List.of(reparacion);
        when(mockReparacionRepo.findAll()).thenReturn(reparacions);

        when(mockValoracionRepo.findMediaPuntuacion()).thenReturn(0.0);

        // Configure MecanicoRepository.findAll(...).
        final Mecanico mecanico1 = new Mecanico();
        mecanico1.setId(0L);
        mecanico1.setNombre("nombre");
        mecanico1.setApellidos("apellidos");
        mecanico1.setEspecialidad("e");
        mecanico1.setTelefono("telefono");
        final List<Mecanico> mecanicos = List.of(mecanico1);
        when(mockMecanicoRepo.findAll()).thenReturn(mecanicos);

        // Configure ValoracionRepository.findTop10ByOrderByFechaDesc(...).
        final Valoracion valoracion = new Valoracion();
        valoracion.setId(0L);
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("c");
        cliente.setUsuario(usuario);
        valoracion.setCliente(cliente);
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Mecanico mecanico2 = new Mecanico();
        mecanico2.setId(0L);
        mecanico2.setNombre("nombre");
        mecanico2.setApellidos("apellidos");
        reparacion1.setMecanico(mecanico2);
        reparacion1.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        valoracion.setReparacion(reparacion1);
        valoracion.setPuntuacion((short) 0);
        valoracion.setComentario("c");
        valoracion.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<Valoracion> valoracions = List.of(valoracion);
        when(mockValoracionRepo.findTop10ByOrderByFechaDesc()).thenReturn(valoracions);

        // Run the test
        final EstadisticasResponse result = estadisticasServiceUnderTest.getEstadisticas(0, 0);

        // Verify the results
    }

    @Test
    void testGetEstadisticas_FacturaRepositoryReturnsNull() {
        // Setup
        when(mockFacturaRepo.sumTotalPagadoEntreFechas(LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0))).thenReturn(null);

        // Configure ReparacionRepository.findAll(...).
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("nombre");
        mecanico.setApellidos("apellidos");
        reparacion.setMecanico(mecanico);
        reparacion.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion.setCosteTotal(new BigDecimal("0.00"));
        final List<Reparacion> reparacions = List.of(reparacion);
        when(mockReparacionRepo.findAll()).thenReturn(reparacions);

        when(mockValoracionRepo.findMediaPuntuacion()).thenReturn(0.0);

        // Configure MecanicoRepository.findAll(...).
        final Mecanico mecanico1 = new Mecanico();
        mecanico1.setId(0L);
        mecanico1.setNombre("nombre");
        mecanico1.setApellidos("apellidos");
        mecanico1.setEspecialidad("e");
        mecanico1.setTelefono("telefono");
        final List<Mecanico> mecanicos = List.of(mecanico1);
        when(mockMecanicoRepo.findAll()).thenReturn(mecanicos);

        // Configure ValoracionRepository.findTop10ByOrderByFechaDesc(...).
        final Valoracion valoracion = new Valoracion();
        valoracion.setId(0L);
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("c");
        cliente.setUsuario(usuario);
        valoracion.setCliente(cliente);
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Mecanico mecanico2 = new Mecanico();
        mecanico2.setId(0L);
        mecanico2.setNombre("nombre");
        mecanico2.setApellidos("apellidos");
        reparacion1.setMecanico(mecanico2);
        reparacion1.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        valoracion.setReparacion(reparacion1);
        valoracion.setPuntuacion((short) 0);
        valoracion.setComentario("c");
        valoracion.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<Valoracion> valoracions = List.of(valoracion);
        when(mockValoracionRepo.findTop10ByOrderByFechaDesc()).thenReturn(valoracions);

        // Run the test
        final EstadisticasResponse result = estadisticasServiceUnderTest.getEstadisticas(0, 0);

        // Verify the results
    }

    @Test
    void testGetEstadisticas_ReparacionRepositoryReturnsNoItems() {
        // Setup
        when(mockFacturaRepo.sumTotalPagadoEntreFechas(LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0))).thenReturn(new BigDecimal("0.00"));
        when(mockReparacionRepo.findAll()).thenReturn(Collections.emptyList());
        when(mockValoracionRepo.findMediaPuntuacion()).thenReturn(0.0);

        // Configure MecanicoRepository.findAll(...).
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("nombre");
        mecanico.setApellidos("apellidos");
        mecanico.setEspecialidad("e");
        mecanico.setTelefono("telefono");
        final List<Mecanico> mecanicos = List.of(mecanico);
        when(mockMecanicoRepo.findAll()).thenReturn(mecanicos);

        // Configure ValoracionRepository.findTop10ByOrderByFechaDesc(...).
        final Valoracion valoracion = new Valoracion();
        valoracion.setId(0L);
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("c");
        cliente.setUsuario(usuario);
        valoracion.setCliente(cliente);
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Mecanico mecanico1 = new Mecanico();
        mecanico1.setId(0L);
        mecanico1.setNombre("nombre");
        mecanico1.setApellidos("apellidos");
        reparacion.setMecanico(mecanico1);
        reparacion.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion.setCosteTotal(new BigDecimal("0.00"));
        valoracion.setReparacion(reparacion);
        valoracion.setPuntuacion((short) 0);
        valoracion.setComentario("c");
        valoracion.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<Valoracion> valoracions = List.of(valoracion);
        when(mockValoracionRepo.findTop10ByOrderByFechaDesc()).thenReturn(valoracions);

        // Run the test
        final EstadisticasResponse result = estadisticasServiceUnderTest.getEstadisticas(0, 0);

        // Verify the results
    }

    @Test
    void testGetEstadisticas_ValoracionRepositoryFindMediaPuntuacionReturnsNull() {
        // Setup
        when(mockFacturaRepo.sumTotalPagadoEntreFechas(LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0))).thenReturn(new BigDecimal("0.00"));

        // Configure ReparacionRepository.findAll(...).
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("nombre");
        mecanico.setApellidos("apellidos");
        reparacion.setMecanico(mecanico);
        reparacion.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion.setCosteTotal(new BigDecimal("0.00"));
        final List<Reparacion> reparacions = List.of(reparacion);
        when(mockReparacionRepo.findAll()).thenReturn(reparacions);

        when(mockValoracionRepo.findMediaPuntuacion()).thenReturn(null);

        // Configure MecanicoRepository.findAll(...).
        final Mecanico mecanico1 = new Mecanico();
        mecanico1.setId(0L);
        mecanico1.setNombre("nombre");
        mecanico1.setApellidos("apellidos");
        mecanico1.setEspecialidad("e");
        mecanico1.setTelefono("telefono");
        final List<Mecanico> mecanicos = List.of(mecanico1);
        when(mockMecanicoRepo.findAll()).thenReturn(mecanicos);

        // Configure ValoracionRepository.findTop10ByOrderByFechaDesc(...).
        final Valoracion valoracion = new Valoracion();
        valoracion.setId(0L);
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("c");
        cliente.setUsuario(usuario);
        valoracion.setCliente(cliente);
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Mecanico mecanico2 = new Mecanico();
        mecanico2.setId(0L);
        mecanico2.setNombre("nombre");
        mecanico2.setApellidos("apellidos");
        reparacion1.setMecanico(mecanico2);
        reparacion1.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        valoracion.setReparacion(reparacion1);
        valoracion.setPuntuacion((short) 0);
        valoracion.setComentario("c");
        valoracion.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<Valoracion> valoracions = List.of(valoracion);
        when(mockValoracionRepo.findTop10ByOrderByFechaDesc()).thenReturn(valoracions);

        // Run the test
        final EstadisticasResponse result = estadisticasServiceUnderTest.getEstadisticas(0, 0);

        // Verify the results
    }

    @Test
    void testGetEstadisticas_MecanicoRepositoryReturnsNoItems() {
        // Setup
        when(mockFacturaRepo.sumTotalPagadoEntreFechas(LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0))).thenReturn(new BigDecimal("0.00"));

        // Configure ReparacionRepository.findAll(...).
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("nombre");
        mecanico.setApellidos("apellidos");
        reparacion.setMecanico(mecanico);
        reparacion.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion.setCosteTotal(new BigDecimal("0.00"));
        final List<Reparacion> reparacions = List.of(reparacion);
        when(mockReparacionRepo.findAll()).thenReturn(reparacions);

        when(mockValoracionRepo.findMediaPuntuacion()).thenReturn(0.0);
        when(mockMecanicoRepo.findAll()).thenReturn(Collections.emptyList());

        // Configure ValoracionRepository.findTop10ByOrderByFechaDesc(...).
        final Valoracion valoracion = new Valoracion();
        valoracion.setId(0L);
        final Cliente cliente = new Cliente();
        final Usuario usuario = new Usuario();
        usuario.setNombre("c");
        cliente.setUsuario(usuario);
        valoracion.setCliente(cliente);
        final Reparacion reparacion1 = new Reparacion();
        reparacion1.setId(0L);
        final Mecanico mecanico1 = new Mecanico();
        mecanico1.setId(0L);
        mecanico1.setNombre("nombre");
        mecanico1.setApellidos("apellidos");
        reparacion1.setMecanico(mecanico1);
        reparacion1.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion1.setCosteTotal(new BigDecimal("0.00"));
        valoracion.setReparacion(reparacion1);
        valoracion.setPuntuacion((short) 0);
        valoracion.setComentario("c");
        valoracion.setFecha(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<Valoracion> valoracions = List.of(valoracion);
        when(mockValoracionRepo.findTop10ByOrderByFechaDesc()).thenReturn(valoracions);

        // Run the test
        final EstadisticasResponse result = estadisticasServiceUnderTest.getEstadisticas(0, 0);

        // Verify the results
    }

    @Test
    void testGetEstadisticas_ValoracionRepositoryFindTop10ByOrderByFechaDescReturnsNoItems() {
        // Setup
        when(mockFacturaRepo.sumTotalPagadoEntreFechas(LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0))).thenReturn(new BigDecimal("0.00"));

        // Configure ReparacionRepository.findAll(...).
        final Reparacion reparacion = new Reparacion();
        reparacion.setId(0L);
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("nombre");
        mecanico.setApellidos("apellidos");
        reparacion.setMecanico(mecanico);
        reparacion.setFechaInicio(LocalDate.of(2020, 1, 1));
        reparacion.setCosteTotal(new BigDecimal("0.00"));
        final List<Reparacion> reparacions = List.of(reparacion);
        when(mockReparacionRepo.findAll()).thenReturn(reparacions);

        when(mockValoracionRepo.findMediaPuntuacion()).thenReturn(0.0);

        // Configure MecanicoRepository.findAll(...).
        final Mecanico mecanico1 = new Mecanico();
        mecanico1.setId(0L);
        mecanico1.setNombre("nombre");
        mecanico1.setApellidos("apellidos");
        mecanico1.setEspecialidad("e");
        mecanico1.setTelefono("telefono");
        final List<Mecanico> mecanicos = List.of(mecanico1);
        when(mockMecanicoRepo.findAll()).thenReturn(mecanicos);

        when(mockValoracionRepo.findTop10ByOrderByFechaDesc()).thenReturn(Collections.emptyList());

        // Run the test
        final EstadisticasResponse result = estadisticasServiceUnderTest.getEstadisticas(0, 0);

        // Verify the results
    }
}
