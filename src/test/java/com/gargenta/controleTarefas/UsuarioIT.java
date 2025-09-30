package com.gargenta.controleTarefas;

import com.gargenta.controleTarefas.dto.CreateUsuarioDto;
import com.gargenta.controleTarefas.dto.UsuarioResponseDto;
import com.gargenta.controleTarefas.exception.exceptionHandler.ErrorMessage;
import org.junit.jupiter.api.Assertions;
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
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole().equalsIgnoreCase("ROLE_USER"));
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername().equalsIgnoreCase("fulano"));
        org.assertj.core.api.Assertions.assertThat(responseBody.getEmail().equalsIgnoreCase("fulano@gmail.com"));
    }

    @Test
    public void criarUsuario_comPasswordInvalido_retornarErrorMessageComStatus422() {
        ErrorMessage responseBody = webTestClient
                .post()
                .uri("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateUsuarioDto("fulano@gmail.com", "12345", "fulano"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = webTestClient
                .post()
                .uri("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateUsuarioDto("fulano@gmail.com", "", "fulano"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void criarUsuario_comUsernameInvalido_retornarErrorMessageComStatus422() {
        ErrorMessage responseBody = webTestClient
                .post()
                .uri("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateUsuarioDto("fulano@gmail.com", "123456", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = webTestClient
                .post()
                .uri("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateUsuarioDto("fulano@gmail.com", "123456", "     ")) // espaços em branco
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }
}
