package com.gimnasio.plan.dto;

import com.gimnasio.plan.model.EstadoPlan;
import com.gimnasio.plan.model.Plan;

import java.math.BigDecimal;

public record PlanResponse(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        Integer duracionDias,
        EstadoPlan estado) {

    public static PlanResponse from(Plan p) {
        return new PlanResponse(p.getId(), p.getNombre(), p.getDescripcion(), p.getPrecio(),
                p.getDuracionDias(), p.getEstado());
    }
}
