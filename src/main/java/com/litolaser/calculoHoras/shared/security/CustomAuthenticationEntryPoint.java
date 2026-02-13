package com.litolaser.calculoHoras.shared.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.litolaser.calculoHoras.shared.response.ApiResponse;
import jakarta.servlet.http.*;


import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");

        ApiResponse<Object> apiResponse =
                ApiResponse.error("No autenticado. Debes iniciar sesión.");

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
