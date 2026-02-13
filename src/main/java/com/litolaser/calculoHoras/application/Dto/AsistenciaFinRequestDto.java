package com.litolaser.calculoHoras.application.Dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AsistenciaFinRequestDto {
    private Long usuarioId;
    private String fotoFinUrl;
    private BigDecimal latFin;
    private BigDecimal lngFin;
}
