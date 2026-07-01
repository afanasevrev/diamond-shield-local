package ru.diamondshield.local.controller;

import org.springframework.web.bind.annotation.*;
import ru.diamondshield.local.perco.PercoC01ManualCommandService;
import ru.diamondshield.local.perco.PercoC01SessionRegistry;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/local/perco")
public class PercoC01TestController {

    private final PercoC01SessionRegistry sessionRegistry;
    private final PercoC01ManualCommandService manualCommandService;

    public PercoC01TestController(PercoC01SessionRegistry sessionRegistry,
                                  PercoC01ManualCommandService manualCommandService) {
        this.sessionRegistry = sessionRegistry;
        this.manualCommandService = manualCommandService;
    }

    @GetMapping("/sessions")
    public Map<String, Object> sessions() {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put("time", LocalDateTime.now());
        response.put("openSessions", sessionRegistry.countOpenSessions());
        response.put("sessions", sessionRegistry.listSessions());

        return response;
    }

    @PostMapping("/{controllerId}/state")
    public Map<String, Object> getState(@PathVariable UUID controllerId) {
        manualCommandService.sendGetState(controllerId);
        return ok("get-state", controllerId);
    }

    @PostMapping("/{controllerId}/net")
    public Map<String, Object> getNet(@PathVariable UUID controllerId) {
        manualCommandService.sendGetNet(controllerId);
        return ok("get-net", controllerId);
    }

    @PostMapping("/{controllerId}/reader")
    public Map<String, Object> getReader(@PathVariable UUID controllerId,
                                         @RequestParam(required = false) Integer number) {
        manualCommandService.sendGetReader(controllerId, number);
        return ok("get-reader", controllerId);
    }

    @PostMapping("/{controllerId}/exdev")
    public Map<String, Object> getExdev(@PathVariable UUID controllerId,
                                        @RequestParam(required = false) Integer number) {
        manualCommandService.sendGetExdev(controllerId, number);
        return ok("get-exdev", controllerId);
    }

    @PostMapping("/{controllerId}/open")
    public Map<String, Object> open(@PathVariable UUID controllerId,
                                    @RequestParam Integer number,
                                    @RequestParam Integer direction) {
        manualCommandService.sendOpen(controllerId, number, direction);
        return ok("open", controllerId);
    }

    @PostMapping("/{controllerId}/close")
    public Map<String, Object> close(@PathVariable UUID controllerId,
                                     @RequestParam Integer number,
                                     @RequestParam Integer direction) {
        manualCommandService.sendClose(controllerId, number, direction);
        return ok("close", controllerId);
    }

    @PostMapping("/{controllerId}/ban")
    public Map<String, Object> ban(@PathVariable UUID controllerId,
                                   @RequestParam Integer number,
                                   @RequestParam Integer direction) {
        manualCommandService.sendBan(controllerId, number, direction);
        return ok("ban", controllerId);
    }

    @PostMapping("/{controllerId}/access-mode")
    public Map<String, Object> accessMode(@PathVariable UUID controllerId,
                                          @RequestParam Integer number,
                                          @RequestParam Integer direction,
                                          @RequestParam String accessMode) {
        manualCommandService.sendAccessMode(controllerId, number, direction, accessMode);
        return ok("access-mode-" + accessMode, controllerId);
    }

    private Map<String, Object> ok(String operation,
                                   UUID controllerId) {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put("status", "ok");
        response.put("operation", operation);
        response.put("controllerId", controllerId);
        response.put("time", LocalDateTime.now());

        return response;
    }
}