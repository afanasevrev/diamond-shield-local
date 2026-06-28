package ru.diamondshield.local.dto.central;

import java.util.UUID;

public class CentralConfigAccessPointDto {

    private UUID id;
    private UUID controllerId;
    private UUID zoneFromId;
    private UUID zoneToId;

    private String name;
    private String accessPointType;
    private Boolean active;

    public CentralConfigAccessPointDto() {
    }

    public UUID getId() {
        return id;
    }

    public UUID getControllerId() {
        return controllerId;
    }

    public UUID getZoneFromId() {
        return zoneFromId;
    }

    public UUID getZoneToId() {
        return zoneToId;
    }

    public String getName() {
        return name;
    }

    public String getAccessPointType() {
        return accessPointType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setControllerId(UUID controllerId) {
        this.controllerId = controllerId;
    }

    public void setZoneFromId(UUID zoneFromId) {
        this.zoneFromId = zoneFromId;
    }

    public void setZoneToId(UUID zoneToId) {
        this.zoneToId = zoneToId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccessPointType(String accessPointType) {
        this.accessPointType = accessPointType;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}