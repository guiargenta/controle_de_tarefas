package com.gargenta.controleTarefas;

import com.gargenta.controleTarefas.dto.CreateUsuarioDto;
import com.gargenta.controleTarefas.dto.UsuarioResponseDto;
import com.gargenta.controleTarefas.dto.UsuarioSenhaDto;
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

    @Test
    public void criarUsuario_comEmaiLInvalido_retornarErrorMessageComStatus422() {
        ErrorMessage responseBody = webTestClient
                .post()
                .uri("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateUsuarioDto("fulano@gmail", "123456", "fulano"))
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
                .bodyValue(new CreateUsuarioDto("fulano@gmail.", "123456", "fulano"))
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
                .bodyValue(new CreateUsuarioDto("fulano", "123456", "fulano"))
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
                .bodyValue(new CreateUsuarioDto("    ", "123456", "fulano"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void criarUsuario_comEmailOuUsernameRepetidos_retornarErrorMessagemComStatus409() {
        ErrorMessage responseBody = webTestClient
                .post()
                .uri("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateUsuarioDto("teste1@gmail.com", "123456", "fulano"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);

        responseBody = webTestClient
                .post()
                .uri("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateUsuarioDto("fulano@gmail.com", "123456", "teste1"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void buscarUsuario_comIdInexistente_retornarErrorMessagemComStatus404() {
        ErrorMessage responseBody = webTestClient
                .get()
                .uri("/api/usuarios/34")
                .headers(JwtAuthentication.getHeaderAuthentication(webTestClient, "teste1", "111111"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void buscarUsuario_ClienteBuscandoOutroClientePorId_retornarErrorMessagemComStatus403() {
        ErrorMessage responseBody = webTestClient
                .get()
                .uri("/api/usuarios/102")
                .headers(JwtAuthentication.getHeaderAuthentication(webTestClient, "teste2", "222222"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void mudarSenha_comDadosValidos_RetornarStatus204() {
        webTestClient
                .put()
                .uri("/api/usuarios/100")
                .headers(JwtAuthentication.getHeaderAuthentication(webTestClient, "teste1", "111111"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("111111", "123456", "123456"))
                .exchange()
                .expectStatus().isNoContent();

        webTestClient
                .put()
                .uri("/api/usuarios/101")
                .headers(JwtAuthentication.getHeaderAuthentication(webTestClient, "teste2", "222222"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("222222", "123456", "123456"))
                .exchange()
                .expectStatus().isNoContent();

    }

    @Test
    public void mudarSenha_comUsuariosDiferentes_RetornarStatus403() {
        ErrorMessage responseBody = webTestClient
                .put()
                .uri("/api/usuarios/101")
                .headers(JwtAuthentication.getHeaderAuthentication(webTestClient, "teste1", "111111"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("111111", "111111", "111111"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

        responseBody = webTestClient
                .put()
                .uri("/api/usuarios/100")
                .headers(JwtAuthentication.getHeaderAuthentication(webTestClient, "teste2", "222222"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("222222", "222222", "222222"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void mudarSenha_comDadosInvalidos_RetornarStatus422() {
        ErrorMessage responseBody = webTestClient
                .put()
                .uri("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("", "", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = webTestClient
                .put()
                .uri("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("111111", "123456", "123"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = webTestClient
                .put()
                .uri("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("11111", "123456", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }


}
