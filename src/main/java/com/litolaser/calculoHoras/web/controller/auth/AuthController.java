package com.litolaser.calculoHoras.web.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.litolaser.calculoHoras.application.Services.auth.AuthService;
import com.litolaser.calculoHoras.domain.auth.dto.LoginRequest;
import com.litolaser.calculoHoras.domain.auth.dto.LoginResponse;
import com.litolaser.calculoHoras.shared.response.ApiResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {

    LoginResponse response = service.login(request);

    return ResponseEntity.ok(
            ApiResponse.success(response, "Login exitoso")
    );
}
}