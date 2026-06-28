package ru.diamondshield.local.dto.localcheck;

import java.time.LocalDateTime;
import java.util.UUID;

public class LocalAccessCheckResponse {

    private String decision;
    private Boolean allowed;
    private String reason;

    private UUID personId;
    private UUID identifierId;
    private UUID accessEventId;

    private LocalDateTime checkedAt;

    public LocalAccessCheckResponse() {
    }

    public LocalAccessCheckResponse(String decision,
                                    Boolean allowed,
                                    String reason,
                                    UUID personId,
                                    UUID identifierId,
                                    UUID accessEventId,
                                    LocalDateTime checkedAt) {
        this.decision = decision;
        this.allowed = allowed;
        this.reason = reason;
        this.personId = personId;
        this.identifierId = identifierId;
        this.accessEventId = accessEventId;
        this.checkedAt = checkedAt;
    }

    public String getDecision() {
        return decision;
    }

    public Boolean getAllowed() {
        return allowed;
    }

    public String getReason() {
        return reason;
    }

    public UUID getPersonId() {
        return personId;
    }

    public UUID getIdentifierId() {
        return identifierId;
    }

    public UUID getAccessEventId() {
        return accessEventId;
    }

    public LocalDateTime getCheckedAt() {
        return checkedAt;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public void setAllowed(Boolean allowed) {
        this.allowed = allowed;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }

    public void setIdentifierId(UUID identifierId) {
        this.identifierId = identifierId;
    }

    public void setAccessEventId(UUID accessEventId) {
        this.accessEventId = accessEventId;
    }

    public void setCheckedAt(LocalDateTime checkedAt) {
        this.checkedAt = checkedAt;
    }
}