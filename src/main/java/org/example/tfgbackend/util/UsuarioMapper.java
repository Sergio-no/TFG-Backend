package org.example.tfgbackend.util;

import org.example.tfgbackend.dto.response.ClienteResponse;
import org.example.tfgbackend.dto.response.MecanicoResponse;
import org.example.tfgbackend.dto.response.UsuarioResponse;
import org.example.tfgbackend.model.Cliente;
import org.example.tfgbackend.model.Mecanico;
import org.example.tfgbackend.model.Usuario;

import java.time.format.DateTimeFormatter;

public class UsuarioMapper {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static UsuarioResponse toResponse(Usuario u) {
        UsuarioResponse r = new UsuarioResponse();
        r.setId(u.getId());
        r.setNombre(u.getNombre());
        r.setApellidos(u.getApellidos());
        r.setEmail(u.getEmail());
        r.setTelefono(u.getTelefono());
        r.setRol(u.getRol());
        r.setActivo(u.isActivo());
        return r;
    }

    public static ClienteResponse toClienteResponse(Cliente c) {
        ClienteResponse r = new ClienteResponse();
        r.setId(c.getId());
        r.setUsuarioId(c.getUsuario().getId());
        r.setNombre(c.getUsuario().getNombre());
        r.setApellidos(c.getUsuario().getApellidos());
        r.setEmail(c.getUsuario().getEmail());
        r.setTelefono(c.getUsuario().getTelefono());
        r.setPuntosAcumulados(c.getPuntosAcumulados());
        r.setTotalGastado(c.getTotalGastado().toPlainString());
        r.setFechaRegistro(c.getFechaRegistro().format(FMT));
        return r;
    }

    public static MecanicoResponse toMecanicoResponse(Mecanico m) {
        MecanicoResponse r = new MecanicoResponse();
        r.setId(m.getId());
        r.setNombre(m.getNombre());
        r.setApellidos(m.getApellidos());
        r.setEspecialidad(m.getEspecialidad());
        r.setTelefono(m.getTelefono());
        r.setActivoTaller(m.isActivoTaller());
        return r;
    }
}