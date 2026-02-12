package com.litolaser.calculoHoras.web.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.litolaser.calculoHoras.application.Dto.UsuarioRequestDto;
import com.litolaser.calculoHoras.application.Dto.UsuarioResponseDTO;
import com.litolaser.calculoHoras.application.Services.UsuarioService;
import com.litolaser.calculoHoras.infraestructure.persistence.entity.UsuarioEntity;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public UsuarioResponseDTO crear(@RequestBody UsuarioRequestDto request) {
        return service.crearUsuario(request);
    }

    @GetMapping
    public List<UsuarioResponseDTO> listar() {
        return service.listarUsuarios();
    }

    @GetMapping("/{id}")
    public UsuarioResponseDTO obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public UsuarioResponseDTO actualizar(
            @PathVariable Long id,
            @RequestBody UsuarioRequestDto request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<UsuarioEntity> cambiarEstado(
            @PathVariable Long id,
            @RequestParam Boolean activo) {

        return ResponseEntity.ok(service.cambiarEstado(id, activo));
    }

}
