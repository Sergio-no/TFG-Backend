package org.example.tfgbackend.dto.response;

public class ClienteResponse {
    private Long id;
    private Long usuarioId;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private int puntosAcumulados;
    private String totalGastado;
    private String fechaRegistro;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long u) { this.usuarioId = u; }
    public String getNombre() { return nombre; }
    public void setNombre(String n) { this.nombre = n; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String a) { this.apellidos = a; }
    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String t) { this.telefono = t; }
    public int getPuntosAcumulados() { return puntosAcumulados; }
    public void setPuntosAcumulados(int p) { this.puntosAcumulados = p; }
    public String getTotalGastado() { return totalGastado; }
    public void setTotalGastado(String t) { this.totalGastado = t; }
    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String f) { this.fechaRegistro = f; }
}