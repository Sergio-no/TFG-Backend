package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.request.CitaRequest;
import org.example.tfgbackend.dto.response.CitaResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.model.*;
import org.example.tfgbackend.repository.*;
import org.example.tfgbackend.util.CitaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CitaService {
    @Autowired private CitaRepository     citaRepo;
    @Autowired private ClienteRepository  clienteRepo;
    @Autowired private VehiculoRepository vehiculoRepo;
    @Autowired private NotificationService notificationService;

    private static final DateTimeFormatter FMT_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Todas las franjas horarias posibles: de 9:00 a 17:30 cada 30 min
    private static final List<LocalTime> TODAS_LAS_HORAS;
    static {
        TODAS_LAS_HORAS = new ArrayList<>();
        LocalTime t = LocalTime.of(9, 0);
        LocalTime fin = LocalTime.of(18, 0);
        while (t.isBefore(fin)) {
            TODAS_LAS_HORAS.add(t);
            t = t.plusMinutes(30);
        }
    }

    public List<CitaResponse> getAll() {
        return citaRepo.findAll().stream().map(CitaMapper::toResponse).toList();
    }

    public List<CitaResponse> getCitasHoy() {
        LocalDateTime inicio = LocalDate.now().atStartOfDay();
        LocalDateTime fin    = inicio.plusDays(1).minusSeconds(1);
        return citaRepo.findCitasHoy(inicio, fin).stream()
                .map(CitaMapper::toResponse).toList();
    }

    public List<CitaResponse> getByCliente(Long clienteId) {
        return citaRepo.findByClienteId(clienteId).stream()
                .map(CitaMapper::toResponse).toList();
    }

    public CitaResponse getById(Long id) {
        return CitaMapper.toResponse(citaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada")));
    }

    public List<String> getHorasDisponibles(LocalDate fecha) {
        LocalDateTime inicioDia = fecha.atStartOfDay();
        LocalDateTime finDia    = fecha.atTime(23, 59, 59);

        Set<LocalTime> ocupadas = citaRepo
                .findCitasNoCanceladasEntre(inicioDia, finDia)
                .stream()
                .map(c -> c.getFecha().toLocalTime())
                .collect(Collectors.toSet());

        return TODAS_LAS_HORAS.stream()
                .filter(h -> !ocupadas.contains(h))
                .map(h -> String.format("%02d:%02d", h.getHour(), h.getMinute()))
                .toList();
    }

    @Transactional
    public CitaResponse crear(CitaRequest req) {
        Cliente cliente = clienteRepo.findById(req.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        Vehiculo vehiculo;
        if (req.getMatricula() != null && !req.getMatricula().isBlank()) {
            vehiculo = vehiculoRepo.findByMatricula(req.getMatricula().toUpperCase())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Vehículo no encontrado con matrícula: " + req.getMatricula()));
        } else {
            vehiculo = vehiculoRepo.findById(req.getVehiculoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado"));
        }

        if (citaRepo.existsCitaEnHora(req.getFecha())) {
            throw new IllegalArgumentException(
                    "Ya existe una cita a esa hora. Por favor, elige otro horario.");
        }

        Cita cita = new Cita();
        cita.setCliente(cliente);
        cita.setVehiculo(vehiculo);
        cita.setFecha(req.getFecha());
        cita.setDescripcion(req.getDescripcion());
        cita.setEstado("PENDIENTE");
        return CitaMapper.toResponse(citaRepo.save(cita));
    }

    @Transactional
    public CitaResponse cambiarEstado(Long id, String nuevoEstado) {
        Cita cita = citaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));
        cita.setEstado(nuevoEstado);
        cita = citaRepo.save(cita);

        // Enviar notificación push al cliente
        try {
            Usuario clienteUsuario = cita.getCliente().getUsuario();
            String fechaStr = cita.getFecha().format(FMT_FECHA);
            notificationService.notificarCitaCambioEstado(clienteUsuario, nuevoEstado, fechaStr);
        } catch (Exception e) {
            System.err.println("Error enviando notificación de cita: " + e.getMessage());
        }

        return CitaMapper.toResponse(cita);
    }
}