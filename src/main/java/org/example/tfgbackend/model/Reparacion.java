package org.example.tfgbackend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reparacion")
public class Reparacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne @JoinColumn(name = "mecanico_id", nullable = false)
    private Mecanico mecanico;

    @ManyToOne @JoinColumn(name = "cita_id")
    private Cita cita;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio = LocalDate.now();

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(nullable = false)
    private String estado = "EN_PROCESO";

    @Column(name = "coste_total", nullable = false)
    private BigDecimal costeTotal = BigDecimal.ZERO;

    public Reparacion() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }
    public Mecanico getMecanico() { return mecanico; }
    public void setMecanico(Mecanico mecanico) { this.mecanico = mecanico; }
    public Cita getCita() { return cita; }
    public void setCita(Cita cita) { this.cita = cita; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate f) { this.fechaInicio = f; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate f) { this.fechaFin = f; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public BigDecimal getCosteTotal() { return costeTotal; }
    public void setCosteTotal(BigDecimal c) { this.costeTotal = c; }
}