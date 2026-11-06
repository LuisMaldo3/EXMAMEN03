package com.gimnasio.socio.services;

import com.gimnasio.socio.dto.SocioRequest;
import com.gimnasio.socio.dto.SocioResponse;
import com.gimnasio.socio.exception.DuplicateResourceException;
import com.gimnasio.socio.exception.ResourceNotFoundException;
import com.gimnasio.socio.model.EstadoSocio;
import com.gimnasio.socio.model.Socio;
import com.gimnasio.socio.repository.SocioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SocioService {

    private final SocioRepository repository;

    public SocioService(SocioRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<SocioResponse> listar() {
        return repository.findAll().stream().map(SocioResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public SocioResponse buscarPorId(Long id) {
        return SocioResponse.from(obtener(id));
    }

    @Transactional
    public SocioResponse crear(SocioRequest request) {
        if (repository.existsByDni(request.dni())) {
            throw new DuplicateResourceException("Ya existe un socio con el DNI " + request.dni());
        }
        if (repository.existsByCorreo(request.correo())) {
            throw new DuplicateResourceException("Ya existe un socio con el correo " + request.correo());
        }
        Socio socio = new Socio();
        aplicar(socio, request);
        return SocioResponse.from(repository.save(socio));
    }

    @Transactional
    public SocioResponse actualizar(Long id, SocioRequest request) {
        Socio socio = obtener(id);
        if (repository.existsByDniAndIdNot(request.dni(), id)) {
            throw new DuplicateResourceException("Ya existe otro socio con el DNI " + request.dni());
        }
        if (repository.existsByCorreoAndIdNot(request.correo(), id)) {
            throw new DuplicateResourceException("Ya existe otro socio con el correo " + request.correo());
        }
        aplicar(socio, request);
        return SocioResponse.from(repository.save(socio));
    }

    @Transactional
    public void eliminar(Long id) {
        repository.delete(obtener(id));
    }

    private Socio obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El socio con id " + id + " no existe"));
    }

    private void aplicar(Socio socio, SocioRequest r) {
        socio.setDni(r.dni());
        socio.setNombres(r.nombres().trim());
        socio.setApellidos(r.apellidos().trim());
        socio.setCorreo(r.correo().trim().toLowerCase());
        socio.setTelefono(r.telefono() == null || r.telefono().isBlank() ? null : r.telefono());
        socio.setEstado(r.estado() == null ? EstadoSocio.ACTIVO : r.estado());
    }
}
