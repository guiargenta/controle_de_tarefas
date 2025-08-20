package com.gargenta.controleTarefas.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Getter
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String descricao;

    @Column(nullable = false)
    private Date data;

    @Column
    private Double quantidade;
}
