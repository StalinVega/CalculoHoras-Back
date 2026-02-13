package com.litolaser.calculoHoras.application.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.litolaser.calculoHoras.application.Dto.ResultadoCalculo;
import com.litolaser.calculoHoras.infraestructure.persistence.entity.AsistenciaEntity;
import com.litolaser.calculoHoras.infraestructure.persistence.entity.HorasCalculadasEntity;
import com.litolaser.calculoHoras.infraestructure.persistence.entity.UsuarioEntity;
import com.litolaser.calculoHoras.infraestructure.persistence.repository.AsistenciaRepository;
import com.litolaser.calculoHoras.infraestructure.persistence.repository.HorasCalculadasRepository;
import com.litolaser.calculoHoras.infraestructure.persistence.repository.UsuarioJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HorasService {

    private final UsuarioJpaRepository usuarioRepository;
    private final AsistenciaRepository asistenciaRepository;
    private final HorasCalculadasRepository horasRepository;
    private final MotorCalculoHoras motor;

    public void calcularMes(Long usuarioId, int anio, int mes) {

        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        LocalDate inicio = LocalDate.of(anio, mes, 1);
        LocalDate fin = inicio.withDayOfMonth(inicio.lengthOfMonth());

        List<AsistenciaEntity> asistencias =
                asistenciaRepository
                        .findByUsuarioAndFechaInicioBetweenAndEstado(
                                usuario, inicio, fin, "CERRADO");

        for (AsistenciaEntity a : asistencias) {

            LocalDateTime fechaInicio = LocalDateTime.of(
                    a.getFechaInicio(),
                    a.getHoraInicio());

            LocalDateTime fechaFin = LocalDateTime.of(
                    a.getFechaFin(),
                    a.getHoraFin());

            ResultadoCalculo resultado =
                    motor.calcular(fechaInicio, fechaFin);

            guardarOActualizar(usuario, a.getFechaInicio(), resultado);
        }
    }

    private void guardarOActualizar(
            UsuarioEntity usuario,
            LocalDate fecha,
            ResultadoCalculo r) {

        HorasCalculadasEntity entity =
                horasRepository.findByUsuarioAndFecha(usuario, fecha)
                        .orElse(HorasCalculadasEntity.builder()
                                .usuario(usuario)
                                .fecha(fecha)
                                .build());

        entity.setHorasNormales(r.getNormales());
        entity.setHoras25(r.getHoras25());
        entity.setHoras100(r.getHoras100());
        entity.setHorasExtras(r.getHorasExtras());
        entity.setTotalAjustado(r.getTotalAjustado());

        horasRepository.save(entity);
    }

    public List<HorasCalculadasEntity> informeSemanal(
            Long usuarioId,
            int anio,
            int mes) {

        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        LocalDate inicio = LocalDate.of(anio, mes, 1);
        LocalDate fin = inicio.withDayOfMonth(inicio.lengthOfMonth());

        return horasRepository.findByUsuarioAndFechaBetween(usuario, inicio, fin);
    }
}

