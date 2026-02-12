package com.litolaser.calculoHoras.web.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.litolaser.calculoHoras.application.Services.auth.AuthService;
import com.litolaser.calculoHoras.domain.auth.dto.LoginRequest;
import com.litolaser.calculoHoras.domain.auth.dto.LoginResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }
}