package com.litolaser.calculoHoras.infraestructure.persistence.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.litolaser.calculoHoras.infraestructure.persistence.entity.FeriadoEntity;

public interface FeriadoRepository extends JpaRepository<FeriadoEntity, Long> {

    boolean existsByFecha(LocalDate fecha);

    Optional<FeriadoEntity> findByFecha(LocalDate fecha);
}
