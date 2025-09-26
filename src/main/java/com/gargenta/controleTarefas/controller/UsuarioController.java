package com.gargenta.controleTarefas.controller;

import com.gargenta.controleTarefas.dto.CreateUsuarioDto;
import com.gargenta.controleTarefas.dto.UsuarioResponseDto;
import com.gargenta.controleTarefas.dto.UsuarioSenhaDto;
import com.gargenta.controleTarefas.dto.mapper.UsuarioMapper;
import com.gargenta.controleTarefas.model.Usuario;
import com.gargenta.controleTarefas.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> createUser(@Valid @RequestBody CreateUsuarioDto dto) {
        Usuario usuarioSalvo = usuarioService.salvar(UsuarioMapper.toUsuario(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toUsuarioDto(usuarioSalvo));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDto dto) {
        Usuario usuario = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getUserById(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toUsuarioDto(usuario));
    }

    @GetMapping("/user")
    public ResponseEntity<UsuarioResponseDto> getByUsername(@RequestParam String username) {
        Usuario usuario = usuarioService.buscarPorUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toUsuarioDto(usuario));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAllUsers() {
        List<Usuario> usuarios = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(usuarios));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
