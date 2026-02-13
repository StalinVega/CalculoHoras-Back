package com.litolaser.calculoHoras.application.Dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeriadoResponseDto {

    private Long id;
    private String descripcion;
    private LocalDate fecha;
}
