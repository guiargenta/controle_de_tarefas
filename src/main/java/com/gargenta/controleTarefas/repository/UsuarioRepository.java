package com.gargenta.controleTarefas.repository;

import com.gargenta.controleTarefas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    Usuario deleteUsuarioById(Long id);

    @Query("select u.role from Usuario u where u.username like :username ")
    Usuario.Role findRoleByUsername(String username);
}
