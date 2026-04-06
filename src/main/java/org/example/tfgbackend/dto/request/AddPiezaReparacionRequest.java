package org.example.tfgbackend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddPiezaReparacionRequest {
    @NotNull private Long piezaId;
    @NotNull private int cantidadUsada;

    @NotNull
    public int getCantidadUsada() {
        return cantidadUsada;
    }

    public void setCantidadUsada(@NotNull int cantidadUsada) {
        this.cantidadUsada = cantidadUsada;
    }

    public @NotNull Long getPiezaId() {
        return piezaId;
    }

    public void setPiezaId(@NotNull Long piezaId) {
        this.piezaId = piezaId;
    }
}
