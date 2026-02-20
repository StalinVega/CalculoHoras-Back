package com.litolaser.calculoHoras.application.Services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.litolaser.calculoHoras.application.Dto.ResultadoCalculo;
import com.litolaser.calculoHoras.infraestructure.persistence.repository.FeriadoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MotorCalculoHoras {

    private final FeriadoRepository feriadoRepository;

    public ResultadoCalculo calcular(LocalDateTime inicio, LocalDateTime fin) {

        BigDecimal normales = BigDecimal.ZERO;
        BigDecimal nocturnas = BigDecimal.ZERO;
        BigDecimal extraordinarias = BigDecimal.ZERO;
        BigDecimal extras8h = BigDecimal.ZERO;

        Duration duracion = Duration.between(inicio, fin);
        BigDecimal horasTotales = BigDecimal.valueOf(duracion.getSeconds())
        .divide(BigDecimal.valueOf(3600), 4, RoundingMode.HALF_UP);

        LocalDate fecha = inicio.toLocalDate();
        DayOfWeek dia = fecha.getDayOfWeek();
        boolean esFeriado = feriadoRepository.existsByFecha(fecha);
        boolean esFinDeSemana =
                dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY;

        if (esFeriado || esFinDeSemana) {
            extraordinarias = horasTotales;
        } else {
            BigDecimal jornada = BigDecimal.valueOf(8);

            normales = horasTotales.min(jornada);

            if (horasTotales.compareTo(jornada) > 0) {
                extras8h = horasTotales.subtract(jornada);
            }

            // nocturnas 19:00 - 08:00
            LocalTime horaInicio = inicio.toLocalTime();
            LocalTime horaFin = fin.toLocalTime();

            if (horaInicio.isAfter(LocalTime.of(19, 0)) ||
                horaFin.isBefore(LocalTime.of(8, 0))) {

                nocturnas = horasTotales.min(BigDecimal.valueOf(2)); // base prueba
            }
        }

        BigDecimal totalAjustado =
                normales
                        .add(nocturnas.multiply(BigDecimal.valueOf(1.25)))
                        .add(extraordinarias.multiply(BigDecimal.valueOf(2)))
                        .add(extras8h.multiply(BigDecimal.valueOf(1.5)))
                        .setScale(2, RoundingMode.HALF_UP);

        return new ResultadoCalculo(
                normales,
                nocturnas,
                extraordinarias,
                extras8h,
                totalAjustado
        );
    }
}

