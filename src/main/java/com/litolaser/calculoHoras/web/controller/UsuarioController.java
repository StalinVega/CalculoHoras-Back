package com.litolaser.calculoHoras.web.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.litolaser.calculoHoras.application.Dto.UsuarioRequestDto;
import com.litolaser.calculoHoras.application.Dto.UsuarioResponseDTO;
import com.litolaser.calculoHoras.application.Services.UsuarioService;
import com.litolaser.calculoHoras.shared.response.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioResponseDTO>> crear(
            @RequestBody UsuarioRequestDto request) {

        UsuarioResponseDTO usuario = service.crearUsuario(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(usuario, "Usuario creado correctamente"));
    }

    @PreAuthorize("hasAnyRole('ADMIN','TECNICO')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuarioResponseDTO>>> listar() {

        List<UsuarioResponseDTO> lista = service.listarUsuarios();

        return ResponseEntity.ok(
                ApiResponse.success(lista, "Usuarios obtenidos correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioResponseDTO>> obtener(
            @PathVariable Long id) {

        UsuarioResponseDTO usuario = service.obtenerPorId(id);

        return ResponseEntity.ok(
                ApiResponse.success(usuario, "Usuario obtenido correctamente"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioResponseDTO>> actualizar(
            @PathVariable Long id,
            @RequestBody UsuarioRequestDto request) {

        UsuarioResponseDTO usuario = service.actualizar(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(usuario, "Usuario actualizado correctamente"));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<UsuarioResponseDTO>> cambiarEstado(
            @PathVariable Long id,
            @RequestParam Boolean activo) {

        UsuarioResponseDTO usuario = service.cambiarEstado(id, activo);

        return ResponseEntity.ok(
                ApiResponse.success(usuario, "Estado actualizado correctamente"));
    }

}
