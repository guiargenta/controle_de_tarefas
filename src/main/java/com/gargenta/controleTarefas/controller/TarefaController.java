package com.gargenta.controleTarefas.controller;

import com.gargenta.controleTarefas.model.Tarefa;
import com.gargenta.controleTarefas.service.TarefaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping()
    public ResponseEntity<Void> salvarOuAtualizarTarefaCumprida(@RequestBody Tarefa tarefa) {
        tarefaService.salvarTarefaOuAtualizarTarefa(tarefa);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/data/{data}")
    public ResponseEntity<List<Tarefa>> buscarTarefaCumpridaPorData(@RequestParam LocalDate data) {
        List<Tarefa> tarefas = tarefaService.buscarTarefaPorData(data);
        return ResponseEntity.status(HttpStatus.OK).body(tarefas);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Tarefa>> buscarTarefaCumpridaEntreDatas(@RequestParam LocalDate dataInicio, LocalDate dataFim) {
        List<Tarefa> tarefas = tarefaService.buscarTarefaEntreDatas(dataInicio, dataFim);
        return ResponseEntity.status(HttpStatus.OK).body(tarefas);
    }

}
