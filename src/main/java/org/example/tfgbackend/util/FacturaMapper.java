package org.example.tfgbackend.util;

import org.example.tfgbackend.dto.response.FacturaResponse;
import org.example.tfgbackend.model.Factura;

import java.time.format.DateTimeFormatter;

public class FacturaMapper {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static FacturaResponse toResponse(Factura f) {
        FacturaResponse r = new FacturaResponse();
        r.setId(f.getId());
        r.setNumeroFactura(f.getNumeroFactura());
        r.setClienteId(f.getCliente().getId());
        r.setClienteNombre(f.getCliente().getUsuario().getNombre()
                + " " + f.getCliente().getUsuario().getApellidos());
        r.setReparacionId(f.getReparacion().getId());
        r.setFecha(f.getFecha().format(FMT));
        r.setTotal(f.getTotal().toPlainString());
        r.setPagada(f.isPagada());
        r.setMetodoPago(f.getMetodoPago());
        r.setFechaPago(f.getFechaPago() != null
                ? f.getFechaPago().format(FMT) : null);
        return r;
    }
}