package org.example.tfgbackend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CitaRequest {
    @NotNull private Long clienteId;
    private Long vehiculoId;         // Opcional si se usa matrícula
    private String matricula;        // NUEVO: alternativa al vehiculoId
    @NotNull private LocalDateTime fecha;
    private String descripcion;

    public @NotNull Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(@NotNull Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(Long vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public @NotNull LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(@NotNull LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
