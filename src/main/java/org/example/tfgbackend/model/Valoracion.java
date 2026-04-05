package org.example.tfgbackend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "valoracion")
public class Valoracion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToOne @JoinColumn(name = "reparacion_id", nullable = false, unique = true)
    private Reparacion reparacion;

    @Column(nullable = false)
    private short puntuacion;

    private String comentario;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    public Valoracion() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Reparacion getReparacion() { return reparacion; }
    public void setReparacion(Reparacion r) { this.reparacion = r; }
    public short getPuntuacion() { return puntuacion; }
    public void setPuntuacion(short p) { this.puntuacion = p; }
    public String getComentario() { return comentario; }
    public void setComentario(String c) { this.comentario = c; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}