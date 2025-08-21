package com.gargenta.controleTarefas.service;

import com.gargenta.controleTarefas.model.Tarefa;
import com.gargenta.controleTarefas.repository.TarefaRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    @Transactional
    public void salvarTarefa(Tarefa tarefa) {
        tarefaRepository.saveAndFlush(tarefa);
    }

    @Transactional(readOnly = true)
    public List<Tarefa> buscarTarefaPorData(LocalDate data) {
        return tarefaRepository.findByData(data);
    }

    @Transactional(readOnly = true)
    public List<Tarefa> buscarTarefaEntreDatas(LocalDate dataInicio, LocalDate dataFim) {
        return tarefaRepository.findTarefaByDataCumprimentoBetween(dataInicio, dataFim);
    }
}
