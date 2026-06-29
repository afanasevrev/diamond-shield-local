package ru.diamondshield.local.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diamondshield.local.config.LocalServerProperties;
import ru.diamondshield.local.entity.LocalConfigState;
import ru.diamondshield.local.entity.LocalDeviceStatusEvent;
import ru.diamondshield.local.repository.LocalConfigStateRepository;
import ru.diamondshield.local.repository.LocalDeviceStatusEventRepository;

import java.util.UUID;

@Service
public class DeviceStatusService {

    private final LocalDeviceStatusEventRepository statusEventRepository;
    private final LocalConfigStateRepository configStateRepository;
    private final LocalServerProperties properties;

    public DeviceStatusService(LocalDeviceStatusEventRepository statusEventRepository,
                               LocalConfigStateRepository configStateRepository,
                               LocalServerProperties properties) {
        this.statusEventRepository = statusEventRepository;
        this.configStateRepository = configStateRepository;
        this.properties = properties;
    }

    @Transactional
    public LocalDeviceStatusEvent saveStatus(String deviceType,
                                             UUID deviceId,
                                             String status,
                                             String message) {
        UUID objectId = configStateRepository
                .findFirstByLocalServerId(properties.getLocalServer().getId())
                .map(LocalConfigState::getObjectId)
                .orElse(null);

        LocalDeviceStatusEvent event = new LocalDeviceStatusEvent();

        event.setObjectId(objectId);
        event.setDeviceType(deviceType);
        event.setDeviceId(deviceId);
        event.setStatus(status);
        event.setMessage(message);
        event.setSentToCentral(false);

        return statusEventRepository.save(event);
    }
}