package ru.diamondshield.local.dto.central;

public class CentralHeartbeatRequest {

    private String ipAddress;
    private String softwareVersion;
    private String status;
    private String message;

    public CentralHeartbeatRequest() {
    }

    public CentralHeartbeatRequest(String ipAddress,
                                   String softwareVersion,
                                   String status,
                                   String message) {
        this.ipAddress = ipAddress;
        this.softwareVersion = softwareVersion;
        this.status = status;
        this.message = message;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
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
}