package com.gargenta.controleTarefas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioSenhaDto {

    @NotBlank
    @Size(min = 6, max = 30)
    String senhaAtual;

    @NotBlank
    @Size(min = 6, max = 30)
    String novaSenha;

    @NotBlank
    @Size(min = 6, max = 30)
    String confirmaSenha;
}
