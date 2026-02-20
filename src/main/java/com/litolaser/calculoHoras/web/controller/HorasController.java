package com.litolaser.calculoHoras.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.litolaser.calculoHoras.application.Dto.ReporteMensualDTO;
import com.litolaser.calculoHoras.application.Services.HorasService;
import com.litolaser.calculoHoras.infraestructure.persistence.entity.HorasCalculadasEntity;
import com.litolaser.calculoHoras.shared.response.ApiResponse;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Horas", description = "Operaciones relacionadas al cálculo de horas")
@RestController
@RequestMapping("/api/horas")
@RequiredArgsConstructor
public class HorasController {

        private final HorasService service;

        @PreAuthorize("hasAnyRole('ADMIN','TECNICO')")
        @GetMapping("/calcular/mes")
        public ResponseEntity<ApiResponse<String>> calcularMes(
                        @RequestParam Long usuarioId,
                        @RequestParam int anio,
                        @RequestParam int mes) {

                service.calcularMes(usuarioId, anio, mes);

                return ResponseEntity.ok(
                                ApiResponse.success("Horas calculadas correctamente"));
        }

        @Operation(summary = "Obtener informe semanal", description = "Devuelve el listado de horas calculadas por usuario, año y mes")
        @ApiResponses(value = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Informe generado correctamente")
        })
        @PreAuthorize("hasAnyRole('ADMIN','TECNICO')")
        @GetMapping("/informe-semanal")
        public ResponseEntity<ApiResponse<List<HorasCalculadasEntity>>> informe(
                        @Parameter(description = "ID del usuario", example = "1") @RequestParam Long usuarioId,
                        @Parameter(description = "Año del informe", example = "2025") @RequestParam int anio,
                        @Parameter(description = "Mes del informe", example = "5") @RequestParam int mes) {

                return ResponseEntity.ok(
                                ApiResponse.success(
                                                service.informeSemanal(usuarioId, anio, mes),
                                                "Informe semanal generado correctamente"));

        }

        @PreAuthorize("hasAnyRole('ADMIN','TECNICO')")
        @GetMapping("/reporte-mensual/{usuarioId}")
        public ResponseEntity<ApiResponse<List<ReporteMensualDTO>>> reporteMensual(
                        @PathVariable Long usuarioId) {

                return ResponseEntity.ok(
                                ApiResponse.success(
                                                service.reporteMensual(usuarioId),
                                                "Reporte mensual generado correctamente"));
        }
}
