package com.litolaser.calculoHoras.application.Dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReporteMensualDTO {

    private LocalDate fecha;

    private LocalTime horaIngreso;
    private LocalTime horaSalida;

    private BigDecimal horasNormales;
    private BigDecimal horasNocturnas;
    private BigDecimal horasExtras;
    private BigDecimal horas100;

    private BigDecimal totalAjustado;
}
