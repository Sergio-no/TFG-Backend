package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.request.CitaRequest;
import org.example.tfgbackend.dto.response.CitaResponse;
import org.example.tfgbackend.model.Cita;
import org.example.tfgbackend.model.Cliente;
import org.example.tfgbackend.model.Usuario;
import org.example.tfgbackend.model.Vehiculo;
import org.example.tfgbackend.repository.CitaRepository;
import org.example.tfgbackend.repository.ClienteRepository;
import org.example.tfgbackend.repository.VehiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CitaServiceTest {

    @Mock private CitaRepository citaRepo;
    @Mock private ClienteRepository clienteRepo;
    @Mock private VehiculoRepository vehiculoRepo;
    @Mock private NotificationService notificationService;

    @InjectMocks
    private CitaService citaService;

    private CitaRequest requestMock;
    private Cliente clienteMock;
    private Vehiculo vehiculoMock;
    private Cita citaMock;

    @BeforeEach
    void setUp() {
        Usuario u = new Usuario();
        u.setNombre("Ana");
        u.setApellidos("López");

        clienteMock = new Cliente();
        clienteMock.setId(1L);
        clienteMock.setUsuario(u);

        vehiculoMock = new Vehiculo();
        vehiculoMock.setId(1L);
        vehiculoMock.setMarca("Seat");
        vehiculoMock.setModelo("Ibiza");
        vehiculoMock.setMatricula("9999XYZ");
        vehiculoMock.setCliente(clienteMock);

        requestMock = new CitaRequest();
        requestMock.setClienteId(1L);
        requestMock.setVehiculoId(1L);
        requestMock.setFecha(LocalDateTime.of(2026, 5, 10, 10, 30));
        requestMock.setDescripcion("Cambio de aceite");

        citaMock = new Cita();
        citaMock.setId(1L);
        citaMock.setCliente(clienteMock);
        citaMock.setVehiculo(vehiculoMock);
        citaMock.setFecha(requestMock.getFecha());
    }

    @Test
    void crearCita_HoraDisponible_DeberiaCrearCita() {
        when(clienteRepo.findById(1L)).thenReturn(Optional.of(clienteMock));
        when(vehiculoRepo.findById(1L)).thenReturn(Optional.of(vehiculoMock));
        when(citaRepo.existsCitaEnHora(any(LocalDateTime.class))).thenReturn(false);
        when(citaRepo.save(any(Cita.class))).thenReturn(citaMock);

        CitaResponse response = citaService.crear(requestMock);

        assertNotNull(response);
        assertEquals("PENDIENTE", citaMock.getEstado());
        verify(citaRepo, times(1)).save(any(Cita.class));
    }

    @Test
    void crearCita_HoraOcupada_DeberiaLanzarExcepcion() {
        when(clienteRepo.findById(1L)).thenReturn(Optional.of(clienteMock));
        when(vehiculoRepo.findById(1L)).thenReturn(Optional.of(vehiculoMock));
        when(citaRepo.existsCitaEnHora(any(LocalDateTime.class))).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            citaService.crear(requestMock);
        });

        assertTrue(exception.getMessage().contains("Ya existe una cita a esa hora"));
        verify(citaRepo, never()).save(any(Cita.class));
    }
}