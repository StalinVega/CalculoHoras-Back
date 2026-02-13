package com.litolaser.calculoHoras.application.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.litolaser.calculoHoras.application.Dto.FeriadoRequestDto;
import com.litolaser.calculoHoras.application.Dto.FeriadoResponseDto;
import com.litolaser.calculoHoras.infraestructure.persistence.entity.FeriadoEntity;
import com.litolaser.calculoHoras.infraestructure.persistence.repository.FeriadoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeriadoService {

    private final FeriadoRepository repository;

    public FeriadoResponseDto crear(FeriadoRequestDto request) {

        if (repository.existsByFecha(request.getFecha())) {
            throw new RuntimeException("Ya existe un feriado en esa fecha");
        }

        FeriadoEntity feriado = FeriadoEntity.builder()
                .descripcion(request.getDescripcion())
                .fecha(request.getFecha())
                .build();

        return mapToDto(repository.save(feriado));
    }

    public List<FeriadoResponseDto> listar() {
        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public FeriadoResponseDto actualizar(Long id, FeriadoRequestDto request) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setDescripcion(request.getDescripcion());
                    entity.setFecha(request.getFecha());
                    return mapToDto(repository.save(entity));
                })
                .orElseThrow(() -> new RuntimeException("Feriado no encontrado"));
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
   
    private FeriadoResponseDto mapToDto(FeriadoEntity entity) {
        return FeriadoResponseDto.builder()
                .id(entity.getId())
                .descripcion(entity.getDescripcion())
                .fecha(entity.getFecha())
                .build();
    }
}

