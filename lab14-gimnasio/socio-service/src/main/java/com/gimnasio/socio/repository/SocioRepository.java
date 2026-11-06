package com.gimnasio.socio.repository;

import com.gimnasio.socio.model.Socio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocioRepository extends JpaRepository<Socio, Long> {

    boolean existsByDni(String dni);

    boolean existsByCorreo(String correo);

    boolean existsByDniAndIdNot(String dni, Long id);

    boolean existsByCorreoAndIdNot(String correo, Long id);
}
