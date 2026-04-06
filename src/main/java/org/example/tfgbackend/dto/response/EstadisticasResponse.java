package org.example.tfgbackend.dto.response;

import java.util.List;

public class EstadisticasResponse {
    private String ingresosMes;
    private long reparacionesMes;
    private String ticketMedio;
    private double valoracionMedia;
    private List<MecanicoStatsResponse> mecanicoStats;
    private List<ValoracionResponse> ultimasValoraciones;

    public String getIngresosMes() { return ingresosMes; }
    public void setIngresosMes(String i) { this.ingresosMes = i; }
    public long getReparacionesMes() { return reparacionesMes; }
    public void setReparacionesMes(long r) { this.reparacionesMes = r; }
    public String getTicketMedio() { return ticketMedio; }
    public void setTicketMedio(String t) { this.ticketMedio = t; }
    public double getValoracionMedia() { return valoracionMedia; }
    public void setValoracionMedia(double v) { this.valoracionMedia = v; }
    public List<MecanicoStatsResponse> getMecanicoStats() { return mecanicoStats; }
    public void setMecanicoStats(List<MecanicoStatsResponse> l) { this.mecanicoStats = l; }
    public List<ValoracionResponse> getUltimasValoraciones() { return ultimasValoraciones; }
    public void setUltimasValoraciones(List<ValoracionResponse> l) { this.ultimasValoraciones = l; }
}