package com.litolaser.calculoHoras.infraestructure.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.litolaser.calculoHoras.infraestructure.persistence.entity.HorasCalculadasEntity;
import com.litolaser.calculoHoras.infraestructure.persistence.entity.UsuarioEntity;

public interface HorasCalculadasRepository
                extends JpaRepository<HorasCalculadasEntity, Long> {

        Optional<HorasCalculadasEntity> findByUsuarioAndFecha(
                        UsuarioEntity usuario,
                        LocalDate fecha);

        List<HorasCalculadasEntity> findByUsuarioAndFechaBetween(
                        UsuarioEntity usuario,
                        LocalDate desde,
                        LocalDate hasta);

        @Query("""
                            SELECT h FROM HorasCalculadasEntity h
                            WHERE h.usuario.id = :usuarioId
                            AND YEAR(h.fecha) = :anio
                            AND MONTH(h.fecha) = :mes
                            ORDER BY h.fecha ASC
                        """)
        List<HorasCalculadasEntity> findByUsuarioAndMes(
                        Long usuarioId,
                        int anio,
                        int mes);
}
