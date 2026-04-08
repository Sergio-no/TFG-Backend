package org.example.tfgbackend.util;

import org.example.tfgbackend.dto.response.CitaResponse;
import org.example.tfgbackend.model.Cita;

import java.time.format.DateTimeFormatter;

public class CitaMapper {

    private static final DateTimeFormatter DATE =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME =
            DateTimeFormatter.ofPattern("HH:mm");

    public static CitaResponse toResponse(Cita c) {
        CitaResponse r = new CitaResponse();
        r.setId(c.getId());
        r.setClienteId(c.getCliente().getId());
        r.setClienteNombre(c.getCliente().getUsuario().getNombre()
                + " " + c.getCliente().getUsuario().getApellidos());
        r.setVehiculoId(c.getVehiculo().getId());
        r.setVehiculo(c.getVehiculo().getMarca() + " "
                + c.getVehiculo().getModelo() + " · "
                + c.getVehiculo().getMatricula());
        r.setFecha(c.getFecha().format(DATE));
        r.setHora(c.getFecha().format(TIME));
        r.setTipo(c.getDescripcion());
        r.setEstado(c.getEstado());
        r.setDescripcion(c.getDescripcion());
        return r;
    }
}