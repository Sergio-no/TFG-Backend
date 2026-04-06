package org.example.tfgbackend.dto.response;

public class PiezaResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private String precioUnitario;
    private int stockActual;
    private int stockMinimo;
    private boolean stockBajo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String n) { this.nombre = n; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String d) { this.descripcion = d; }
    public String getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(String p) { this.precioUnitario = p; }
    public int getStockActual() { return stockActual; }
    public void setStockActual(int s) { this.stockActual = s; }
    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int s) { this.stockMinimo = s; }
    public boolean isStockBajo() { return stockBajo; }
    public void setStockBajo(boolean b) { this.stockBajo = b; }
}