package com.litolaser.calculoHoras.application.Dto;



import lombok.Data;

@Data
public class UsuarioRequestDto {

    private String nombre;
    private String apellido;
    private String cedula;
    private String email;
    private String password;
    private String rol;
}

