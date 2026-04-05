package org.example.tfgbackend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pieza")
public class Pieza {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(name = "precio_unitario", nullable = false)
    private BigDecimal precioUnitario;

    @Column(name = "stock_actual", nullable = false)
    private int stockActual = 0;

    @Column(name = "stock_minimo", nullable = false)
    private int stockMinimo = 5;

    public Pieza() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String d) { this.descripcion = d; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal p) { this.precioUnitario = p; }
    public int getStockActual() { return stockActual; }
    public void setStockActual(int s) { this.stockActual = s; }
    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int s) { this.stockMinimo = s; }
}