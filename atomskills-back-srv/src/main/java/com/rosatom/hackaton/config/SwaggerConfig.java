package com.rosatom.hackaton.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {

    /*
         Configuration https://springdoc.org/index.html#migrating-from-springfox
   */
    @Bean
    @ConfigurationProperties(prefix = "openapi")
    public OpenAPI openAPI() {
        List<Server> servers = new ArrayList<>();
        servers.add(new Server().url("https://rosatom2023-api.kovalev.team").description("Prod Server"));
        servers.add(new Server().url("http://localhost:8900").description("Local Server"));
        return new OpenAPI()
                .info(
                        new Info()
                                .title("API сервиса")
                                .version("v1")
                )
                .servers(servers)
                .components(new Components()
                        .addSecuritySchemes("JWT",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .name("Authorization")
                                        .in(SecurityScheme.In.HEADER))
                );
    }

}
