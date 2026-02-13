package com.litolaser.calculoHoras.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.litolaser.calculoHoras.application.Dto.AsistenciaFinRequestDto;
import com.litolaser.calculoHoras.application.Dto.AsistenciaInicioRequestDto;
import com.litolaser.calculoHoras.application.Dto.AsistenciaResponseDto;
import com.litolaser.calculoHoras.application.Services.AsistenciaService;
import com.litolaser.calculoHoras.shared.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/asistencias")
@RequiredArgsConstructor
public class AsistenciaController {

    private final AsistenciaService service;

    @PreAuthorize("hasRole('TECNICO')")
    @PostMapping("/iniciar")
    public ResponseEntity<ApiResponse<AsistenciaResponseDto>> iniciar(
            @RequestBody AsistenciaInicioRequestDto request) {

        return ResponseEntity.ok(
                ApiResponse.success(service.iniciar(request), "Asistencia iniciada correctamente"));
    }

    @PreAuthorize("hasRole('TECNICO')")
    @PostMapping("/cerrar")
    public ResponseEntity<ApiResponse<AsistenciaResponseDto>> cerrar(
            @RequestBody AsistenciaFinRequestDto request) {

        return ResponseEntity.ok(
                ApiResponse.success(service.cerrar(request), "Asistencia cerrada correctamente"));
    }
}
