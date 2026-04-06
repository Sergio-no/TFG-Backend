package org.example.tfgbackend.dto.request;

import lombok.Data;

@Data
public class EstadoRequest {
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
