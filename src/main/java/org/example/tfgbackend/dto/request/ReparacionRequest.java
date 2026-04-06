package org.example.tfgbackend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReparacionRequest {
    @NotNull private Long vehiculoId;
    @NotNull private Long mecanicoId;
    private Long citaId;

    public @NotNull Long getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(@NotNull Long vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public @NotNull Long getMecanicoId() {
        return mecanicoId;
    }

    public void setMecanicoId(@NotNull Long mecanicoId) {
        this.mecanicoId = mecanicoId;
    }

    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
    }
}
