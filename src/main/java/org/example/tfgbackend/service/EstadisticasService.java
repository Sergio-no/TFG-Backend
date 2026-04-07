package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.response.*;
import org.example.tfgbackend.model.Reparacion;
import org.example.tfgbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EstadisticasService {
    @Autowired private FacturaRepository    facturaRepo;
    @Autowired private ReparacionRepository reparacionRepo;
    @Autowired private ValoracionRepository valoracionRepo;
    @Autowired private MecanicoRepository   mecanicoRepo;

    public EstadisticasResponse getEstadisticas() {
        LocalDateTime inicioMes = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime finMes    = inicioMes.plusMonths(1).minusSeconds(1);

        BigDecimal ingresosMes = facturaRepo
                .sumTotalPagadoEntreFechas(inicioMes, finMes);
        if (ingresosMes == null) ingresosMes = BigDecimal.ZERO;

        List<Reparacion> repsMes = reparacionRepo.findAll().stream()
                .filter(r -> !r.getFechaInicio().isBefore(inicioMes.toLocalDate()))
                .toList();

        BigDecimal ticket = repsMes.isEmpty() ? BigDecimal.ZERO :
                ingresosMes.divide(BigDecimal.valueOf(repsMes.size()), 2, RoundingMode.HALF_UP);

        Double media = valoracionRepo.findMediaPuntuacion();

        // Stats por mecánico
        Map<Long, List<Reparacion>> porMecanico = repsMes.stream()
                .collect(Collectors.groupingBy(r -> r.getMecanico().getId()));

        List<MecanicoStatsResponse> mecStats = mecanicoRepo.findAll().stream()
                .map(m -> {
                    List<Reparacion> reps = porMecanico.getOrDefault(m.getId(), List.of());
                    BigDecimal ing = reps.stream()
                            .map(Reparacion::getCosteTotal)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    MecanicoStatsResponse ms = new MecanicoStatsResponse();
                    ms.setMecanicoId(m.getId());
                    ms.setNombre(m.getNombre() + " " + m.getApellidos());
                    ms.setReparaciones(reps.size());
                    ms.setIngresos(ing.toPlainString() + "€");
                    return ms;
                }).toList();

        List<ValoracionResponse> ultimasVal = valoracionRepo
                .findTop10ByOrderByFechaDesc().stream()
                .map(v -> {
                    ValoracionResponse vr = new ValoracionResponse();
                    vr.setId(v.getId());
                    vr.setClienteNombre(v.getCliente().getUsuario().getNombre());
                    vr.setReparacionId(v.getReparacion().getId());
                    vr.setPuntuacion(v.getPuntuacion());
                    vr.setComentario(v.getComentario());
                    vr.setFecha(v.getFecha().toString());
                    return vr;
                }).toList();

        EstadisticasResponse res = new EstadisticasResponse();
        res.setIngresosMes(ingresosMes.toPlainString() + "€");
        res.setReparacionesMes(repsMes.size());
        res.setTicketMedio(ticket.toPlainString() + "€");
        res.setValoracionMedia(media != null ?
                BigDecimal.valueOf(media).setScale(1, RoundingMode.HALF_UP).doubleValue() : 0);
        res.setMecanicoStats(mecStats);
        res.setUltimasValoraciones(ultimasVal);
        return res;
    }
}
