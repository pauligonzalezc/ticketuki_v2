package com.ticketuki.pagoservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${ms.venta.url:http://localhost:8006}")
    private String ventaUrl;

    @Value("${ms.usuario.url:http://localhost:8001}")
    private String usuarioUrl;

    @Bean("ventaWebClient")
    public WebClient ventaWebClient() {
        return WebClient.builder()
                .baseUrl(ventaUrl)
                .build();
    }

    @Bean("usuarioWebClient")
    public WebClient usuarioWebClient() {
        return WebClient.builder()
                .baseUrl(usuarioUrl)
                .build();
    }
}
