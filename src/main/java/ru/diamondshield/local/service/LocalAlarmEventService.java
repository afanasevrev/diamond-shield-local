package ru.diamondshield.local.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diamondshield.local.config.LocalServerProperties;
import ru.diamondshield.local.entity.LocalAlarmEvent;
import ru.diamondshield.local.entity.LocalConfigState;
import ru.diamondshield.local.repository.LocalAlarmEventRepository;
import ru.diamondshield.local.repository.LocalConfigStateRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LocalAlarmEventService {

    private final LocalAlarmEventRepository alarmEventRepository;
    private final LocalConfigStateRepository configStateRepository;
    private final LocalServerProperties properties;

    public LocalAlarmEventService(LocalAlarmEventRepository alarmEventRepository,
                                  LocalConfigStateRepository configStateRepository,
                                  LocalServerProperties properties) {
        this.alarmEventRepository = alarmEventRepository;
        this.configStateRepository = configStateRepository;
        this.properties = properties;
    }

    @Transactional
    public LocalAlarmEvent saveAlarm(UUID accessPointId,
                                     UUID readerId,
                                     UUID controllerId,
                                     String alarmType,
                                     String severity,
                                     String message) {
        UUID objectId = configStateRepository
                .findFirstByLocalServerId(properties.getLocalServer().getId())
                .map(LocalConfigState::getObjectId)
                .orElse(null);

        LocalAlarmEvent event = new LocalAlarmEvent();

        event.setLocalEventId(UUID.randomUUID().toString());
        event.setObjectId(objectId);
        event.setAccessPointId(accessPointId);
        event.setReaderId(readerId);
        event.setControllerId(controllerId);
        event.setAlarmType(alarmType);
        event.setSeverity(severity);
        event.setMessage(message);
        event.setOccurredAt(LocalDateTime.now());
        event.setSentToCentral(false);

        return alarmEventRepository.save(event);
    }
}