package org.example.tfgbackend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReparacionRequest {
    @NotNull private Long vehiculoId;
    @NotNull private Long mecanicoId;
    private Long citaId;
    private BigDecimal costeInicial;  // NUEVO: coste inicial opcional

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

    public BigDecimal getCosteInicial() {
        return costeInicial;
    }

    public void setCosteInicial(BigDecimal costeInicial) {
        this.costeInicial = costeInicial;
    }
}
