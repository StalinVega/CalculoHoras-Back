package com.litolaser.calculoHoras.infraestructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.litolaser.calculoHoras.infraestructure.persistence.entity.UsuarioEntity;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCedula(String cedula);
}
