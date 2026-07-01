package ru.diamondshield.local.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.diamondshield.local.config.LocalServerProperties;
import ru.diamondshield.local.entity.LocalConfigState;
import ru.diamondshield.local.repository.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/local/diagnostics")
public class LocalDiagnosticsController {

    private final LocalServerProperties properties;
    private final LocalConfigStateRepository configStateRepository;
    private final LocalControllerDeviceRepository controllerRepository;
    private final LocalReaderRepository readerRepository;
    private final LocalAccessPointRepository accessPointRepository;
    private final LocalPersonRepository personRepository;
    private final LocalAccessIdentifierRepository identifierRepository;
    private final LocalAccessRuleRepository ruleRepository;
    private final LocalScheduleRepository scheduleRepository;
    private final LocalScheduleIntervalRepository intervalRepository;
    private final LocalAccessEventRepository accessEventRepository;
    private final LocalAlarmEventRepository alarmEventRepository;
    private final LocalDeviceStatusEventRepository deviceStatusEventRepository;

    public LocalDiagnosticsController(LocalServerProperties properties,
                                      LocalConfigStateRepository configStateRepository,
                                      LocalControllerDeviceRepository controllerRepository,
                                      LocalReaderRepository readerRepository,
                                      LocalAccessPointRepository accessPointRepository,
                                      LocalPersonRepository personRepository,
                                      LocalAccessIdentifierRepository identifierRepository,
                                      LocalAccessRuleRepository ruleRepository,
                                      LocalScheduleRepository scheduleRepository,
                                      LocalScheduleIntervalRepository intervalRepository,
                                      LocalAccessEventRepository accessEventRepository,
                                      LocalAlarmEventRepository alarmEventRepository,
                                      LocalDeviceStatusEventRepository deviceStatusEventRepository) {
        this.properties = properties;
        this.configStateRepository = configStateRepository;
        this.controllerRepository = controllerRepository;
        this.readerRepository = readerRepository;
        this.accessPointRepository = accessPointRepository;
        this.personRepository = personRepository;
        this.identifierRepository = identifierRepository;
        this.ruleRepository = ruleRepository;
        this.scheduleRepository = scheduleRepository;
        this.intervalRepository = intervalRepository;
        this.accessEventRepository = accessEventRepository;
        this.alarmEventRepository = alarmEventRepository;
        this.deviceStatusEventRepository = deviceStatusEventRepository;
    }

    @GetMapping("/status")
    public Map<String, Object> status() {
        Map<String, Object> result = new LinkedHashMap<>();

        result.put("application", "diamondshield-local-server");
        result.put("status", "running");
        result.put("serverTime", LocalDateTime.now());

        result.put("localServerId", properties.getLocalServer().getId());
        result.put("centralBaseUrl", properties.getCentral().getBaseUrl());

        result.put("controllersSource", "database");
        result.put("controllersNote", "Controllers are received from central server config and stored in local DB");

        result.put("percoEnabled", properties.getPerco().isEnabled());
        result.put("percoWebsocketPath", properties.getPerco().getWebsocketPath());
        result.put("percoDefaultOpenTimeMs", properties.getPerco().getDefaultOpenTimeMs());
        result.put("percoDefaultOpenType", properties.getPerco().getDefaultOpenType());
        result.put("percoIdentifierType", properties.getPerco().getIdentifierType());
        
        LocalConfigState state = configStateRepository
                .findFirstByLocalServerId(properties.getLocalServer().getId())
                .orElse(null);

        if (state == null) {
            result.put("objectId", null);
            result.put("lastConfigPullAt", null);
            result.put("lastSuccessfulPushAt", null);
        } else {
            result.put("objectId", state.getObjectId());
            result.put("lastConfigPullAt", state.getLastConfigPullAt());
            result.put("lastSuccessfulPushAt", state.getLastSuccessfulPushAt());
        }

        result.put("configState", configStateRepository.count());

        result.put("controllers", controllerRepository.count());
        result.put("readers", readerRepository.count());
        result.put("accessPoints", accessPointRepository.count());

        result.put("persons", personRepository.count());
        result.put("identifiers", identifierRepository.count());
        result.put("accessRules", ruleRepository.count());

        result.put("schedules", scheduleRepository.count());
        result.put("scheduleIntervals", intervalRepository.count());

        result.put("accessEvents", accessEventRepository.count());
        result.put("sentAccessEvents", accessEventRepository.countBySentToCentralTrue());
        result.put("unsentAccessEvents", accessEventRepository.countBySentToCentralFalse());

        result.put("alarmEvents", alarmEventRepository.count());
        result.put("sentAlarmEvents", alarmEventRepository.countBySentToCentralTrue());
        result.put("unsentAlarmEvents", alarmEventRepository.countBySentToCentralFalse());

        result.put("deviceStatusEvents", deviceStatusEventRepository.count());
        result.put("sentDeviceStatusEvents", deviceStatusEventRepository.countBySentToCentralTrue());
        result.put("unsentDeviceStatusEvents", deviceStatusEventRepository.countBySentToCentralFalse());

        return result;
    }
}