package com.litolaser.calculoHoras.shared.security;

import jakarta.servlet.http.*;
import tools.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.litolaser.calculoHoras.shared.response.ApiResponse;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        ApiResponse<Object> apiResponse =
                ApiResponse.error("No tienes permisos para acceder a este recurso");

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}