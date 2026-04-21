package org.example.tfgbackend.dto.response;

public class CanjearRecompensaResponse {
    private boolean exito;
    private int puntosRestantes;
    private String mensaje;

    public CanjearRecompensaResponse() {}

    public CanjearRecompensaResponse(boolean exito, int puntosRestantes, String mensaje) {
        this.exito = exito;
        this.puntosRestantes = puntosRestantes;
        this.mensaje = mensaje;
    }

    public boolean isExito() { return exito; }
    public void setExito(boolean exito) { this.exito = exito; }
    public int getPuntosRestantes() { return puntosRestantes; }
    public void setPuntosRestantes(int puntosRestantes) { this.puntosRestantes = puntosRestantes; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}