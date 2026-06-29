package ru.diamondshield.local.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diamondshield.local.config.LocalServerProperties;
import ru.diamondshield.local.dto.central.*;
import ru.diamondshield.local.entity.*;
import ru.diamondshield.local.repository.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LocalEventQueueService {

    private final LocalAccessEventRepository accessEventRepository;
    private final LocalAlarmEventRepository alarmEventRepository;
    private final LocalDeviceStatusEventRepository deviceStatusEventRepository;
    private final LocalConfigStateRepository configStateRepository;
    private final CentralClientService centralClientService;
    private final LocalServerProperties properties;

    public LocalEventQueueService(LocalAccessEventRepository accessEventRepository,
                                  LocalAlarmEventRepository alarmEventRepository,
                                  LocalDeviceStatusEventRepository deviceStatusEventRepository,
                                  LocalConfigStateRepository configStateRepository,
                                  CentralClientService centralClientService,
                                  LocalServerProperties properties) {
        this.accessEventRepository = accessEventRepository;
        this.alarmEventRepository = alarmEventRepository;
        this.deviceStatusEventRepository = deviceStatusEventRepository;
        this.configStateRepository = configStateRepository;
        this.centralClientService = centralClientService;
        this.properties = properties;
    }

    @Transactional
    public int pushAccessEvents() {
        List<LocalAccessEvent> events =
                accessEventRepository.findTop100BySentToCentralFalseOrderByCreatedAtAsc();

        if (events.isEmpty()) {
            return 0;
        }

        List<CentralAccessEventPushRequest> payload = events.stream()
                .map(this::toCentralAccessEvent)
                .toList();

        CentralBatchResponse response = centralClientService.pushAccessEvents(payload);

        if (response == null) {
            return 0;
        }

        /*
         * Для MVP считаем, что если центральный сервер ответил без исключения,
         * пакет обработан. В production лучше учитывать accepted/skipped/error поэлементно.
         */
        LocalDateTime now = LocalDateTime.now();

        for (LocalAccessEvent event : events) {
            event.setSentToCentral(true);
            event.setSentAt(now);
            accessEventRepository.save(event);
        }

        updateLastSuccessfulPushAt(now);

        return events.size();
    }

    @Transactional
    public int pushAlarmEvents() {
        List<LocalAlarmEvent> events =
                alarmEventRepository.findTop100BySentToCentralFalseOrderByCreatedAtAsc();

        if (events.isEmpty()) {
            return 0;
        }

        List<CentralAlarmEventPushRequest> payload = events.stream()
                .map(this::toCentralAlarmEvent)
                .toList();

        CentralBatchResponse response = centralClientService.pushAlarmEvents(payload);

        if (response == null) {
            return 0;
        }

        LocalDateTime now = LocalDateTime.now();

        for (LocalAlarmEvent event : events) {
            event.setSentToCentral(true);
            event.setSentAt(now);
            alarmEventRepository.save(event);
        }

        updateLastSuccessfulPushAt(now);

        return events.size();
    }

    @Transactional
    public int pushDeviceStatuses() {
        List<LocalDeviceStatusEvent> events =
                deviceStatusEventRepository.findTop100BySentToCentralFalseOrderByCreatedAtAsc();

        if (events.isEmpty()) {
            return 0;
        }

        List<CentralDeviceStatusPushRequest> payload = events.stream()
                .map(this::toCentralDeviceStatus)
                .toList();

        CentralBatchResponse response = centralClientService.pushDeviceStatuses(payload);

        if (response == null) {
            return 0;
        }

        LocalDateTime now = LocalDateTime.now();

        for (LocalDeviceStatusEvent event : events) {
            event.setSentToCentral(true);
            event.setSentAt(now);
            deviceStatusEventRepository.save(event);
        }

        updateLastSuccessfulPushAt(now);

        return events.size();
    }

    @Transactional
    public PushAllResult pushAll() {
        int accessEvents = pushAccessEvents();
        int alarmEvents = pushAlarmEvents();
        int deviceStatuses = pushDeviceStatuses();

        return new PushAllResult(accessEvents, alarmEvents, deviceStatuses);
    }

    private CentralAccessEventPushRequest toCentralAccessEvent(LocalAccessEvent event) {
        CentralAccessEventPushRequest request = new CentralAccessEventPushRequest();

        request.setLocalEventId(event.getLocalEventId());

        request.setObjectId(event.getObjectId());
        request.setAccessPointId(event.getAccessPointId());
        request.setReaderId(event.getReaderId());
        request.setControllerId(event.getControllerId());

        request.setPersonId(event.getPersonId());
        request.setIdentifierId(event.getIdentifierId());

        request.setEventTime(event.getEventTime());
        request.setDirection(event.getDirection());

        request.setAccessResult(event.getAccessResult());
        request.setReason(event.getReason());

        request.setIdentifierType(event.getIdentifierType());

        /*
         * На локальном сервере мы не храним открытое значение карты.
         * Поэтому отправляем null.
         */
        request.setIdentifierValue(null);

        return request;
    }

    private CentralAlarmEventPushRequest toCentralAlarmEvent(LocalAlarmEvent event) {
        CentralAlarmEventPushRequest request = new CentralAlarmEventPushRequest();

        request.setLocalEventId(event.getLocalEventId());

        request.setObjectId(event.getObjectId());
        request.setAccessPointId(event.getAccessPointId());
        request.setReaderId(event.getReaderId());
        request.setControllerId(event.getControllerId());

        request.setAlarmType(event.getAlarmType());
        request.setSeverity(event.getSeverity());
        request.setMessage(event.getMessage());
        request.setOccurredAt(event.getOccurredAt());

        return request;
    }

    private CentralDeviceStatusPushRequest toCentralDeviceStatus(LocalDeviceStatusEvent event) {
        CentralDeviceStatusPushRequest request = new CentralDeviceStatusPushRequest();

        request.setObjectId(event.getObjectId());

        request.setDeviceType(event.getDeviceType());
        request.setDeviceId(event.getDeviceId());

        request.setStatus(event.getStatus());
        request.setMessage(event.getMessage());

        request.setOccurredAt(event.getCreatedAt());

        return request;
    }

    private void updateLastSuccessfulPushAt(LocalDateTime dateTime) {
        LocalConfigState state = configStateRepository
                .findFirstByLocalServerId(properties.getLocalServer().getId())
                .orElseGet(LocalConfigState::new);

        state.setLocalServerId(properties.getLocalServer().getId());
        state.setLastSuccessfulPushAt(dateTime);

        configStateRepository.save(state);
    }

    public static class PushAllResult {

        private final int accessEvents;
        private final int alarmEvents;
        private final int deviceStatuses;

        public PushAllResult(int accessEvents,
                             int alarmEvents,
                             int deviceStatuses) {
            this.accessEvents = accessEvents;
            this.alarmEvents = alarmEvents;
            this.deviceStatuses = deviceStatuses;
        }

        public int getAccessEvents() {
            return accessEvents;
        }

        public int getAlarmEvents() {
            return alarmEvents;
        }

        public int getDeviceStatuses() {
            return deviceStatuses;
        }

        public int getTotal() {
            return accessEvents + alarmEvents + deviceStatuses;
        }
    }
}