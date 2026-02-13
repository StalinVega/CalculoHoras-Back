package com.litolaser.calculoHoras.application.Services;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.litolaser.calculoHoras.application.Dto.AsistenciaFinRequestDto;
import com.litolaser.calculoHoras.application.Dto.AsistenciaInicioRequestDto;
import com.litolaser.calculoHoras.application.Dto.AsistenciaResponseDto;
import com.litolaser.calculoHoras.infraestructure.persistence.entity.AsistenciaEntity;
import com.litolaser.calculoHoras.infraestructure.persistence.entity.UsuarioEntity;
import com.litolaser.calculoHoras.infraestructure.persistence.repository.AsistenciaRepository;
import com.litolaser.calculoHoras.infraestructure.persistence.repository.UsuarioJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;
    private final UsuarioJpaRepository usuarioRepository;

    public AsistenciaResponseDto iniciar(AsistenciaInicioRequestDto request) {

        UsuarioEntity usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }

        asistenciaRepository.findByUsuarioAndEstado(usuario, "ABIERTO")
                .ifPresent(a -> {
                    throw new RuntimeException("Ya existe una asistencia abierta");
                });

        AsistenciaEntity asistencia = AsistenciaEntity.builder()
                .usuario(usuario)
                .fechaInicio(LocalDate.now())
                .horaInicio(LocalTime.now())
                .fotoInicioUrl(request.getFotoInicioUrl())
                .latInicio(request.getLatInicio())
                .lngInicio(request.getLngInicio())
                .estado("ABIERTO")
                .build();

        asistenciaRepository.save(asistencia);

        return mapToDTO(asistencia);
    }

    public AsistenciaResponseDto cerrar(AsistenciaFinRequestDto request) {

        UsuarioEntity usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        AsistenciaEntity asistencia = asistenciaRepository
                .findByUsuarioAndEstado(usuario, "ABIERTO")
                .orElseThrow(() -> new RuntimeException("No existe asistencia abierta"));

        asistencia.setFechaFin(LocalDate.now());
        asistencia.setHoraFin(LocalTime.now());
        asistencia.setFotoFinUrl(request.getFotoFinUrl());
        asistencia.setLatFin(request.getLatFin());
        asistencia.setLngFin(request.getLngFin());
        asistencia.setEstado("CERRADO");

        asistenciaRepository.save(asistencia);

        return mapToDTO(asistencia);
    }

    private AsistenciaResponseDto mapToDTO(AsistenciaEntity asistencia) {
        return AsistenciaResponseDto.builder()
                .id(asistencia.getId())
                .usuarioId(asistencia.getUsuario().getId())
                .fechaInicio(asistencia.getFechaInicio())
                .horaInicio(asistencia.getHoraInicio())
                .fechaFin(asistencia.getFechaFin())
                .horaFin(asistencia.getHoraFin())
                .estado(asistencia.getEstado())
                .build();
    }
}
