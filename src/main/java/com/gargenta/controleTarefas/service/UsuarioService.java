package com.gargenta.controleTarefas.service;

import com.gargenta.controleTarefas.model.Usuario;
import com.gargenta.controleTarefas.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.saveAndFlush(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario com Id: %d não encontrado.", id))
        );
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findbyUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Username: %d não localizado.", username))
        );
    }

}
