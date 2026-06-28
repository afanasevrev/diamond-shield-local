package ru.diamondshield.local.dto.central;

import java.util.UUID;

public class CentralConfigControllerDto {

    private UUID id;
    private String name;
    private String model;
    private String serialNumber;
    private String ipAddress;
    private Integer port;
    private String status;

    public CentralConfigControllerDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Integer getPort() {
        return port;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}