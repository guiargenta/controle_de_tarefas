package com.gargenta.controleTarefas.dto.mapper;

import com.gargenta.controleTarefas.controller.UsuarioController;
import com.gargenta.controleTarefas.dto.CreateUsuarioDto;
import com.gargenta.controleTarefas.dto.UsuarioResponseDto;
import com.gargenta.controleTarefas.model.Usuario;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {

    public static Usuario toUsuario(CreateUsuarioDto dto) {
        return new ModelMapper().map(dto, Usuario.class);
    }

    public static UsuarioResponseDto toUsuarioDto(Usuario usuario) {
        return new ModelMapper().map(usuario, UsuarioResponseDto.class);
    }

    public static List<UsuarioResponseDto> toListDto(List<Usuario> usuarios) {
        return usuarios.stream().map(user -> toUsuarioDto(user)).collect(Collectors.toList());
    }
}
