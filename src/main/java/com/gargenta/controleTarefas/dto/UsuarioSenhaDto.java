package com.gargenta.controleTarefas.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioSenhaDto {

    String senhaAtual;
    String novaSenha;
    String confirmaSenha;
}
