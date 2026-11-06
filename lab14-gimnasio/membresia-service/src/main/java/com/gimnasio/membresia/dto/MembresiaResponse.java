package com.gimnasio.membresia.dto;

import com.gimnasio.membresia.model.EstadoMembresia;
import com.gimnasio.membresia.model.Membresia;

import java.math.BigDecimal;
import java.time.LocalDate;

/** socio y plan vienen en null cuando no se consultó a los otros microservicios (listados simples). */
public record MembresiaResponse(
        Long id,
        Long idSocio,
        Long idPlan,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        BigDecimal precioPlan,
        EstadoMembresia estado,
        SocioDto socio,
        PlanDto plan) {

    public static MembresiaResponse from(Membresia m) {
        return from(m, null, null);
    }

    public static MembresiaResponse from(Membresia m, SocioDto socio, PlanDto plan) {
        return new MembresiaResponse(m.getId(), m.getIdSocio(), m.getIdPlan(), m.getFechaInicio(),
                m.getFechaFin(), m.getPrecioPlan(), m.getEstado(), socio, plan);
    }
}
