package com.litolaser.calculoHoras.infraestructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "asistencias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsistenciaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔥 Relación real
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "hora_fin")
    private LocalTime horaFin;

    @Column(name = "foto_inicio_url", columnDefinition = "TEXT")
    private String fotoInicioUrl;

    @Column(name = "lat_inicio", precision = 10, scale = 8)
    private BigDecimal latInicio;

    @Column(name = "lng_inicio", precision = 11, scale = 8)
    private BigDecimal lngInicio;

    @Column(name = "foto_fin_url", columnDefinition = "TEXT")
    private String fotoFinUrl;

    @Column(name = "lat_fin", precision = 10, scale = 8)
    private BigDecimal latFin;

    @Column(name = "lng_fin", precision = 11, scale = 8)
    private BigDecimal lngFin;

    @Column(length = 20)
    private String estado;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = "ABIERTO";
        }
    }
}

