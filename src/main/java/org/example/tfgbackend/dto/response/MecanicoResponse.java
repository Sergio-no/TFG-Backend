package org.example.tfgbackend.dto.response;

public class MecanicoResponse {
    private Long id;
    private String nombre;
    private String apellidos;
    private String especialidad;
    private String telefono;
    private boolean activoTaller;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String n) { this.nombre = n; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String a) { this.apellidos = a; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String e) { this.especialidad = e; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String t) { this.telefono = t; }
    public boolean isActivoTaller() { return activoTaller; }
    public void setActivoTaller(boolean a) { this.activoTaller = a; }
}