package com.gimnasio.plan.dto;

import com.gimnasio.plan.model.EstadoPlan;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record PlanRequest(
        @NotBlank(message = "El nombre del plan es obligatorio")
        @Size(max = 60, message = "El nombre no puede superar 60 caracteres")
        String nombre,

        @Size(max = 200, message = "La descripción no puede superar 200 caracteres")
        String descripcion,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor que cero")
        @Digits(integer = 8, fraction = 2, message = "El precio admite hasta 8 enteros y 2 decimales")
        BigDecimal precio,

        @NotNull(message = "La duración en días es obligatoria")
        @Min(value = 1, message = "La duración debe ser de al menos 1 día")
        Integer duracionDias,

        EstadoPlan estado) {
}
