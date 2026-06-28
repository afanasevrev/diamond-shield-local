package ru.diamondshield.local.dto.central;

import java.time.LocalDateTime;
import java.util.UUID;

public class CentralConfigAccessRuleDto {

    private UUID id;
    private UUID personId;
    private UUID accessPointId;
    private UUID scheduleId;

    private LocalDateTime validFrom;
    private LocalDateTime validTo;

    private Boolean active;

    public CentralConfigAccessRuleDto() {
    }

    public UUID getId() {
        return id;
    }

    public UUID getPersonId() {
        return personId;
    }

    public UUID getAccessPointId() {
        return accessPointId;
    }

    public UUID getScheduleId() {
        return scheduleId;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public Boolean getActive() {
        return active;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }

    public void setAccessPointId(UUID accessPointId) {
        this.accessPointId = accessPointId;
    }

    public void setScheduleId(UUID scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}