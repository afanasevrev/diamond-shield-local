package ru.diamondshield.local.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.diamondshield.local.service.LocalEventQueueService;

@Component
public class EventPushScheduler {

    private final LocalEventQueueService eventQueueService;

    public EventPushScheduler(LocalEventQueueService eventQueueService) {
        this.eventQueueService = eventQueueService;
    }

    @Scheduled(
            fixedDelayString = "${diamondshield.sync.event-push-interval-ms}",
            initialDelay = 7000
    )
    public void pushEvents() {
        try {
            LocalEventQueueService.PushAllResult result = eventQueueService.pushAll();

            if (result.getTotal() > 0) {
                System.out.println(
                        "Events pushed successfully. accessEvents="
                                + result.getAccessEvents()
                                + ", alarmEvents="
                                + result.getAlarmEvents()
                                + ", deviceStatuses="
                                + result.getDeviceStatuses()
                );
            }
        } catch (Exception ex) {
            /*
             * Важно:
             * если центральный сервер недоступен, события не удаляем и не помечаем sent_to_central=true.
             */
            System.out.println("Event push failed: " + ex.getMessage());
        }
    }
}