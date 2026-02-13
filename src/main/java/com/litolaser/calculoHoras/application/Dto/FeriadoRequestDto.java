package com.litolaser.calculoHoras.application.Dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class FeriadoRequestDto {

    private String descripcion;
    private LocalDate fecha;
}

