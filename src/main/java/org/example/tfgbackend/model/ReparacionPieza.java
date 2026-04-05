package org.example.tfgbackend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "reparacion_pieza")
public class ReparacionPieza {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "reparacion_id", nullable = false)
    private Reparacion reparacion;

    @ManyToOne @JoinColumn(name = "pieza_id", nullable = false)
    private Pieza pieza;

    @Column(name = "cantidad_usada", nullable = false)
    private int cantidadUsada = 1;

    @Column(name = "precio_momento", nullable = false)
    private BigDecimal precioMomento;

    public ReparacionPieza() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Reparacion getReparacion() { return reparacion; }
    public void setReparacion(Reparacion r) { this.reparacion = r; }
    public Pieza getPieza() { return pieza; }
    public void setPieza(Pieza pieza) { this.pieza = pieza; }
    public int getCantidadUsada() { return cantidadUsada; }
    public void setCantidadUsada(int c) { this.cantidadUsada = c; }
    public BigDecimal getPrecioMomento() { return precioMomento; }
    public void setPrecioMomento(BigDecimal p) { this.precioMomento = p; }
}