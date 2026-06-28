package ru.diamondshield.local.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "local_device_status_events")
public class LocalDeviceStatusEvent {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "object_id")
    private UUID objectId;

    @Column(name = "device_type", nullable = false, length = 50)
    private String deviceType;

    @Column(name = "device_id", nullable = false)
    private UUID deviceId;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(columnDefinition = "text")
    private String message;

    @Column(name = "sent_to_central", nullable = false)
    private Boolean sentToCentral;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public LocalDeviceStatusEvent() {
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

    public UUID getObjectId() {
        return objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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