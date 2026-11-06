package com.gimnasio.socio.dto;

import com.gimnasio.socio.model.EstadoSocio;
import com.gimnasio.socio.model.Socio;

public record SocioResponse(
        Long id,
        String dni,
        String nombres,
        String apellidos,
        String correo,
        String telefono,
        EstadoSocio estado) {

    public static SocioResponse from(Socio s) {
        return new SocioResponse(s.getId(), s.getDni(), s.getNombres(), s.getApellidos(),
                s.getCorreo(), s.getTelefono(), s.getEstado());
    }
}
