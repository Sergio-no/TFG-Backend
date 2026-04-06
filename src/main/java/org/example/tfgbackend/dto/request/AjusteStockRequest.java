package org.example.tfgbackend.dto.request;

import lombok.Data;

@Data
public class AjusteStockRequest {
    private int cantidad;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
