package ru.diamondshield.local.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "local_readers")
public class LocalReader {

    @Id
    private UUID id;

    @Column(name = "controller_id")
    private UUID controllerId;

    @Column(length = 255)
    private String name;

    @Column(name = "reader_type", length = 50)
    private String readerType;

    @Column(length = 20)
    private String direction;

    @Column(name = "access_point_id")
    private UUID accessPointId;

    @Column(length = 50)
    private String status;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public LocalReader() {
    }

    @PrePersist
    @PreUpdate
    public void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getControllerId() {
        return controllerId;
    }

    public void setControllerId(UUID controllerId) {
        this.controllerId = controllerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReaderType() {
        return readerType;
    }

    public void setReaderType(String readerType) {
        this.readerType = readerType;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public UUID getAccessPointId() {
        return accessPointId;
    }

    public void setAccessPointId(UUID accessPointId) {
        this.accessPointId = accessPointId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}