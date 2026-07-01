package ru.diamondshield.local.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.diamondshield.local.perco.PercoC01WebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final LocalServerProperties properties;
    private final PercoC01WebSocketHandler percoC01WebSocketHandler;

    public WebSocketConfig(LocalServerProperties properties,
                           PercoC01WebSocketHandler percoC01WebSocketHandler) {
        this.properties = properties;
        this.percoC01WebSocketHandler = percoC01WebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        String path = properties.getPerco().getWebsocketPath();

        if (path == null || path.isBlank()) {
            path = "/ws/perco";
        }

        registry.addHandler(percoC01WebSocketHandler, path)
                .setAllowedOrigins("*");
    }
}