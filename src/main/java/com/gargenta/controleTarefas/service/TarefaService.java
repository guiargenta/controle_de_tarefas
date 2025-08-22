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
import java.util.Optional;

@AllArgsConstructor
@Getter
@Setter
@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    @Transactional
    public void salvarTarefaOuAtualizarTarefa(Tarefa tarefa) {
        // Verifica se já existe uma tarefa com o mesmo nome na mesma data
        Optional<Tarefa> tarefaExistente = tarefaRepository.findByNomeDaTarefaEData(
                tarefa.getNomeTarefa(),
                tarefa.getDataCumprimento()
        );
        // Se existir, incrementa a quantidade
        if (tarefaExistente.isPresent()) {
            Tarefa atualizado = tarefaExistente.get();
            atualizado.setQuantidade(atualizado.getQuantidade() + tarefa.getQuantidade());
            tarefaRepository.saveAndFlush(tarefa);
        } else {
            tarefaRepository.save(tarefa);
        }
    }

    @Transactional
    public Tarefa incrementarQuantidade(String nomeDaTarefa, LocalDate dataCumprimento, Integer quantidadeAdicional) {
        Optional<Tarefa> tarefaExistente = tarefaRepository.findByNomeDaTarefaEData(
                nomeDaTarefa, dataCumprimento
        );

        if (tarefaExistente.isPresent()) {
            Tarefa tarefa = tarefaExistente.get();
            tarefa.setQuantidade(tarefa.getQuantidade() + quantidadeAdicional);
            return tarefaRepository.save(tarefa);
        } else {
            // Se não existir, cria nova tarefa com a quantidade especificada
            Tarefa novaTarefa = new Tarefa(nomeDaTarefa, dataCumprimento, quantidadeAdicional);
            return tarefaRepository.save(novaTarefa);
        }
    }

    @Transactional(readOnly = true)
    public List<Tarefa> buscarTarefaPorData(LocalDate data) {
        return tarefaRepository.findByDataCumprimento(data);
    }

    @Transactional(readOnly = true)
    public List<Tarefa> buscarTarefaEntreDatas(LocalDate dataInicio, LocalDate dataFim) {
        return tarefaRepository.findTarefaByDataCumprimentoBetween(dataInicio, dataFim);
    }

    public Optional<Tarefa> findByNomeDaTarefaEData(String NomeDaTarefa, LocalDate dataCumprimento) {

        return tarefaRepository.findByNomeDaTarefaEData(NomeDaTarefa, dataCumprimento);
    }
}
