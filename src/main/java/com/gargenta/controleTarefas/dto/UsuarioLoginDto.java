package com.gargenta.controleTarefas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioLoginDto {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{3,10}$", message = "Formato de username inválido")
    private String username;

    @NotBlank(message = "A senha deve ser entre 6 e 30 caractéres.")
    @Size(min = 6, max = 30)
    private String password;
}
