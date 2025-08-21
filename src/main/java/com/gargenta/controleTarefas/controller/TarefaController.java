package com.gargenta.controleTarefas.controller;

import com.gargenta.controleTarefas.model.Tarefa;
import com.gargenta.controleTarefas.service.TarefaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping()
    public ResponseEntity<Void> salvarTarefa(@RequestBody Tarefa tarefa) {
        tarefaService.salvarTarefa(tarefa);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> buscarTarefaPorData(@RequestParam LocalDate data) {
        List<Tarefa> tarefas = tarefaService.buscarTarefaPorData(data);
        return ResponseEntity.status(HttpStatus.OK).body(tarefas);
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> buscarTarefaEntreDatas(@RequestParam LocalDate dataInicio, LocalDate dataFim) {
        List<Tarefa> tarefas = tarefaService.buscarTarefaEntreDatas(dataInicio, dataFim);
        return ResponseEntity.status(HttpStatus.OK).body(tarefas);
    }

}
