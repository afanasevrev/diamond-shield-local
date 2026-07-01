package ru.diamondshield.local.perco;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class PercoC01WebSocketHandler extends TextWebSocketHandler {

    private final PercoC01SessionRegistry sessionRegistry;
    private final PercoC01MessageService messageService;

    public PercoC01WebSocketHandler(PercoC01SessionRegistry sessionRegistry,
                                    PercoC01MessageService messageService) {
        this.sessionRegistry = sessionRegistry;
        this.messageService = messageService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessionRegistry.register(session);
        messageService.onConnected(session);

        System.out.println("PERCo C01 connected. sessionId="
                + session.getId()
                + ", remote="
                + session.getRemoteAddress());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session,
                                     TextMessage message) {
        System.out.println("PERCo C01 -> local: " + message.getPayload());
        messageService.handleText(session, message.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session,
                                     Throwable exception) {
        System.out.println("PERCo C01 transport error. sessionId="
                + session.getId()
                + ", error="
                + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus status) {
        messageService.onDisconnected(session);
        sessionRegistry.unregister(session.getId());

        System.out.println("PERCo C01 disconnected. sessionId="
                + session.getId()
                + ", status="
                + status);
    }
}