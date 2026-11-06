package com.gimnasio.membresia.dto;

/** Datos del socio tal como los devuelve socio-service. */
public record SocioDto(
        Long id,
        String dni,
        String nombres,
        String apellidos,
        String correo,
        String telefono,
        String estado) {
}
