package com.gargenta.controleTarefas.service;

import com.gargenta.controleTarefas.model.Usuario;
import com.gargenta.controleTarefas.repository.UsuarioRepository;
import exception.EntityNotFoundException;
import exception.PasswordInvalidException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Getter
@Setter
@ToString
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.saveAndFlush(usuario);
    }

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaNovaSenha) {

        if (!novaSenha.equals(confirmaNovaSenha)) {
            throw new PasswordInvalidException("Senhas não conferem.");
        }

        Usuario usuario = buscarPorId(id);

        if (!senhaAtual.equals(usuario.getPassword())) {
            throw new PasswordInvalidException("Senha atual não confere.");
        }

        usuario.setPassword(senhaAtual);
        return usuario;
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario com Id: %d não encontrado.", id))
        );
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Username: %d não localizado.", username))
        );
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Usuário com Id: '%d' não encontrado.", id));
        }
        usuarioRepository.deleteById(id);
    }
}
