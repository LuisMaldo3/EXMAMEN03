package com.gimnasio.membresia.dto;

import com.gimnasio.membresia.model.EstadoMembresia;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record MembresiaRequest(
        @NotNull(message = "El idSocio es obligatorio")
        @Positive(message = "El idSocio debe ser positivo")
        Long idSocio,

        @NotNull(message = "El idPlan es obligatorio")
        @Positive(message = "El idPlan debe ser positivo")
        Long idPlan,

        @NotNull(message = "La fecha de inicio es obligatoria")
        LocalDate fechaInicio,

        @NotNull(message = "La fecha de fin es obligatoria")
        LocalDate fechaFin,

        EstadoMembresia estado) {
}
