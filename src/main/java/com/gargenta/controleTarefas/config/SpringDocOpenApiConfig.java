package com.gargenta.controleTarefas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("REST API - Controle de Tarefas")
                        .description("API para controle de tarefas dos usuários")
                        .version("1.0")
                        .contact(new Contact().name("Guilherme Argenta").email("gargenta.dev@gmail.com"))
        );
    }
}
