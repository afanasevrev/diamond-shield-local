package ru.diamondshield.local.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "local_access_points")
public class LocalAccessPoint {

    @Id
    private UUID id;

    @Column(name = "controller_id")
    private UUID controllerId;

    @Column(name = "zone_from_id")
    private UUID zoneFromId;

    @Column(name = "zone_to_id")
    private UUID zoneToId;

    @Column(length = 255)
    private String name;

    @Column(name = "access_point_type", length = 50)
    private String accessPointType;

    @Column(nullable = false)
    private Boolean active;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public LocalAccessPoint() {
    }

    @PrePersist
    @PreUpdate
    public void touch() {
        this.updatedAt = LocalDateTime.now();

        if (this.active == null) {
            this.active = true;
        }
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

    public UUID getZoneFromId() {
        return zoneFromId;
    }

    public void setZoneFromId(UUID zoneFromId) {
        this.zoneFromId = zoneFromId;
    }

    public UUID getZoneToId() {
        return zoneToId;
    }

    public void setZoneToId(UUID zoneToId) {
        this.zoneToId = zoneToId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessPointType() {
        return accessPointType;
    }

    public void setAccessPointType(String accessPointType) {
        this.accessPointType = accessPointType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}