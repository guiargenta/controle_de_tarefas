package com.gargenta.controleTarefas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_tarefa", nullable = false, length = 50, unique = true)
    private String nomeTarefa;

    @Column(name = "data_cumprimento", nullable = false)
    private LocalDate dataCumprimento;

    @Column(name = "quantidade")
    private int quantidade;

    public Tarefa(String nomeTarefa, LocalDate dataCumprimento, Integer quantidade) {
        this.nomeTarefa = nomeTarefa;
        this.dataCumprimento = dataCumprimento;
        this.quantidade = quantidade;
    }
}
