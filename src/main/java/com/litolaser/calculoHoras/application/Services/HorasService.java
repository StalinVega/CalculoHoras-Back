package com.litolaser.calculoHoras.application.Services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.litolaser.calculoHoras.application.Dto.ReporteMensualDTO;
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

                List<AsistenciaEntity> asistencias = asistenciaRepository
                                .findByUsuarioAndFechaInicioBetweenAndEstado(
                                                usuario, inicio, fin, "CERRADO");

                Map<LocalDate, List<AsistenciaEntity>> agrupado = asistencias.stream()
                                .collect(Collectors.groupingBy(AsistenciaEntity::getFechaInicio));

                for (Map.Entry<LocalDate, List<AsistenciaEntity>> entry : agrupado.entrySet()) {

                        LocalDate fecha = entry.getKey();
                        List<AsistenciaEntity> registrosDelDia = entry.getValue();

                        BigDecimal totalHorasDia = BigDecimal.ZERO;

                        for (AsistenciaEntity a : registrosDelDia) {

                                LocalDateTime fechaInicio = LocalDateTime.of(
                                                a.getFechaInicio(),
                                                a.getHoraInicio());

                                LocalDateTime fechaFin = LocalDateTime.of(
                                                a.getFechaFin(),
                                                a.getHoraFin());

                                Duration duracion = Duration.between(fechaInicio, fechaFin);

                                BigDecimal horas = BigDecimal.valueOf(duracion.toMinutes())
                                                .divide(BigDecimal.valueOf(60), 4, RoundingMode.HALF_UP);

                                totalHorasDia = totalHorasDia.add(horas);
                        }

                        ResultadoCalculo resultado = motor.calcular(
                                        fecha.atStartOfDay(),
                                        fecha.atStartOfDay().plusHours(totalHorasDia.longValue()));

                        guardarOActualizar(usuario, fecha, resultado);
                }
        }

        private void guardarOActualizar(
                        UsuarioEntity usuario,
                        LocalDate fecha,
                        ResultadoCalculo r) {

                HorasCalculadasEntity entity = horasRepository.findByUsuarioAndFecha(usuario, fecha)
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

        public List<ReporteMensualDTO> reporteMensual(Long usuarioId) {

                UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                LocalDate hoy = LocalDate.now();
                LocalDate inicio = hoy.withDayOfMonth(1);
                LocalDate fin = hoy.withDayOfMonth(hoy.lengthOfMonth());

                List<AsistenciaEntity> asistencias = asistenciaRepository.findByUsuarioAndFechaInicioBetweenAndEstado(
                                usuario, inicio, fin, "CERRADO");

                return asistencias.stream()
                                .map(a -> {

                                        LocalDateTime fechaInicio = LocalDateTime.of(a.getFechaInicio(),
                                                        a.getHoraInicio());

                                        LocalDateTime fechaFin = LocalDateTime.of(a.getFechaFin(), a.getHoraFin());

                                        // 🔥 CALCULAR DIRECTAMENTE
                                        ResultadoCalculo r = motor.calcular(fechaInicio, fechaFin);

                                        return ReporteMensualDTO.builder()
                                                        .fecha(a.getFechaInicio())
                                                        .horaIngreso(a.getHoraInicio())
                                                        .horaSalida(a.getHoraFin())
                                                        .horasNormales(r.getNormales())
                                                        .horasNocturnas(r.getHoras25())
                                                        .horasExtras(r.getHorasExtras())
                                                        .horas100(r.getHoras100())
                                                        .totalAjustado(r.getTotalAjustado())
                                                        .build();
                                })
                                .toList();
        }
}
