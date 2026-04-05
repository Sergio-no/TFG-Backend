package org.example.tfgbackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mecanico")
public class Mecanico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    private String especialidad;
    private String telefono;

    @Column(name = "activo_taller", nullable = false)
    private boolean activoTaller = true;

    public Mecanico() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String e) { this.especialidad = e; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public boolean isActivoTaller() { return activoTaller; }
    public void setActivoTaller(boolean a) { this.activoTaller = a; }
}