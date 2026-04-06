package org.example.tfgbackend.dto.response;

public class ValoracionResponse {
    private Long id;
    private String clienteNombre;
    private Long reparacionId;
    private short puntuacion;
    private String comentario;
    private String fecha;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String c) { this.clienteNombre = c; }
    public Long getReparacionId() { return reparacionId; }
    public void setReparacionId(Long r) { this.reparacionId = r; }
    public short getPuntuacion() { return puntuacion; }
    public void setPuntuacion(short p) { this.puntuacion = p; }
    public String getComentario() { return comentario; }
    public void setComentario(String c) { this.comentario = c; }
    public String getFecha() { return fecha; }
    public void setFecha(String f) { this.fecha = f; }
}