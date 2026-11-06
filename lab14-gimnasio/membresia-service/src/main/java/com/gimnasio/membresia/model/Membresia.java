package com.gimnasio.membresia.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "membresias")
public class Membresia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Referencia por ID al microservicio de socios (sin llave foránea entre bases). */
    @Column(nullable = false)
    private Long idSocio;

    /** Referencia por ID al microservicio de planes. */
    @Column(nullable = false)
    private Long idPlan;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    /** Precio del plan al momento de registrar la membresía (copia histórica). */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioPlan;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20)")
    private EstadoMembresia estado = EstadoMembresia.ACTIVA;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdSocio() { return idSocio; }
    public void setIdSocio(Long idSocio) { this.idSocio = idSocio; }
    public Long getIdPlan() { return idPlan; }
    public void setIdPlan(Long idPlan) { this.idPlan = idPlan; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public BigDecimal getPrecioPlan() { return precioPlan; }
    public void setPrecioPlan(BigDecimal precioPlan) { this.precioPlan = precioPlan; }
    public EstadoMembresia getEstado() { return estado; }
    public void setEstado(EstadoMembresia estado) { this.estado = estado; }
}
