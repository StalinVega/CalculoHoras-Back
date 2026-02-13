package com.litolaser.calculoHoras.infraestructure.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.litolaser.calculoHoras.infraestructure.persistence.entity.HorasCalculadasEntity;
import com.litolaser.calculoHoras.infraestructure.persistence.entity.UsuarioEntity;

public interface HorasCalculadasRepository
        extends JpaRepository<HorasCalculadasEntity, Long> {

    Optional<HorasCalculadasEntity> findByUsuarioAndFecha(
            UsuarioEntity usuario,
            LocalDate fecha
    );

    List<HorasCalculadasEntity> findByUsuarioAndFechaBetween(
            UsuarioEntity usuario,
            LocalDate desde,
            LocalDate hasta
    );
}

