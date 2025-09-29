package com.example.restfulapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://dog.ceo/api")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("User-Agent", "Spring Boot Dog API Client")
                .build();
    }
}