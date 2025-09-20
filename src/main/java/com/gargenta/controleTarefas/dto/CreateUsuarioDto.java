package com.gargenta.controleTarefas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateUsuarioDto {

    @NotBlank
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Formato de e-mail inválido")
    private String email;

    @NotBlank(message = "A senha deve ser entre 6 e 30 caractéres.")
    @Size(min = 6, max = 30)
    private String password;

    @NotBlank
    private String username;
}
