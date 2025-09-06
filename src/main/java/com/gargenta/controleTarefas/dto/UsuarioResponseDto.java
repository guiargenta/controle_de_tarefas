package com.gargenta.controleTarefas.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioResponseDto {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    private String role;

}
