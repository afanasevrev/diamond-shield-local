package ru.diamondshield.local.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.diamondshield.local.service.CentralSyncService;

@Component
public class HeartbeatScheduler {

    private final CentralSyncService centralSyncService;

    public HeartbeatScheduler(CentralSyncService centralSyncService) {
        this.centralSyncService = centralSyncService;
    }

    @Scheduled(
            fixedDelayString = "${diamondshield.sync.heartbeat-interval-ms}",
            initialDelay = 5000
    )
    public void heartbeat() {
        try {
            centralSyncService.heartbeat();
            System.out.println("Heartbeat sent successfully");
        } catch (Exception ex) {
            System.out.println("Heartbeat failed: " + ex.getMessage());
        }
    }
}