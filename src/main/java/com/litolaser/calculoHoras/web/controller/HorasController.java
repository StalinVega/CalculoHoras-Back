package com.litolaser.calculoHoras.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.litolaser.calculoHoras.application.Services.HorasService;
import com.litolaser.calculoHoras.infraestructure.persistence.entity.HorasCalculadasEntity;
import com.litolaser.calculoHoras.shared.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/horas")
@RequiredArgsConstructor
public class HorasController {

    private final HorasService service;

    @PreAuthorize("hasAnyRole('ADMIN','TECNICO')")
    @PostMapping("/calcular/mes")
    public ResponseEntity<ApiResponse<String>> calcularMes(
            @RequestParam Long usuarioId,
            @RequestParam int anio,
            @RequestParam int mes) {

        service.calcularMes(usuarioId, anio, mes);

        return ResponseEntity.ok(
                ApiResponse.success("Horas calculadas correctamente"));
    }

    @PreAuthorize("hasAnyRole('ADMIN','TECNICO')")
    @GetMapping("/informe-semanal")
    public ResponseEntity<ApiResponse<List<HorasCalculadasEntity>>> informe(
            @RequestParam Long usuarioId,
            @RequestParam int anio,
            @RequestParam int mes) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        service.informeSemanal(usuarioId, anio, mes),
                        "Informe semanal generado correctamente"));

    }
}
