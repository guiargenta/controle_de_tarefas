package com.gargenta.controleTarefas.repository;

import com.gargenta.controleTarefas.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByData(LocalDate data);

    List<Tarefa> findTarefaByDataCumprimentoBetween(LocalDate dataInicio, LocalDate dataFim);
}
