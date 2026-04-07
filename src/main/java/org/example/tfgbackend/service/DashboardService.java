package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.response.*;
import org.example.tfgbackend.repository.*;
import org.example.tfgbackend.util.CitaMapper;
import org.example.tfgbackend.util.ReparacionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DashboardService {
    @Autowired private CitaRepository     citaRepo;
    @Autowired private ReparacionRepository reparacionRepo;
    @Autowired private PiezaRepository    piezaRepo;
    @Autowired private FacturaRepository  facturaRepo;

    public DashboardResponse getDashboard() {
        LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
        LocalDateTime finDia    = inicioDia.plusDays(1).minusSeconds(1);

        List<CitaResponse> citasHoy = citaRepo
                .findCitasHoy(inicioDia, finDia).stream()
                .map(CitaMapper::toResponse).toList();

        List<ReparacionResponse> activas = reparacionRepo
                .findActivas().stream()
                .map(ReparacionMapper::toResponse).toList();

        List<PiezaResponse> stockBajo = piezaRepo.findPiezasConStockBajo().stream()
                .map(p -> {
                    PiezaResponse pr = new PiezaResponse();
                    pr.setId(p.getId());
                    pr.setNombre(p.getNombre());
                    pr.setStockActual(p.getStockActual());
                    pr.setStockMinimo(p.getStockMinimo());
                    pr.setStockBajo(true);
                    pr.setPrecioUnitario(p.getPrecioUnitario().toPlainString());
                    return pr;
                }).toList();

        BigDecimal facturacionHoy = facturaRepo
                .sumTotalPagadoEntreFechas(inicioDia, finDia);

        long pendientes = citasHoy.stream()
                .filter(c -> "PENDIENTE".equals(c.getEstado())).count();

        DashboardResponse resp = new DashboardResponse();
        resp.setCitasHoy(citasHoy.size());
        resp.setCitasPendientes(pendientes);
        resp.setReparacionesActivas(activas.size());
        resp.setFacturacionHoy(facturacionHoy != null ?
                facturacionHoy.toPlainString() + "€" : "0€");
        resp.setAlertasStock(stockBajo.size());
        resp.setProximasCitas(citasHoy);
        resp.setReparacionesEnCurso(activas);
        resp.setPiezasStockBajo(stockBajo);
        return resp;
    }
}
