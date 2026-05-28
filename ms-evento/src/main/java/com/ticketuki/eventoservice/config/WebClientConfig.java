package com.ticketuki.eventoservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${ms.estado.url:http://localhost:8004}")
    private String estadoUrl;

    @Value("${ms.recinto.url:http://localhost:8008}")
    private String recintoUrl;

    @Bean("estadoWebClient")
    public WebClient estadoWebClient() {
        return WebClient.builder()
                .baseUrl(estadoUrl)
                .build();
    }

    @Bean("recintoWebClient")
    public WebClient recintoWebClient() {
        return WebClient.builder()
                .baseUrl(recintoUrl)
                .build();
    }
}
