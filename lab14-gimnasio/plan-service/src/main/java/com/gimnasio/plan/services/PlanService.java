package com.gimnasio.plan.services;

import com.gimnasio.plan.dto.PlanRequest;
import com.gimnasio.plan.dto.PlanResponse;
import com.gimnasio.plan.exception.DuplicateResourceException;
import com.gimnasio.plan.exception.ResourceNotFoundException;
import com.gimnasio.plan.model.EstadoPlan;
import com.gimnasio.plan.model.Plan;
import com.gimnasio.plan.repository.PlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlanService {

    private final PlanRepository repository;

    public PlanService(PlanRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<PlanResponse> listar() {
        return repository.findAll().stream().map(PlanResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public PlanResponse buscarPorId(Long id) {
        return PlanResponse.from(obtener(id));
    }

    @Transactional
    public PlanResponse crear(PlanRequest request) {
        if (repository.existsByNombreIgnoreCase(request.nombre().trim())) {
            throw new DuplicateResourceException("Ya existe un plan llamado '" + request.nombre().trim() + "'");
        }
        Plan plan = new Plan();
        aplicar(plan, request);
        return PlanResponse.from(repository.save(plan));
    }

    @Transactional
    public PlanResponse actualizar(Long id, PlanRequest request) {
        Plan plan = obtener(id);
        if (repository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(), id)) {
            throw new DuplicateResourceException("Ya existe otro plan llamado '" + request.nombre().trim() + "'");
        }
        aplicar(plan, request);
        return PlanResponse.from(repository.save(plan));
    }

    @Transactional
    public void eliminar(Long id) {
        repository.delete(obtener(id));
    }

    private Plan obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El plan con id " + id + " no existe"));
    }

    private void aplicar(Plan plan, PlanRequest r) {
        plan.setNombre(r.nombre().trim());
        plan.setDescripcion(r.descripcion());
        plan.setPrecio(r.precio());
        plan.setDuracionDias(r.duracionDias());
        plan.setEstado(r.estado() == null ? EstadoPlan.ACTIVO : r.estado());
    }
}
