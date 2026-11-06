package com.gimnasio.membresia.dto;

import java.math.BigDecimal;

/** Datos del plan tal como los devuelve plan-service. */
public record PlanDto(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        Integer duracionDias,
        String estado) {
}
