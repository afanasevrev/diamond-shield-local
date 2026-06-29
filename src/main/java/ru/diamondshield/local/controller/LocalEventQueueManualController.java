package ru.diamondshield.local.controller;

import org.springframework.web.bind.annotation.*;
import ru.diamondshield.local.service.LocalEventQueueService;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/local/sync")
public class LocalEventQueueManualController {

    private final LocalEventQueueService eventQueueService;

    public LocalEventQueueManualController(LocalEventQueueService eventQueueService) {
        this.eventQueueService = eventQueueService;
    }

    @PostMapping("/push-events")
    public Map<String, Object> pushEvents() {
        LocalEventQueueService.PushAllResult result = eventQueueService.pushAll();

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("status", "ok");
        response.put("operation", "push-events");
        response.put("time", LocalDateTime.now());

        response.put("accessEvents", result.getAccessEvents());
        response.put("alarmEvents", result.getAlarmEvents());
        response.put("deviceStatuses", result.getDeviceStatuses());
        response.put("total", result.getTotal());

        return response;
    }

    @PostMapping("/push-access-events")
    public Map<String, Object> pushAccessEvents() {
        int count = eventQueueService.pushAccessEvents();

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("status", "ok");
        response.put("operation", "push-access-events");
        response.put("time", LocalDateTime.now());
        response.put("count", count);

        return response;
    }

    @PostMapping("/push-alarm-events")
    public Map<String, Object> pushAlarmEvents() {
        int count = eventQueueService.pushAlarmEvents();

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("status", "ok");
        response.put("operation", "push-alarm-events");
        response.put("time", LocalDateTime.now());
        response.put("count", count);

        return response;
    }

    @PostMapping("/push-device-statuses")
    public Map<String, Object> pushDeviceStatuses() {
        int count = eventQueueService.pushDeviceStatuses();

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("status", "ok");
        response.put("operation", "push-device-statuses");
        response.put("time", LocalDateTime.now());
        response.put("count", count);

        return response;
    }
}