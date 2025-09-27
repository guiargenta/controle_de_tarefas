package com.gargenta.controleTarefas.controller;

import com.gargenta.controleTarefas.dto.TarefaResponseDto;
import com.gargenta.controleTarefas.dto.mapper.TarefaMapper;
import com.gargenta.controleTarefas.exception.exceptionHandler.ErrorMessage;
import com.gargenta.controleTarefas.model.Tarefa;
import com.gargenta.controleTarefas.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Tarefas", description = "Recursos para criar, alterar, consultar e deletar tarefas.")
@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @Operation(summary = "Criar ou atualizar tarefa cumprida.", description = "Recurso para criar uma nova tarefa que foi cumprida ou alterar uma que já exista.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TarefaResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Conflito ao criar tarefa",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping()
    public ResponseEntity<TarefaResponseDto> criarOuAtualizarTarefaCumprida(@RequestBody TarefaResponseDto dto) {
        Tarefa tarefa = tarefaService.salvarTarefaOuAtualizarTarefa(TarefaMapper.toTarefa(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(TarefaMapper.toDto(tarefa));
    }

    @PostMapping("/incrementar")
    public ResponseEntity<Tarefa> incrementarQuantidade(
            @RequestParam String nomeTarefa,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataTarefa,
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
    public ResponseEntity<List<TarefaResponseDto>> buscarTodas() {
        List<Tarefa> tarefas = tarefaService.buscarTodas();
        List<TarefaResponseDto> dtos = TarefaMapper.toListDto(tarefas);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/total-hoje")
    public ResponseEntity<Integer> quantidadeTotalHoje() {
        return ResponseEntity.ok(tarefaService.quantidadeTotalPorData(LocalDate.now()));
    }
}
