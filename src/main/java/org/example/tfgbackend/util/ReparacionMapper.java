package org.example.tfgbackend.util;

import org.example.tfgbackend.dto.response.ReparacionResponse;
import org.example.tfgbackend.model.Reparacion;

import java.time.format.DateTimeFormatter;

public class ReparacionMapper {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static ReparacionResponse toResponse(Reparacion r) {
        ReparacionResponse res = new ReparacionResponse();
        res.setId(r.getId());
        res.setVehiculo(r.getVehiculo().getMarca()
                + " " + r.getVehiculo().getModelo());
        res.setMatricula(r.getVehiculo().getMatricula());
        res.setClienteNombre(r.getVehiculo().getCliente().getUsuario().getNombre()
                + " " + r.getVehiculo().getCliente().getUsuario().getApellidos());
        res.setMecanico(r.getMecanico().getNombre()
                + " " + r.getMecanico().getApellidos());
        res.setMecanicoId(r.getMecanico().getId());
        res.setFechaInicio(r.getFechaInicio().format(FMT));
        res.setFechaFin(r.getFechaFin() != null
                ? r.getFechaFin().format(FMT) : "");
        res.setEstado(r.getEstado());
        res.setCosteTotal(r.getCosteTotal().toPlainString());
        res.setCitaId(r.getCita() != null ? r.getCita().getId() : null);
        return res;
    }
}