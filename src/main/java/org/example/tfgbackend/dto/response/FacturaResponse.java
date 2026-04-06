package org.example.tfgbackend.dto.response;

public class FacturaResponse {
    private Long id;
    private String numeroFactura;
    private Long clienteId;
    private String clienteNombre;
    private Long reparacionId;
    private String fecha;
    private String total;
    private boolean pagada;
    private String metodoPago;
    private String fechaPago;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String n) { this.numeroFactura = n; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long c) { this.clienteId = c; }
    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String c) { this.clienteNombre = c; }
    public Long getReparacionId() { return reparacionId; }
    public void setReparacionId(Long r) { this.reparacionId = r; }
    public String getFecha() { return fecha; }
    public void setFecha(String f) { this.fecha = f; }
    public String getTotal() { return total; }
    public void setTotal(String t) { this.total = t; }
    public boolean isPagada() { return pagada; }
    public void setPagada(boolean p) { this.pagada = p; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String m) { this.metodoPago = m; }
    public String getFechaPago() { return fechaPago; }
    public void setFechaPago(String f) { this.fechaPago = f; }
}