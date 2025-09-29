package com.gargenta.controleTarefas;

import com.gargenta.controleTarefas.dto.CreateUsuarioDto;
import com.gargenta.controleTarefas.dto.UsuarioResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql.usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql.usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void criarUsuario_comUsernameEmaileSenhaValidos_retornarUsuarioCriadoComStatus201() {
        UsuarioResponseDto responseBody = webTestClient
                .post()
                .uri("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateUsuarioDto("fulano@gmail.com", "123456", "fulano"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername().equalsIgnoreCase("fulano"));
        org.assertj.core.api.Assertions.assertThat(responseBody.getEmail().equalsIgnoreCase("fulano@gmail.com"));
    }
}
