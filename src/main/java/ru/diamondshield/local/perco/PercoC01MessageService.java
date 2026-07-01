package ru.diamondshield.local.perco;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.diamondshield.local.config.LocalServerProperties;
import ru.diamondshield.local.dto.localcheck.LocalAccessCheckRequest;
import ru.diamondshield.local.dto.localcheck.LocalAccessCheckResponse;
import ru.diamondshield.local.entity.LocalReader;
import ru.diamondshield.local.repository.LocalControllerDeviceRepository;
import ru.diamondshield.local.service.DeviceStatusService;
import ru.diamondshield.local.service.LocalAccessCheckService;
import ru.diamondshield.local.service.LocalAlarmEventService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PercoC01MessageService {

    private final ObjectMapper objectMapper;
    private final LocalServerProperties properties;
    private final PercoC01SessionRegistry sessionRegistry;
    private final PercoC01CommandFactory commandFactory;
    private final PercoC01AuthService authService;
    private final PercoC01ReaderResolver readerResolver;
    private final LocalAccessCheckService accessCheckService;
    private final LocalAlarmEventService alarmEventService;
    private final DeviceStatusService deviceStatusService;
    private final LocalControllerDeviceRepository controllerRepository;

    public PercoC01MessageService(ObjectMapper objectMapper,
                                  LocalServerProperties properties,
                                  PercoC01SessionRegistry sessionRegistry,
                                  PercoC01CommandFactory commandFactory,
                                  PercoC01AuthService authService,
                                  PercoC01ReaderResolver readerResolver,
                                  LocalAccessCheckService accessCheckService,
                                  LocalAlarmEventService alarmEventService,
                                  DeviceStatusService deviceStatusService,
                                  LocalControllerDeviceRepository controllerRepository) {
        this.objectMapper = objectMapper;
        this.properties = properties;
        this.sessionRegistry = sessionRegistry;
        this.commandFactory = commandFactory;
        this.authService = authService;
        this.readerResolver = readerResolver;
        this.accessCheckService = accessCheckService;
        this.alarmEventService = alarmEventService;
        this.deviceStatusService = deviceStatusService;
        this.controllerRepository = controllerRepository;
    }

    public void onConnected(WebSocketSession session) {
        UUID controllerId = resolveControllerIdFromQuery(session);

        if (controllerId != null && controllerRepository.existsById(controllerId)) {
            sessionRegistry.bindController(session.getId(), controllerId);
            deviceStatusService.saveStatus("controller", controllerId, "online", "PERCo C01 WebSocket connected");
        }
    }

    public void onDisconnected(WebSocketSession session) {
        UUID controllerId = sessionRegistry.getControllerId(session.getId());

        if (controllerId != null) {
            deviceStatusService.saveStatus("controller", controllerId, "offline", "PERCo C01 WebSocket disconnected");
        }
    }

    public void handleText(WebSocketSession session,
                           String payload) {
        if (!properties.getPerco().isEnabled()) {
            return;
        }

        try {
            JsonNode root = objectMapper.readTree(payload);

            if (root.has("event")) {
                handleEvent(session, root);
                return;
            }

            if (root.has("answer")) {
                handleAnswer(session, root);
                return;
            }

            if (root.has("result")) {
                handleResult(session, root);
                return;
            }

            System.out.println("PERCo unknown message: " + payload);
        } catch (Exception ex) {
            System.out.println("PERCo message processing failed: " + ex.getMessage());
        }
    }

    private void handleEvent(WebSocketSession session,
                             JsonNode root) throws Exception {
        String event = root.path("event").asText();

        switch (event) {
            case "need_auth" -> handleNeedAuth(session, root);
            case "card" -> handleCard(session, root);
            case "pass_personal" -> handlePassPersonal(session, root);
            case "pass_impersonal" -> handlePassImpersonal(session, root);
            case "refusal_personal" -> handleRefusalPersonal(session, root);
            case "refusal_impersonal" -> handleRefusalImpersonal(session, root);
            case "pass_ban_personal" -> handlePassBanPersonal(session, root);
            case "pass_ban_impersonal" -> handlePassBanImpersonal(session, root);
            case "break" -> handleBreak(session, root);
            case "exdev_long_open" -> handleExdevLongOpen(session, root);
            case "exdev_unlock" -> handleExdevUnlock(session, root);
            case "input" -> handleInput(session, root);
            case "output" -> handleOutput(session, root);
            default -> System.out.println("PERCo unsupported event: " + event);
        }
    }

    private void handleNeedAuth(WebSocketSession session,
                                JsonNode root) throws Exception {
        String salt = root.path("need_auth").path("salt").asText();

        String hash = authService.buildAuthHash(salt);

        send(session, commandFactory.auth(hash));
    }

    private void handleCard(WebSocketSession session,
                            JsonNode root) throws Exception {
        UUID controllerId = sessionRegistry.getControllerId(session.getId());

        JsonNode card = root.path("card");

        Integer number = card.path("number").asInt();
        Integer direction = card.path("direction").asInt();
        String cardId = card.path("id").asText();

        Optional<LocalReader> readerOptional = readerResolver.resolve(controllerId, number, direction);

        if (readerOptional.isEmpty()) {
            alarmEventService.saveAlarm(
                    null,
                    null,
                    controllerId,
                    "perco_reader_mapping_not_found",
                    "medium",
                    "PERCo reader mapping not found. number=" + number + ", direction=" + direction
            );

            send(session, commandFactory.banAccess(number, direction));
            return;
        }

        LocalReader reader = readerOptional.get();

        if (reader.getAccessPointId() == null) {
            alarmEventService.saveAlarm(
                    null,
                    reader.getId(),
                    controllerId,
                    "perco_reader_access_point_not_configured",
                    "medium",
                    "PERCo reader has no accessPointId. readerId=" + reader.getId()
            );

            send(session, commandFactory.banAccess(number, direction));
            return;
        }

        LocalAccessCheckRequest request = new LocalAccessCheckRequest();

        request.setIdentifierType(properties.getPerco().getIdentifierType());
        request.setIdentifierValue(cardId);
        request.setAccessPointId(reader.getAccessPointId());
        request.setReaderId(reader.getId());
        request.setControllerId(controllerId);
        request.setDirection(directionToText(direction));
        request.setEventTime(LocalDateTime.now());

        LocalAccessCheckResponse response = accessCheckService.check(request);

        if (Boolean.TRUE.equals(response.getAllowed())) {
            send(session, commandFactory.openExdev(number, direction));
        } else {
            send(session, commandFactory.banAccess(number, direction));
        }
    }

    private void handlePassPersonal(WebSocketSession session,
                                    JsonNode root) {
        UUID controllerId = sessionRegistry.getControllerId(session.getId());

        JsonNode node = root.path("pass_personal");

        alarmEventService.saveAlarm(
                null,
                null,
                controllerId,
                "perco_pass_personal",
                "info",
                "Personal pass. number=" + node.path("number").asInt()
                        + ", direction=" + node.path("direction").asInt()
                        + ", id=" + node.path("id").asText()
        );
    }

    private void handlePassImpersonal(WebSocketSession session,
                                      JsonNode root) {
        UUID controllerId = sessionRegistry.getControllerId(session.getId());

        JsonNode node = root.path("pass_impersonal");

        alarmEventService.saveAlarm(
                null,
                null,
                controllerId,
                "perco_pass_impersonal",
                "info",
                "Impersonal pass. command_source=" + node.path("command_source").asText()
        );
    }

    private void handleRefusalPersonal(WebSocketSession session,
                                       JsonNode root) {
        UUID controllerId = sessionRegistry.getControllerId(session.getId());

        alarmEventService.saveAlarm(
                null,
                null,
                controllerId,
                "perco_refusal_personal",
                "info",
                root.toString()
        );
    }

    private void handleRefusalImpersonal(WebSocketSession session,
                                         JsonNode root) {
        UUID controllerId = sessionRegistry.getControllerId(session.getId());

        alarmEventService.saveAlarm(
                null,
                null,
                controllerId,
                "perco_refusal_impersonal",
                "info",
                root.toString()
        );
    }

    private void handlePassBanPersonal(WebSocketSession session,
                                       JsonNode root) {
        UUID controllerId = sessionRegistry.getControllerId(session.getId());

        alarmEventService.saveAlarm(
                null,
                null,
                controllerId,
                "perco_pass_ban_personal",
                "warning",
                root.toString()
        );
    }

    private void handlePassBanImpersonal(WebSocketSession session,
                                         JsonNode root) {
        UUID controllerId = sessionRegistry.getControllerId(session.getId());

        alarmEventService.saveAlarm(
                null,
                null,
                controllerId,
                "perco_pass_ban_impersonal",
                "warning",
                root.toString()
        );
    }

    private void handleBreak(WebSocketSession session,
                             JsonNode root) {
        UUID controllerId = sessionRegistry.getControllerId(session.getId());

        JsonNode node = root.path("break");

        alarmEventService.saveAlarm(
                null,
                null,
                controllerId,
                "perco_break",
                "high",
                "PERCo exdev break. number=" + node.path("number").asInt()
                        + ", direction=" + node.path("direction").asInt()
        );
    }

    private void handleExdevLongOpen(WebSocketSession session,
                                     JsonNode root) {
        UUID controllerId = sessionRegistry.getControllerId(session.getId());

        JsonNode node = root.path("exdev_long_open");

        alarmEventService.saveAlarm(
                null,
                null,
                controllerId,
                "perco_exdev_long_open",
                "high",
                "PERCo exdev long open. number=" + node.path("number").asInt()
                        + ", direction=" + node.path("direction").asInt()
        );
    }

    private void handleExdevUnlock(WebSocketSession session,
                                   JsonNode root) {
        UUID controllerId = sessionRegistry.getControllerId(session.getId());

        JsonNode node = root.path("exdev_unlock");

        String status = node.path("unlock").asBoolean() ? "unlocked" : "locked";

        if (controllerId != null) {
            deviceStatusService.saveStatus(
                    "controller",
                    controllerId,
                    status,
                    "PERCo exdev unlock event. number=" + node.path("number").asInt()
                            + ", direction=" + node.path("direction").asInt()
            );
        }
    }

    private void handleInput(WebSocketSession session,
                             JsonNode root) {
        UUID controllerId = sessionRegistry.getControllerId(session.getId());

        JsonNode node = root.path("input");

        String function = node.path("function").asText();
        boolean on = node.path("on").asBoolean();

        if ("fire alarm input".equalsIgnoreCase(function) && on) {
            alarmEventService.saveAlarm(
                    null,
                    null,
                    controllerId,
                    "perco_fire_alarm",
                    "critical",
                    root.toString()
            );
        } else {
            alarmEventService.saveAlarm(
                    null,
                    null,
                    controllerId,
                    "perco_input",
                    "info",
                    root.toString()
            );
        }
    }

    private void handleOutput(WebSocketSession session,
                              JsonNode root) {
        UUID controllerId = sessionRegistry.getControllerId(session.getId());

        alarmEventService.saveAlarm(
                null,
                null,
                controllerId,
                "perco_output",
                "info",
                root.toString()
        );
    }

    private void handleAnswer(WebSocketSession session,
                              JsonNode root) {
        System.out.println("PERCo answer: " + root);
    }

    private void handleResult(WebSocketSession session,
                              JsonNode root) {
        System.out.println("PERCo result: " + root);
    }

    private UUID resolveControllerIdFromQuery(WebSocketSession session) {
        if (session.getUri() == null || session.getUri().getQuery() == null) {
            return null;
        }

        String query = session.getUri().getQuery();

        String[] parts = query.split("&");

        for (String part : parts) {
            String[] keyValue = part.split("=");

            if (keyValue.length == 2 && "controllerId".equals(keyValue[0])) {
                try {
                    return UUID.fromString(keyValue[1]);
                } catch (Exception ignored) {
                    return null;
                }
            }
        }

        return null;
    }

    private String directionToText(Integer direction) {
        if (direction == null) {
            return null;
        }

        if (direction == 0) {
            return "in";
        }

        if (direction == 1) {
            return "out";
        }

        return direction.toString();
    }

    private void send(WebSocketSession session,
                      String text) throws Exception {
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(text));
        }
    }
}