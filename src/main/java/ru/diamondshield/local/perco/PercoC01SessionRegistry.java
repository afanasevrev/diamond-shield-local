package ru.diamondshield.local.perco;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PercoC01SessionRegistry {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final Map<String, UUID> sessionControllerIds = new ConcurrentHashMap<>();

    public void register(WebSocketSession session) {
        sessions.put(session.getId(), session);
    }

    public void unregister(String sessionId) {
        sessions.remove(sessionId);
        sessionControllerIds.remove(sessionId);
    }

    public void bindController(String sessionId, UUID controllerId) {
        if (controllerId != null) {
            sessionControllerIds.put(sessionId, controllerId);
        }
    }

    public UUID getControllerId(String sessionId) {
        return sessionControllerIds.get(sessionId);
    }

    public Optional<WebSocketSession> findSessionByControllerId(UUID controllerId) {
        for (Map.Entry<String, UUID> entry : sessionControllerIds.entrySet()) {
            if (controllerId.equals(entry.getValue())) {
                WebSocketSession session = sessions.get(entry.getKey());

                if (session != null && session.isOpen()) {
                    return Optional.of(session);
                }
            }
        }

        return Optional.empty();
    }

    public int countOpenSessions() {
        int count = 0;

        for (WebSocketSession session : sessions.values()) {
            if (session.isOpen()) {
                count++;
            }
        }

        return count;
    }

    public List<Map<String, Object>> listSessions() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (WebSocketSession session : sessions.values()) {
            Map<String, Object> item = new LinkedHashMap<>();

            item.put("sessionId", session.getId());
            item.put("open", session.isOpen());
            item.put("remoteAddress", session.getRemoteAddress() == null ? null : session.getRemoteAddress().toString());
            item.put("controllerId", sessionControllerIds.get(session.getId()));

            result.add(item);
        }

        return result;
    }
}