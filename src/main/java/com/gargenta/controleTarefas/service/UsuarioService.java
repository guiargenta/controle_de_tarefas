package com.gargenta.controleTarefas.service;

import com.gargenta.controleTarefas.exception.*;
import com.gargenta.controleTarefas.model.Usuario;
import com.gargenta.controleTarefas.repository.UsuarioRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Getter
@Setter
@ToString
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return usuarioRepository.saveAndFlush(usuario);
        } catch (DataIntegrityViolationException exception) {
            if (exception.getCause() instanceof UsernameUniqueViolationException) {
                throw new UsernameUniqueViolationException(String.format("Usuário: {%s} já cadastrado.", usuario.getUsername()));
            } else if (exception.getCause() instanceof EmailUniqueViolationException) {
                throw new EmailUniqueViolationException(String.format("E-mail: {%s} já cadastrado.", usuario.getEmail()));
            } else {
                throw exception;
            }
        }
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

        usuario.setPassword(novaSenha);
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
                () -> new UsernameNotFoundException(String.format("Username: '%s' não localizado.", username))
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

    @Transactional(readOnly = true)
    public Usuario.Role buscarRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username);
    }
}
