package com.litolaser.calculoHoras.application.Dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AsistenciaInicioRequestDto {
    private Long usuarioId;
    private String fotoInicioUrl;
    private BigDecimal latInicio;
    private BigDecimal lngInicio;
}
