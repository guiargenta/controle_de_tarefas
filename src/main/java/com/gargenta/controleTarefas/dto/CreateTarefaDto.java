package com.gargenta.controleTarefas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateTarefaDto {

    @NotBlank
    @Size(max = 50)
    String nomeTarefa;

    @PastOrPresent
    LocalDate dataCumprimento;

    @Size(min = 0)
    int quantidade;
}
