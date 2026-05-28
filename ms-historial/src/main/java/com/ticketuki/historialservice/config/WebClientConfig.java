package com.ticketuki.historialservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${ms.usuario.url:http://localhost:8001}")
    private String usuarioUrl;

    @Bean("usuarioWebClient")
    public WebClient usuarioWebClient() {
        return WebClient.builder()
                .baseUrl(usuarioUrl)
                .build();
    }
}
