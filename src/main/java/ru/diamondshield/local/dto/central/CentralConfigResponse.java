package ru.diamondshield.local.dto.central;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CentralConfigResponse {

    private UUID localServerId;
    private UUID objectId;
    private LocalDateTime generatedAt;

    private List<CentralConfigControllerDto> controllers;
    private List<CentralConfigReaderDto> readers;
    private List<CentralConfigAccessPointDto> accessPoints;
    private List<CentralConfigPersonDto> persons;
    private List<CentralConfigIdentifierDto> identifiers;
    private List<CentralConfigAccessRuleDto> accessRules;
    private List<CentralConfigScheduleDto> schedules;
    private List<CentralConfigScheduleIntervalDto> scheduleIntervals;

    public CentralConfigResponse() {
    }

    public UUID getLocalServerId() {
        return localServerId;
    }

    public void setLocalServerId(UUID localServerId) {
        this.localServerId = localServerId;
    }

    public UUID getObjectId() {
        return objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public List<CentralConfigControllerDto> getControllers() {
        return controllers;
    }

    public void setControllers(List<CentralConfigControllerDto> controllers) {
        this.controllers = controllers;
    }

    public List<CentralConfigReaderDto> getReaders() {
        return readers;
    }

    public void setReaders(List<CentralConfigReaderDto> readers) {
        this.readers = readers;
    }

    public List<CentralConfigAccessPointDto> getAccessPoints() {
        return accessPoints;
    }

    public void setAccessPoints(List<CentralConfigAccessPointDto> accessPoints) {
        this.accessPoints = accessPoints;
    }

    public List<CentralConfigPersonDto> getPersons() {
        return persons;
    }

    public void setPersons(List<CentralConfigPersonDto> persons) {
        this.persons = persons;
    }

    public List<CentralConfigIdentifierDto> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<CentralConfigIdentifierDto> identifiers) {
        this.identifiers = identifiers;
    }

    public List<CentralConfigAccessRuleDto> getAccessRules() {
        return accessRules;
    }

    public void setAccessRules(List<CentralConfigAccessRuleDto> accessRules) {
        this.accessRules = accessRules;
    }

    public List<CentralConfigScheduleDto> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<CentralConfigScheduleDto> schedules) {
        this.schedules = schedules;
    }

    public List<CentralConfigScheduleIntervalDto> getScheduleIntervals() {
        return scheduleIntervals;
    }

    public void setScheduleIntervals(List<CentralConfigScheduleIntervalDto> scheduleIntervals) {
        this.scheduleIntervals = scheduleIntervals;
    }
}