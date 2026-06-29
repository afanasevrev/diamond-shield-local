package ru.diamondshield.local.dto.central;

import java.time.LocalDateTime;
import java.util.UUID;

public class CentralAlarmEventPushRequest {

    private String localEventId;

    private UUID objectId;
    private UUID accessPointId;
    private UUID readerId;
    private UUID controllerId;

    private String alarmType;
    private String severity;
    private String message;

    private LocalDateTime occurredAt;

    public CentralAlarmEventPushRequest() {
    }

    public String getLocalEventId() {
        return localEventId;
    }

    public void setLocalEventId(String localEventId) {
        this.localEventId = localEventId;
    }

    public UUID getObjectId() {
        return objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    public UUID getAccessPointId() {
        return accessPointId;
    }

    public void setAccessPointId(UUID accessPointId) {
        this.accessPointId = accessPointId;
    }

    public UUID getReaderId() {
        return readerId;
    }

    public void setReaderId(UUID readerId) {
        this.readerId = readerId;
    }

    public UUID getControllerId() {
        return controllerId;
    }

    public void setControllerId(UUID controllerId) {
        this.controllerId = controllerId;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(LocalDateTime occurredAt) {
        this.occurredAt = occurredAt;
    }
}