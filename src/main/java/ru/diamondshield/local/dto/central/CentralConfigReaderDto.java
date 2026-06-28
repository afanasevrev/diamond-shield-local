package ru.diamondshield.local.dto.central;

import java.util.UUID;

public class CentralConfigReaderDto {

    private UUID id;
    private UUID controllerId;
    private UUID accessPointId;

    private String name;
    private String readerType;
    private String direction;
    private String status;

    public CentralConfigReaderDto() {
    }

    public UUID getId() {
        return id;
    }

    public UUID getControllerId() {
        return controllerId;
    }

    public UUID getAccessPointId() {
        return accessPointId;
    }

    public String getName() {
        return name;
    }

    public String getReaderType() {
        return readerType;
    }

    public String getDirection() {
        return direction;
    }

    public String getStatus() {
        return status;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setControllerId(UUID controllerId) {
        this.controllerId = controllerId;
    }

    public void setAccessPointId(UUID accessPointId) {
        this.accessPointId = accessPointId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReaderType(String readerType) {
        this.readerType = readerType;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}