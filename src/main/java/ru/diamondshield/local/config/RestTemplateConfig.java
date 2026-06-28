package ru.diamondshield.local.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder,
                                     LocalServerProperties properties) {
        return builder
                .connectTimeout(Duration.ofMillis(properties.getCentral().getConnectTimeoutMs()))
                .readTimeout(Duration.ofMillis(properties.getCentral().getReadTimeoutMs()))
                .build();
    }
}