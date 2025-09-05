package com.gargenta.controleTarefas.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioResponseDto {

    @NotBlank
    private String usuario;

    @NotBlank
    @Size(min = 6, max = 100)
    private String senha;

    @NotBlank
    @Email
    private String email;

}
