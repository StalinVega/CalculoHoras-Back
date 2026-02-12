package com.litolaser.calculoHoras.application.Services.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.litolaser.calculoHoras.domain.auth.dto.LoginRequest;
import com.litolaser.calculoHoras.domain.auth.dto.LoginResponse;
import com.litolaser.calculoHoras.infraestructure.persistence.entity.UsuarioEntity;
import com.litolaser.calculoHoras.infraestructure.persistence.repository.UsuarioJpaRepository;
import com.litolaser.calculoHoras.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioJpaRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        UsuarioEntity usuario = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!usuario.getActivo()) {
            throw new RuntimeException("Usuario desactivado");
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = jwtService.generateToken(
                usuario.getEmail(),
                usuario.getRol()
        );

        return new LoginResponse(token);
    }
}