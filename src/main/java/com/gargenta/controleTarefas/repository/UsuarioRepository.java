package com.gargenta.controleTarefas.repository;

import com.gargenta.controleTarefas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findbyUsername(String username);
}
