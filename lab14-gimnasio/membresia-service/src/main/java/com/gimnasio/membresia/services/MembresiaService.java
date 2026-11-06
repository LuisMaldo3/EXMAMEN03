package com.gimnasio.membresia.services;

import com.gimnasio.membresia.client.PlanClient;
import com.gimnasio.membresia.client.SocioClient;
import com.gimnasio.membresia.dto.MembresiaRequest;
import com.gimnasio.membresia.dto.MembresiaResponse;
import com.gimnasio.membresia.dto.PlanDto;
import com.gimnasio.membresia.dto.SocioDto;
import com.gimnasio.membresia.exception.BusinessException;
import com.gimnasio.membresia.exception.ResourceNotFoundException;
import com.gimnasio.membresia.model.EstadoMembresia;
import com.gimnasio.membresia.model.Membresia;
import com.gimnasio.membresia.repository.MembresiaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MembresiaService {

    private final MembresiaRepository repository;
    private final SocioClient socioClient;
    private final PlanClient planClient;

    public MembresiaService(MembresiaRepository repository, SocioClient socioClient, PlanClient planClient) {
        this.repository = repository;
        this.socioClient = socioClient;
        this.planClient = planClient;
    }

    /** Listado simple, sin llamar a otros microservicios. */
    @Transactional(readOnly = true)
    public List<MembresiaResponse> listar() {
        return repository.findAll().stream().map(MembresiaResponse::from).toList();
    }

    /** Detalle enriquecido con los datos del socio y del plan. */
    @Transactional(readOnly = true)
    public MembresiaResponse buscarPorId(Long id) {
        Membresia m = obtener(id);
        return MembresiaResponse.from(m, socioClient.obtener(m.getIdSocio()), planClient.obtener(m.getIdPlan()));
    }

    /** Membresías de un socio (método de repositorio findByIdSocio). */
    @Transactional(readOnly = true)
    public List<MembresiaResponse> listarPorSocio(Long idSocio) {
        SocioDto socio = socioClient.obtener(idSocio);   // 404 si el socio no existe
        Map<Long, PlanDto> planes = new HashMap<>();
        return repository.findByIdSocio(idSocio).stream()
                .map(m -> MembresiaResponse.from(m, socio,
                        planes.computeIfAbsent(m.getIdPlan(), planClient::obtener)))
                .toList();
    }

    @Transactional
    public MembresiaResponse crear(MembresiaRequest request) {
        Membresia m = new Membresia();
        PlanDto plan = validarYAplicar(m, request);
        return MembresiaResponse.from(repository.save(m), null, plan);
    }

    @Transactional
    public MembresiaResponse actualizar(Long id, MembresiaRequest request) {
        Membresia m = obtener(id);
        PlanDto plan = validarYAplicar(m, request);
        return MembresiaResponse.from(repository.save(m), null, plan);
    }

    @Transactional
    public MembresiaResponse cancelar(Long id) {
        Membresia m = obtener(id);
        if (m.getEstado() == EstadoMembresia.CANCELADA) {
            throw new BusinessException("La membresía " + id + " ya está cancelada");
        }
        m.setEstado(EstadoMembresia.CANCELADA);
        return MembresiaResponse.from(repository.save(m));
    }

    @Transactional
    public void eliminar(Long id) {
        repository.delete(obtener(id));
    }

    private Membresia obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La membresía con id " + id + " no existe"));
    }

    /**
     * Reglas de negocio:
     *  - fechaFin debe ser posterior a fechaInicio
     *  - el socio debe existir y estar ACTIVO (consulta a socio-service)
     *  - el plan debe existir, estar ACTIVO y tener precio mayor que cero (consulta a plan-service)
     */
    private PlanDto validarYAplicar(Membresia m, MembresiaRequest r) {
        if (!r.fechaFin().isAfter(r.fechaInicio())) {
            throw new BusinessException("La fecha de fin debe ser posterior a la fecha de inicio");
        }
        SocioDto socio = socioClient.obtener(r.idSocio());
        if (!"ACTIVO".equals(socio.estado())) {
            throw new BusinessException("El socio " + r.idSocio() + " no está ACTIVO");
        }
        PlanDto plan = planClient.obtener(r.idPlan());
        if (!"ACTIVO".equals(plan.estado())) {
            throw new BusinessException("El plan " + r.idPlan() + " no está ACTIVO");
        }
        if (plan.precio() == null || plan.precio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("El plan " + r.idPlan() + " tiene un precio inválido");
        }
        m.setIdSocio(r.idSocio());
        m.setIdPlan(r.idPlan());
        m.setFechaInicio(r.fechaInicio());
        m.setFechaFin(r.fechaFin());
        m.setPrecioPlan(plan.precio());
        m.setEstado(r.estado() == null ? EstadoMembresia.ACTIVA : r.estado());
        return plan;
    }
}
