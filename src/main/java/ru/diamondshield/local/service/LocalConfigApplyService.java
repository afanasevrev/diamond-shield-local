package ru.diamondshield.local.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diamondshield.local.config.LocalServerProperties;
import ru.diamondshield.local.dto.central.*;
import ru.diamondshield.local.entity.*;
import ru.diamondshield.local.repository.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LocalConfigApplyService {

    private final LocalConfigStateRepository configStateRepository;
    private final LocalControllerDeviceRepository controllerRepository;
    private final LocalReaderRepository readerRepository;
    private final LocalAccessPointRepository accessPointRepository;
    private final LocalPersonRepository personRepository;
    private final LocalAccessIdentifierRepository identifierRepository;
    private final LocalAccessRuleRepository ruleRepository;
    private final LocalScheduleRepository scheduleRepository;
    private final LocalScheduleIntervalRepository intervalRepository;
    private final LocalServerProperties properties;

    public LocalConfigApplyService(LocalConfigStateRepository configStateRepository,
                                   LocalControllerDeviceRepository controllerRepository,
                                   LocalReaderRepository readerRepository,
                                   LocalAccessPointRepository accessPointRepository,
                                   LocalPersonRepository personRepository,
                                   LocalAccessIdentifierRepository identifierRepository,
                                   LocalAccessRuleRepository ruleRepository,
                                   LocalScheduleRepository scheduleRepository,
                                   LocalScheduleIntervalRepository intervalRepository,
                                   LocalServerProperties properties) {
        this.configStateRepository = configStateRepository;
        this.controllerRepository = controllerRepository;
        this.readerRepository = readerRepository;
        this.accessPointRepository = accessPointRepository;
        this.personRepository = personRepository;
        this.identifierRepository = identifierRepository;
        this.ruleRepository = ruleRepository;
        this.scheduleRepository = scheduleRepository;
        this.intervalRepository = intervalRepository;
        this.properties = properties;
    }

    @Transactional
    public void apply(CentralConfigResponse config) {
        if (config == null) {
            return;
        }

        validateConfig(config);

        applyControllers(config);
        applyAccessPoints(config);
        applyReaders(config);
        applyPersons(config);
        applyIdentifiers(config);
        applySchedules(config);
        applyScheduleIntervals(config);
        applyAccessRules(config);
        updateConfigState(config);
    }

    private void validateConfig(CentralConfigResponse config) {
        UUID expectedLocalServerId = properties.getLocalServer().getId();

        if (config.getLocalServerId() != null && !expectedLocalServerId.equals(config.getLocalServerId())) {
            throw new IllegalStateException(
                    "Received config for another local server. Expected: "
                            + expectedLocalServerId
                            + ", actual: "
                            + config.getLocalServerId()
            );
        }
    }

    private void applyControllers(CentralConfigResponse config) {
        if (config.getControllers() == null) {
            return;
        }

        for (CentralConfigControllerDto dto : config.getControllers()) {
            if (dto.getId() == null) {
                continue;
            }

            LocalControllerDevice entity = controllerRepository.findById(dto.getId())
                    .orElseGet(LocalControllerDevice::new);

            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setModel(dto.getModel());
            entity.setSerialNumber(dto.getSerialNumber());
            entity.setIpAddress(dto.getIpAddress());
            entity.setPort(dto.getPort());
            entity.setStatus(dto.getStatus());

            controllerRepository.save(entity);
        }
    }

    private void applyReaders(CentralConfigResponse config) {
        if (config.getReaders() == null) {
            return;
        }

        for (CentralConfigReaderDto dto : config.getReaders()) {
            if (dto.getId() == null) {
                continue;
            }

            LocalReader entity = readerRepository.findById(dto.getId())
                    .orElseGet(LocalReader::new);

            entity.setId(dto.getId());
            entity.setControllerId(dto.getControllerId());
            entity.setAccessPointId(dto.getAccessPointId());
            entity.setName(dto.getName());
            entity.setReaderType(dto.getReaderType());
            entity.setDirection(dto.getDirection());
            entity.setStatus(dto.getStatus());

            readerRepository.save(entity);
        }
    }

    private void applyAccessPoints(CentralConfigResponse config) {
        if (config.getAccessPoints() == null) {
            return;
        }

        for (CentralConfigAccessPointDto dto : config.getAccessPoints()) {
            if (dto.getId() == null) {
                continue;
            }

            LocalAccessPoint entity = accessPointRepository.findById(dto.getId())
                    .orElseGet(LocalAccessPoint::new);

            entity.setId(dto.getId());
            entity.setControllerId(dto.getControllerId());
            entity.setZoneFromId(dto.getZoneFromId());
            entity.setZoneToId(dto.getZoneToId());
            entity.setName(dto.getName());
            entity.setAccessPointType(dto.getAccessPointType());
            entity.setActive(dto.getActive());

            accessPointRepository.save(entity);
        }
    }

    private void applyPersons(CentralConfigResponse config) {
        if (config.getPersons() == null) {
            return;
        }

        for (CentralConfigPersonDto dto : config.getPersons()) {
            if (dto.getId() == null) {
                continue;
            }

            LocalPerson entity = personRepository.findById(dto.getId())
                    .orElseGet(LocalPerson::new);

            entity.setId(dto.getId());
            entity.setPersonType(dto.getPersonType());
            entity.setPersonnelNumber(dto.getPersonnelNumber());
            entity.setLastName(dto.getLastName());
            entity.setFirstName(dto.getFirstName());
            entity.setMiddleName(dto.getMiddleName());
            entity.setActive(dto.getActive());

            personRepository.save(entity);
        }
    }

    private void applyIdentifiers(CentralConfigResponse config) {
        if (config.getIdentifiers() == null) {
            return;
        }

        for (CentralConfigIdentifierDto dto : config.getIdentifiers()) {
            if (dto.getId() == null) {
                continue;
            }

            LocalAccessIdentifier entity = identifierRepository.findById(dto.getId())
                    .orElseGet(LocalAccessIdentifier::new);

            entity.setId(dto.getId());
            entity.setPersonId(dto.getPersonId());
            entity.setIdentifierType(dto.getIdentifierType());
            entity.setIdentifierValueHash(dto.getIdentifierValueHash());
            entity.setValidFrom(dto.getValidFrom());
            entity.setValidTo(dto.getValidTo());
            entity.setStatus(dto.getStatus());

            identifierRepository.save(entity);
        }
    }

    private void applyAccessRules(CentralConfigResponse config) {
        if (config.getAccessRules() == null) {
            return;
        }

        for (CentralConfigAccessRuleDto dto : config.getAccessRules()) {
            if (dto.getId() == null) {
                continue;
            }

            LocalAccessRule entity = ruleRepository.findById(dto.getId())
                    .orElseGet(LocalAccessRule::new);

            entity.setId(dto.getId());
            entity.setPersonId(dto.getPersonId());
            entity.setAccessPointId(dto.getAccessPointId());
            entity.setScheduleId(dto.getScheduleId());
            entity.setValidFrom(dto.getValidFrom());
            entity.setValidTo(dto.getValidTo());
            entity.setActive(dto.getActive());

            ruleRepository.save(entity);
        }
    }

    private void applySchedules(CentralConfigResponse config) {
        if (config.getSchedules() == null) {
            return;
        }

        for (CentralConfigScheduleDto dto : config.getSchedules()) {
            if (dto.getId() == null) {
                continue;
            }

            LocalSchedule entity = scheduleRepository.findById(dto.getId())
                    .orElseGet(LocalSchedule::new);

            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setActive(dto.getActive());

            scheduleRepository.save(entity);
        }
    }

    private void applyScheduleIntervals(CentralConfigResponse config) {
        if (config.getScheduleIntervals() == null) {
            return;
        }

        for (CentralConfigScheduleIntervalDto dto : config.getScheduleIntervals()) {
            if (dto.getId() == null) {
                continue;
            }

            LocalScheduleInterval entity = intervalRepository.findById(dto.getId())
                    .orElseGet(LocalScheduleInterval::new);

            entity.setId(dto.getId());
            entity.setScheduleId(dto.getScheduleId());
            entity.setDayOfWeek(dto.getDayOfWeek());
            entity.setStartTime(dto.getStartTime());
            entity.setEndTime(dto.getEndTime());

            intervalRepository.save(entity);
        }
    }

    private void updateConfigState(CentralConfigResponse config) {
        LocalConfigState state = configStateRepository
                .findFirstByLocalServerId(properties.getLocalServer().getId())
                .orElseGet(LocalConfigState::new);

        state.setLocalServerId(properties.getLocalServer().getId());
        state.setObjectId(config.getObjectId());
        state.setLastConfigPullAt(LocalDateTime.now());

        configStateRepository.save(state);
    }
}