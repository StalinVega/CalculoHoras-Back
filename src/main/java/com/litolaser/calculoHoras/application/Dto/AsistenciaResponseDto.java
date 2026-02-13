package com.litolaser.calculoHoras.application.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AsistenciaResponseDto {

     private Long id;
    private Long usuarioId;
    private LocalDate fechaInicio;
    private LocalTime horaInicio;
    private LocalDate fechaFin;
    private LocalTime horaFin;
    private String estado;
    
}
