package org.example.tfgbackend.dto.response;

public class VehiculoResponse {
    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private String marca;
    private String modelo;
    private short anio;
    private String matricula;
    private int kilometraje;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long c) { this.clienteId = c; }
    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String c) { this.clienteNombre = c; }
    public String getMarca() { return marca; }
    public void setMarca(String m) { this.marca = m; }
    public String getModelo() { return modelo; }
    public void setModelo(String m) { this.modelo = m; }
    public short getAnio() { return anio; }
    public void setAnio(short a) { this.anio = a; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String m) { this.matricula = m; }
    public int getKilometraje() { return kilometraje; }
    public void setKilometraje(int k) { this.kilometraje = k; }
}