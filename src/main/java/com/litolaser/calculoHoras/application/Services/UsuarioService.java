package com.litolaser.calculoHoras.application.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.litolaser.calculoHoras.application.Dto.UsuarioRequestDto;
import com.litolaser.calculoHoras.application.Dto.UsuarioResponseDTO;
import com.litolaser.calculoHoras.infraestructure.persistence.entity.UsuarioEntity;
import com.litolaser.calculoHoras.infraestructure.persistence.repository.UsuarioJpaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioJpaRepository repository;

    // CREATE
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDto request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        if (repository.existsByCedula(request.getCedula())) {
            throw new RuntimeException("La cédula ya está registrada");
        }

        UsuarioEntity usuario = UsuarioEntity.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .cedula(request.getCedula())
                .email(request.getEmail())
                .passwordHash(request.getPassword()) // luego lo encriptamos 🔐
                .rol(request.getRol())
                .activo(true)
                .build();

        UsuarioEntity saved = repository.save(usuario);

        return mapToResponse(saved);
    }

    // GET ALL
    public List<UsuarioResponseDTO> listarUsuarios() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public UsuarioResponseDTO obtenerPorId(Long id) {
        UsuarioEntity usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return mapToResponse(usuario);
    }

    // UPDATE
    public UsuarioResponseDTO actualizar(Long id, UsuarioRequestDto request) {
        UsuarioEntity usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setRol(request.getRol());

        return mapToResponse(repository.save(usuario));
    }

    // DELETE (soft delete recomendado)
    public void eliminar(Long id) {
        UsuarioEntity usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setActivo(false);
        repository.save(usuario);
    }

    private UsuarioResponseDTO mapToResponse(UsuarioEntity usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .cedula(usuario.getCedula())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .activo(usuario.getActivo())
                .build();
    }
}
