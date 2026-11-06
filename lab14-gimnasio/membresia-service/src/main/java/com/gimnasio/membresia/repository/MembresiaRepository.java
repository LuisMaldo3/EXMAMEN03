package com.gimnasio.membresia.repository;

import com.gimnasio.membresia.model.EstadoMembresia;
import com.gimnasio.membresia.model.Membresia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembresiaRepository extends JpaRepository<Membresia, Long> {

    List<Membresia> findByIdSocio(Long idSocio);

    List<Membresia> findByIdSocioAndEstado(Long idSocio, EstadoMembresia estado);
}
