package ru.diamondshield.local.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "local_alarm_events")
public class LocalAlarmEvent {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "local_event_id", nullable = false, length = 100)
    private String localEventId;

    @Column(name = "object_id")
    private UUID objectId;

    @Column(name = "access_point_id")
    private UUID accessPointId;

    @Column(name = "reader_id")
    private UUID readerId;

    @Column(name = "controller_id")
    private UUID controllerId;

    @Column(name = "alarm_type", nullable = false, length = 100)
    private String alarmType;

    @Column(nullable = false, length = 50)
    private String severity;

    @Column(columnDefinition = "text")
    private String message;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    @Column(name = "sent_to_central", nullable = false)
    private Boolean sentToCentral;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public LocalAlarmEvent() {
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();

        if (this.sentToCentral == null) {
            this.sentToCentral = false;
        }
    }

    public UUID getId() {
        return id;
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

    public Boolean getSentToCentral() {
        return sentToCentral;
    }

    public void setSentToCentral(Boolean sentToCentral) {
        this.sentToCentral = sentToCentral;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}