package com.litolaser.calculoHoras.infraestructure.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.litolaser.calculoHoras.infraestructure.persistence.entity.AsistenciaEntity;
import com.litolaser.calculoHoras.infraestructure.persistence.entity.UsuarioEntity;

public interface AsistenciaRepository extends JpaRepository<AsistenciaEntity, Long> {

    Optional<AsistenciaEntity> findByUsuarioAndEstado(UsuarioEntity usuario, String estado);

    List<AsistenciaEntity> findByUsuarioAndFechaInicioBetweenAndEstado(
            UsuarioEntity usuario,
            LocalDate desde,
            LocalDate hasta,
            String estado);

    boolean existsByUsuarioIdAndHoraFinIsNull(Long usuarioId);

}
