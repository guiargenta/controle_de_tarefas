package com.gargenta.controleTarefas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
public class TarefaResponseDto {

    @NotBlank
    String nomeTarefa;

    @PastOrPresent
    LocalDate dataCumprimento;

    @Size(min = 0, max = 50)
    int quantidade;
}
