package com.gargenta.controleTarefas.controller;

import com.gargenta.controleTarefas.dto.UsuarioLoginDto;
import com.gargenta.controleTarefas.exception.exceptionHandler.ErrorMessage;
import com.gargenta.controleTarefas.jwt.JwtToken;
import com.gargenta.controleTarefas.jwt.JwtUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Autenticação",
        description = "Contém a operação necessária para realizar login e autenticar um usuário."
)
@Slf4j
@RestController
@RequestMapping("/api")
public class AutenticacaoController {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;

    public AutenticacaoController(JwtUserDetailsService jwtUserDetailsService, AuthenticationManager authenticationManager) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Operation(summary = "Realizar o login de um usuário", description = "Requisição exige 'usernme' e 'senha' para autenticar",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso.",
                            content = @Content(
                                    mediaType = "application/json", schema = @Schema(implementation = UsuarioLoginDto.class))),
                    @ApiResponse(responseCode = "401", description = "Credenciais inválidas, autenticação falhou.",
                            content = @Content(
                                    mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)
                            ))
            })
    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDto dto, HttpServletRequest request) {
        log.info("\nIniciando autenticacao.. \nAutenticando com o login {}", dto.getUsername());

        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());

            authenticationManager.authenticate(authenticationToken);
            JwtToken token = jwtUserDetailsService.getTokenAuthenticated(dto.getUsername());
            return ResponseEntity.ok(token);

        } catch (AuthenticationException ex) {
            log.warn("Bad Credentials from username: {}", dto.getUsername());
        }

        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais Inválidas"));

    }
}
