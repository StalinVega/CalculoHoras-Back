package com.litolaser.calculoHoras.application.Dto; 

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponseDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String cedula;
    private String email;
    private String rol;
    private Boolean activo;
}
