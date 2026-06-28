package ru.diamondshield.local;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.diamondshield.local.config.LocalServerProperties;

@SpringBootApplication
@EnableConfigurationProperties(LocalServerProperties.class)
public class LocalServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalServerApplication.class, args);
    }
}