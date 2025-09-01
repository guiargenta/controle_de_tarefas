package com.gargenta.controleTarefas.dto.mapper;

import com.gargenta.controleTarefas.dto.TarefaResponseDto;
import com.gargenta.controleTarefas.model.Tarefa;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TarefaMapper {

    public static Tarefa toTarefa(TarefaResponseDto tarefaResponseDto) {
        return new ModelMapper().map(tarefaResponseDto, Tarefa.class);
    }

    public static TarefaResponseDto toDto(Tarefa tarefa) {
        return new ModelMapper().map(tarefa, TarefaResponseDto.class);
    }

    public static List<TarefaResponseDto> toListDto(List<Tarefa> tarefas) {
//        List<TarefaResponseDto> dto = new ArrayList<>();
//        for (Tarefa tarefa : tarefas) {
//            dto.add(toDto(tarefa));
//        }
//        return dto;
        return tarefas.stream().map(t -> toDto(t)).collect(Collectors.toList());
    }
}
