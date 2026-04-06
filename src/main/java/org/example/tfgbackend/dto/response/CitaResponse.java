package org.example.tfgbackend.dto.response;

public class CitaResponse {
    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private Long vehiculoId;
    private String vehiculo;
    private String fecha;
    private String hora;
    private String tipo;
    private String estado;
    private String descripcion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long c) { this.clienteId = c; }
    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String c) { this.clienteNombre = c; }
    public Long getVehiculoId() { return vehiculoId; }
    public void setVehiculoId(Long v) { this.vehiculoId = v; }
    public String getVehiculo() { return vehiculo; }
    public void setVehiculo(String v) { this.vehiculo = v; }
    public String getFecha() { return fecha; }
    public void setFecha(String f) { this.fecha = f; }
    public String getHora() { return hora; }
    public void setHora(String h) { this.hora = h; }
    public String getTipo() { return tipo; }
    public void setTipo(String t) { this.tipo = t; }
    public String getEstado() { return estado; }
    public void setEstado(String e) { this.estado = e; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String d) { this.descripcion = d; }
}