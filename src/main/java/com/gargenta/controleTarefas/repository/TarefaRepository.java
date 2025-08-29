package com.gargenta.controleTarefas.repository;

import com.gargenta.controleTarefas.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    Optional<Tarefa> findByNomeTarefaAndDataCumprimento (String nomeTarefa, LocalDate dataCumprimento);

    List<Tarefa> findByDataCumprimento(LocalDate dataCumprimento);

    List<Tarefa> findTarefaByDataCumprimentoBetween(LocalDate dataInicio, LocalDate dataFim);

    @Query("SELECT SUM(t.quantidade) FROM Tarefa t WHERE t.dataCumprimento = :data")
    Integer sumQuantidadeByDataCumprimento(@Param("data") LocalDate dataCumprimento);
}
