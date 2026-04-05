package org.example.tfgbackend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "factura")
public class Factura {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToOne @JoinColumn(name = "reparacion_id", nullable = false)
    private Reparacion reparacion;

    @ManyToOne @JoinColumn(name = "oficina_id")
    private Oficina oficina;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = false)
    private boolean pagada = false;

    @Column(name = "numero_factura", nullable = false, unique = true)
    private String numeroFactura;

    @Column(name = "metodo_pago")
    private String metodoPago;

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;

    public Factura() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Reparacion getReparacion() { return reparacion; }
    public void setReparacion(Reparacion r) { this.reparacion = r; }
    public Oficina getOficina() { return oficina; }
    public void setOficina(Oficina oficina) { this.oficina = oficina; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public boolean isPagada() { return pagada; }
    public void setPagada(boolean pagada) { this.pagada = pagada; }
    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String n) { this.numeroFactura = n; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String m) { this.metodoPago = m; }
    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime f) { this.fechaPago = f; }
}