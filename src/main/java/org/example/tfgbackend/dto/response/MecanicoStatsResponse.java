package org.example.tfgbackend.dto.response;

public class MecanicoStatsResponse {
    private Long mecanicoId;
    private String nombre;
    private long reparaciones;
    private String ingresos;

    public Long getMecanicoId() { return mecanicoId; }
    public void setMecanicoId(Long m) { this.mecanicoId = m; }
    public String getNombre() { return nombre; }
    public void setNombre(String n) { this.nombre = n; }
    public long getReparaciones() { return reparaciones; }
    public void setReparaciones(long r) { this.reparaciones = r; }
    public String getIngresos() { return ingresos; }
    public void setIngresos(String i) { this.ingresos = i; }
}