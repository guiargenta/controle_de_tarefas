package com.gargenta.controleTarefas.service;

import com.gargenta.controleTarefas.model.Tarefa;
import com.gargenta.controleTarefas.repository.TarefaRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Getter
@Setter
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public void salvarTarefa(Tarefa tarefa) {
        tarefaRepository.saveAndFlush(tarefa);
    }
}
