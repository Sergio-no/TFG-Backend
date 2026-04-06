package org.example.tfgbackend.dto.response;

public class ReparacionResponse {
    private Long id;
    private String vehiculo;
    private String matricula;
    private String clienteNombre;
    private String mecanico;
    private Long mecanicoId;
    private String fechaInicio;
    private String fechaFin;
    private String estado;
    private String costeTotal;
    private Long citaId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getVehiculo() { return vehiculo; }
    public void setVehiculo(String v) { this.vehiculo = v; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String m) { this.matricula = m; }
    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String c) { this.clienteNombre = c; }
    public String getMecanico() { return mecanico; }
    public void setMecanico(String m) { this.mecanico = m; }
    public Long getMecanicoId() { return mecanicoId; }
    public void setMecanicoId(Long m) { this.mecanicoId = m; }
    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String f) { this.fechaInicio = f; }
    public String getFechaFin() { return fechaFin; }
    public void setFechaFin(String f) { this.fechaFin = f; }
    public String getEstado() { return estado; }
    public void setEstado(String e) { this.estado = e; }
    public String getCosteTotal() { return costeTotal; }
    public void setCosteTotal(String c) { this.costeTotal = c; }
    public Long getCitaId() { return citaId; }
    public void setCitaId(Long c) { this.citaId = c; }
}