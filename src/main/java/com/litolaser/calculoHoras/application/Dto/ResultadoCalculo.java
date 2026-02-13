package com.litolaser.calculoHoras.application.Dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultadoCalculo {

    private BigDecimal normales;
    private BigDecimal horas25;
    private BigDecimal horas100;
    private BigDecimal horasExtras;
    private BigDecimal totalAjustado;
}

