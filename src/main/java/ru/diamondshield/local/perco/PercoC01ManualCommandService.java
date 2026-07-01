package ru.diamondshield.local.perco;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

@Service
public class PercoC01ManualCommandService {

    private final PercoC01SessionRegistry sessionRegistry;
    private final PercoC01CommandFactory commandFactory;

    public PercoC01ManualCommandService(PercoC01SessionRegistry sessionRegistry,
                                        PercoC01CommandFactory commandFactory) {
        this.sessionRegistry = sessionRegistry;
        this.commandFactory = commandFactory;
    }

    public void sendGetState(UUID controllerId) {
        send(controllerId, commandFactory.getState());
    }

    public void sendGetNet(UUID controllerId) {
        send(controllerId, commandFactory.getNet());
    }

    public void sendGetReader(UUID controllerId,
                              Integer number) {
        send(controllerId, commandFactory.getReader(number));
    }

    public void sendGetExdev(UUID controllerId,
                             Integer number) {
        send(controllerId, commandFactory.getExdev(number));
    }

    public void sendOpen(UUID controllerId,
                         Integer number,
                         Integer direction) {
        send(controllerId, commandFactory.openExdev(number, direction));
    }

    public void sendClose(UUID controllerId,
                          Integer number,
                          Integer direction) {
        send(controllerId, commandFactory.closeExdev(number, direction));
    }

    public void sendBan(UUID controllerId,
                        Integer number,
                        Integer direction) {
        send(controllerId, commandFactory.banAccess(number, direction));
    }

    public void sendAccessMode(UUID controllerId,
                               Integer number,
                               Integer direction,
                               String accessMode) {
        send(controllerId, commandFactory.setAccessMode(number, direction, accessMode));
    }

    private void send(UUID controllerId,
                      String json) {
        WebSocketSession session = sessionRegistry
                .findSessionByControllerId(controllerId)
                .orElseThrow(() -> new IllegalStateException("PERCo controller is not connected: " + controllerId));

        try {
            session.sendMessage(new TextMessage(json));
        } catch (Exception ex) {
            throw new IllegalStateException("Cannot send PERCo command", ex);
        }
    }
}