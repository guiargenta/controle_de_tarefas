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
    public Tarefa salvarTarefaOuAtualizarTarefa(Tarefa tarefa) {

        // Se a data não for informada, define como data atual
        if (tarefa.getDataCumprimento() == null) {
            tarefa.setDataCumprimento(LocalDate.now());
        }

        // Verifica se já existe uma tarefa com o mesmo nome na mesma data
        Optional<Tarefa> tarefaExistente = tarefaRepository.findByNomeTarefaAndDataCumprimento(
                tarefa.getNomeTarefa(),
                tarefa.getDataCumprimento()
        );
        // Se existir, incrementa a quantidade
        if (tarefaExistente.isPresent()) {
            Tarefa atualizado = tarefaExistente.get();
            atualizado.setQuantidade(atualizado.getQuantidade() + tarefa.getQuantidade());
            return tarefaRepository.saveAndFlush(tarefa);
        } else {
            return tarefaRepository.save(tarefa);
        }
    }

    @Transactional
    public Tarefa incrementarQuantidade(String nomeDaTarefa, LocalDate dataCumprimento, Integer quantidadeAdicional) {

        LocalDate data = (dataCumprimento != null) ? dataCumprimento : LocalDate.now();

        Optional<Tarefa> tarefaExistente = tarefaRepository.findByNomeTarefaAndDataCumprimento(
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
        LocalDate dataBusca = (data != null) ? data : LocalDate.now();
        return tarefaRepository.findByDataCumprimento(dataBusca);
    }

    @Transactional(readOnly = true)
    public List<Tarefa> buscarTarefaEntreDatas(LocalDate dataInicio, LocalDate dataFim) {
        return tarefaRepository.findTarefaByDataCumprimentoBetween(dataInicio, dataFim);
    }

    @Transactional(readOnly = true)
    public Optional<Tarefa> findByNomeDaTarefaEData(String NomeDaTarefa, LocalDate dataCumprimento) {
        return tarefaRepository.findByNomeTarefaAndDataCumprimento(NomeDaTarefa, dataCumprimento);
    }

    @Transactional
    public Integer quantidadeTotalPorData(LocalDate data) {
        LocalDate dataBusca = (data!= null) ? data : LocalDate.now();
        return tarefaRepository.sumQuantidadeByDataCumprimento(dataBusca);
    }

    @Transactional
    public List<Tarefa> buscarTodas() {
        return tarefaRepository.findAll();
    }
}
