package com.mirusoft.api.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    private String devUrl = "http://localhost:8080";

    @Bean
    public OpenAPI openAPI() {

        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server url in development environment");


        Info info = new Info();
        info.title("mirusoft project api");
        info.description("프로젝트를 위한 Sample Spring boot project");
        info.version("1.0.0");

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
