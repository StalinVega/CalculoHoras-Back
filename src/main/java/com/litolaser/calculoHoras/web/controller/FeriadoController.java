package com.litolaser.calculoHoras.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.litolaser.calculoHoras.application.Dto.FeriadoRequestDto;
import com.litolaser.calculoHoras.application.Dto.FeriadoResponseDto;
import com.litolaser.calculoHoras.application.Services.FeriadoService;
import com.litolaser.calculoHoras.shared.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/feriados")
@RequiredArgsConstructor
public class FeriadoController {

    private final FeriadoService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<FeriadoResponseDto>> crear(
            @RequestBody FeriadoRequestDto request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        service.crear(request),
                        "Feriado creado correctamente"));
    }

    @PreAuthorize("hasAnyRole('ADMIN','TECNICO')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<FeriadoResponseDto>>> listar() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        service.listar(),
                        "Feriados obtenidos correctamente"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FeriadoResponseDto>> actualizar(@PathVariable Long id,
            @RequestBody FeriadoRequestDto request) {
        FeriadoResponseDto response = service.actualizar(id, request);
        return ResponseEntity.ok(ApiResponse.success(response, "Feriado actualizado correctamente"));
    }

}
