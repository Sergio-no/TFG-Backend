package org.example.tfgbackend.dto.request;

import lombok.Data;

@Data
public class PagoRequest {
    private String metodoPago; // EFECTIVO, TARJETA, TRANSFERENCIA, ONLINE

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}
