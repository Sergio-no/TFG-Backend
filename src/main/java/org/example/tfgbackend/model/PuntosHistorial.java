package org.example.tfgbackend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "puntos_historial")
public class PuntosHistorial {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne @JoinColumn(name = "factura_id")
    private Factura factura;

    @Column(nullable = false)
    private int puntos;

    @Column(nullable = false)
    private String concepto;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    public PuntosHistorial() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }
    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }
    public String getConcepto() { return concepto; }
    public void setConcepto(String concepto) { this.concepto = concepto; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}