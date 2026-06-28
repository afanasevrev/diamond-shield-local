package ru.diamondshield.local.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.diamondshield.local.service.CentralSyncService;

@Component
public class ConfigPullScheduler {

    private final CentralSyncService centralSyncService;

    public ConfigPullScheduler(CentralSyncService centralSyncService) {
        this.centralSyncService = centralSyncService;
    }

    @Scheduled(
            fixedDelayString = "${diamondshield.sync.config-pull-interval-ms}",
            initialDelay = 3000
    )
    public void pullConfig() {
        try {
            centralSyncService.pullConfig();
            System.out.println("Config pulled successfully");
        } catch (Exception ex) {
            System.out.println("Config pull failed: " + ex.getMessage());
        }
    }
}