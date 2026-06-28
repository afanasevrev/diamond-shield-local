package ru.diamondshield.local.dto.central;

import java.time.LocalDateTime;
import java.util.UUID;

public class CentralConfigIdentifierDto {

    private UUID id;
    private UUID personId;

    private String identifierType;
    private String identifierValueHash;

    private LocalDateTime validFrom;
    private LocalDateTime validTo;

    private String status;

    public CentralConfigIdentifierDto() {
    }

    public UUID getId() {
        return id;
    }

    public UUID getPersonId() {
        return personId;
    }

    public String getIdentifierType() {
        return identifierType;
    }

    public String getIdentifierValueHash() {
        return identifierValueHash;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public String getStatus() {
        return status;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }

    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }

    public void setIdentifierValueHash(String identifierValueHash) {
        this.identifierValueHash = identifierValueHash;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}