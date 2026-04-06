package org.example.tfgbackend.dto.response;

import java.util.List;

public class DashboardResponse {
    private long citasHoy;
    private long citasPendientes;
    private long reparacionesActivas;
    private String facturacionHoy;
    private long alertasStock;
    private List<CitaResponse> proximasCitas;
    private List<ReparacionResponse> reparacionesEnCurso;
    private List<PiezaResponse> piezasStockBajo;

    public long getCitasHoy() { return citasHoy; }
    public void setCitasHoy(long c) { this.citasHoy = c; }
    public long getCitasPendientes() { return citasPendientes; }
    public void setCitasPendientes(long c) { this.citasPendientes = c; }
    public long getReparacionesActivas() { return reparacionesActivas; }
    public void setReparacionesActivas(long r) { this.reparacionesActivas = r; }
    public String getFacturacionHoy() { return facturacionHoy; }
    public void setFacturacionHoy(String f) { this.facturacionHoy = f; }
    public long getAlertasStock() { return alertasStock; }
    public void setAlertasStock(long a) { this.alertasStock = a; }
    public List<CitaResponse> getProximasCitas() { return proximasCitas; }
    public void setProximasCitas(List<CitaResponse> l) { this.proximasCitas = l; }
    public List<ReparacionResponse> getReparacionesEnCurso() { return reparacionesEnCurso; }
    public void setReparacionesEnCurso(List<ReparacionResponse> l) { this.reparacionesEnCurso = l; }
    public List<PiezaResponse> getPiezasStockBajo() { return piezasStockBajo; }
    public void setPiezasStockBajo(List<PiezaResponse> l) { this.piezasStockBajo = l; }
}