package com.gargenta.controleTarefas.controller;

import com.gargenta.controleTarefas.dto.CreateUsuarioDto;
import com.gargenta.controleTarefas.dto.UsuarioResponseDto;
import com.gargenta.controleTarefas.dto.UsuarioSenhaDto;
import com.gargenta.controleTarefas.dto.mapper.UsuarioMapper;
import com.gargenta.controleTarefas.exception.exceptionHandler.ErrorMessage;
import com.gargenta.controleTarefas.model.Usuario;
import com.gargenta.controleTarefas.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuários", description = "Contém os recursos para criar, alterar e ler dados de usuários")
@Getter
@Setter
@ToString
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Criar novo usuário", description = "Recursos para criar um novo usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "422", description = "Não foi possível processar a requisição devido a dados inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "409", description = "Ocorreu um conflito na requisição",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> createUser(@Valid @RequestBody CreateUsuarioDto dto) {
        Usuario usuarioSalvo = usuarioService.salvar(UsuarioMapper.toUsuario(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toUsuarioDto(usuarioSalvo));
    }

    @Operation(summary = "Alterar senha", description = "Recurso para alterar a senha de um usuário",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Não autorizado devido à falha de autenticação.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDto dto) {
        Usuario usuario = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar usuário por ID", description = "Recurso para buscar um usuário pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário recuperado com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getUserById(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toUsuarioDto(usuario));
    }

    @Operation(summary = "Buscar usuário por 'username'", description = "Recurso para buscar um usuário pelo 'username'",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário recuperado com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @GetMapping("/user")
    public ResponseEntity<UsuarioResponseDto> getByUsername(@RequestParam String username) {
        Usuario usuario = usuarioService.buscarPorUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toUsuarioDto(usuario));
    }

    @Operation(summary = "Buscar todos usuários", description = "Recurso para buscar todos usuários",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuários retornados com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Não autorizado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponseDto>> getAllUsers() {
        List<Usuario> usuarios = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(usuarios));
    }

    @Operation(summary = "Deletar usuário por ID", description = "Recurso para deletar um usuário pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Não autorizado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
