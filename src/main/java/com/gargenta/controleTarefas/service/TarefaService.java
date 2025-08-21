package com.gargenta.controleTarefas.service;

import com.gargenta.controleTarefas.model.Tarefa;
import com.gargenta.controleTarefas.repository.TarefaRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Getter
@Setter
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    @Transactional
    public void salvarTarefa(Tarefa tarefa) {
        tarefaRepository.saveAndFlush(tarefa);
    }

    @Transactional(readOnly = true)
    public List<Tarefa> buscarTarefaPorData(Date data) {
        return tarefaRepository.findByData(data);
    }
}
