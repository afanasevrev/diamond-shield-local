package ru.diamondshield.local.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "local_config_state")
public class LocalConfigState {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "local_server_id", nullable = false)
    private UUID localServerId;

    @Column(name = "object_id")
    private UUID objectId;

    @Column(name = "last_config_pull_at")
    private LocalDateTime lastConfigPullAt;

    @Column(name = "last_successful_push_at")
    private LocalDateTime lastSuccessfulPushAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public LocalConfigState() {
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
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

    public LocalDateTime getLastConfigPullAt() {
        return lastConfigPullAt;
    }

    public void setLastConfigPullAt(LocalDateTime lastConfigPullAt) {
        this.lastConfigPullAt = lastConfigPullAt;
    }

    public LocalDateTime getLastSuccessfulPushAt() {
        return lastSuccessfulPushAt;
    }

    public void setLastSuccessfulPushAt(LocalDateTime lastSuccessfulPushAt) {
        this.lastSuccessfulPushAt = lastSuccessfulPushAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}