package com.gargenta.controleTarefas.controller;

import com.gargenta.controleTarefas.model.Tarefa;
import com.gargenta.controleTarefas.service.TarefaService;
import org.springframework.format.annotation.DateTimeFormat;
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
    public ResponseEntity<Void> criarOuAtualizarTarefaCumprida(@RequestBody Tarefa tarefa) {
        tarefaService.salvarTarefaOuAtualizarTarefa(tarefa);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/incrementar")
    public ResponseEntity<Tarefa> incrementarQuantidade(
            @RequestParam String nomeTarefa,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataTarefa,
            @RequestParam(defaultValue = "1") Integer quantidade
    ) {
        return ResponseEntity.ok(tarefaService.incrementarQuantidade(nomeTarefa, dataTarefa, quantidade));
    }

    @GetMapping("/data/{data}")
    public ResponseEntity<List<Tarefa>> buscarTarefaCumpridaPorData(@RequestParam LocalDate data) {
        return ResponseEntity.ok(tarefaService.buscarTarefaPorData(data));
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Tarefa>> buscarTarefaCumpridaEntreDatas(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataInicio,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataFim) {
        return ResponseEntity.ok(tarefaService.buscarTarefaEntreDatas(dataInicio, dataFim));
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> buscarTodas() {
        return ResponseEntity.ok(tarefaService.buscarTodas());
    }
}
