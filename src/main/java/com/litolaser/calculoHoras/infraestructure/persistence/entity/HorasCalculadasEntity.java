package com.litolaser.calculoHoras.infraestructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "horas_calculadas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HorasCalculadasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "horas_normales", precision = 5, scale = 2)
    private BigDecimal horasNormales;

    @Column(name = "horas_25", precision = 5, scale = 2)
    private BigDecimal horas25;

    @Column(name = "horas_100", precision = 5, scale = 2)
    private BigDecimal horas100;

    @Column(name = "horas_extras", precision = 5, scale = 2)
    private BigDecimal horasExtras;

    @Column(name = "total_ajustado", precision = 6, scale = 2)
    private BigDecimal totalAjustado;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}

