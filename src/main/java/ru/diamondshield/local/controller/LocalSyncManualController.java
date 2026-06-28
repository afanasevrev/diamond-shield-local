package ru.diamondshield.local.controller;

import org.springframework.web.bind.annotation.*;
import ru.diamondshield.local.service.CentralSyncService;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/local/sync")
public class LocalSyncManualController {

    private final CentralSyncService centralSyncService;

    public LocalSyncManualController(CentralSyncService centralSyncService) {
        this.centralSyncService = centralSyncService;
    }

    @PostMapping("/heartbeat")
    public Map<String, Object> heartbeat() {
        centralSyncService.heartbeat();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "ok");
        response.put("operation", "heartbeat");
        response.put("time", LocalDateTime.now());

        return response;
    }

    @PostMapping("/pull-config")
    public Map<String, Object> pullConfig() {
        centralSyncService.pullConfig();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "ok");
        response.put("operation", "pull-config");
        response.put("time", LocalDateTime.now());

        return response;
    }
}