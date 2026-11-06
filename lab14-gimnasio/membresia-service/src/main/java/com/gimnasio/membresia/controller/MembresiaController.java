package com.gimnasio.membresia.controller;

import com.gimnasio.membresia.dto.MembresiaRequest;
import com.gimnasio.membresia.dto.MembresiaResponse;
import com.gimnasio.membresia.services.MembresiaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membresias")
public class MembresiaController {

    private final MembresiaService service;

    public MembresiaController(MembresiaService service) {
        this.service = service;
    }

    @GetMapping
    public List<MembresiaResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public MembresiaResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/socio/{idSocio}")
    public List<MembresiaResponse> listarPorSocio(@PathVariable Long idSocio) {
        return service.listarPorSocio(idSocio);
    }

    @PostMapping
    public ResponseEntity<MembresiaResponse> crear(@Valid @RequestBody MembresiaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(request));
    }

    @PutMapping("/{id}")
    public MembresiaResponse actualizar(@PathVariable Long id, @Valid @RequestBody MembresiaRequest request) {
        return service.actualizar(id, request);
    }

    @PatchMapping("/{id}/cancelar")
    public MembresiaResponse cancelar(@PathVariable Long id) {
        return service.cancelar(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
