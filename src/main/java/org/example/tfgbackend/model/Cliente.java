package org.example.tfgbackend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "puntos_acumulados", nullable = false)
    private int puntosAcumulados = 0;

    @Column(name = "total_gastado", nullable = false)
    private BigDecimal totalGastado = BigDecimal.ZERO;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    // ── Stripe ──
    @Column(name = "stripe_customer_id")
    private String stripeCustomerId;

    public Cliente() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public int getPuntosAcumulados() { return puntosAcumulados; }
    public void setPuntosAcumulados(int p) { this.puntosAcumulados = p; }
    public BigDecimal getTotalGastado() { return totalGastado; }
    public void setTotalGastado(BigDecimal t) { this.totalGastado = t; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime f) { this.fechaRegistro = f; }
    public String getStripeCustomerId() { return stripeCustomerId; }
    public void setStripeCustomerId(String s) { this.stripeCustomerId = s; }
}
