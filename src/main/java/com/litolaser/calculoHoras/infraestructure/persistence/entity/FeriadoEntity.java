package com.litolaser.calculoHoras.infraestructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "feriados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeriadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private LocalDate fecha;

    @Column(length = 150)
    private String descripcion;
}

