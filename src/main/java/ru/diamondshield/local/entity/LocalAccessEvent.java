package ru.diamondshield.local.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "local_access_events")
public class LocalAccessEvent {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "local_event_id", nullable = false, length = 100)
    private String localEventId;

    @Column(name = "object_id")
    private UUID objectId;

    @Column(name = "access_point_id")
    private UUID accessPointId;

    @Column(name = "reader_id")
    private UUID readerId;

    @Column(name = "controller_id")
    private UUID controllerId;

    @Column(name = "person_id")
    private UUID personId;

    @Column(name = "identifier_id")
    private UUID identifierId;

    @Column(name = "event_time", nullable = false)
    private LocalDateTime eventTime;

    @Column(length = 20)
    private String direction;

    @Column(name = "access_result", nullable = false, length = 50)
    private String accessResult;

    @Column(length = 255)
    private String reason;

    @Column(name = "identifier_type", length = 50)
    private String identifierType;

    @Column(name = "identifier_masked", length = 100)
    private String identifierMasked;

    @Column(name = "identifier_value_hash", columnDefinition = "text")
    private String identifierValueHash;

    @Column(name = "is_unknown_identifier", nullable = false)
    private Boolean unknownIdentifier;

    @Column(name = "sent_to_central", nullable = false)
    private Boolean sentToCentral;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public LocalAccessEvent() {
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();

        if (this.sentToCentral == null) {
            this.sentToCentral = false;
        }

        if (this.unknownIdentifier == null) {
            this.unknownIdentifier = false;
        }
    }

    public UUID getId() {
        return id;
    }

    public String getLocalEventId() {
        return localEventId;
    }

    public void setLocalEventId(String localEventId) {
        this.localEventId = localEventId;
    }

    public UUID getObjectId() {
        return objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    public UUID getAccessPointId() {
        return accessPointId;
    }

    public void setAccessPointId(UUID accessPointId) {
        this.accessPointId = accessPointId;
    }

    public UUID getReaderId() {
        return readerId;
    }

    public void setReaderId(UUID readerId) {
        this.readerId = readerId;
    }

    public UUID getControllerId() {
        return controllerId;
    }

    public void setControllerId(UUID controllerId) {
        this.controllerId = controllerId;
    }

    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }

    public UUID getIdentifierId() {
        return identifierId;
    }

    public void setIdentifierId(UUID identifierId) {
        this.identifierId = identifierId;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getAccessResult() {
        return accessResult;
    }

    public void setAccessResult(String accessResult) {
        this.accessResult = accessResult;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }

    public String getIdentifierMasked() {
        return identifierMasked;
    }

    public void setIdentifierMasked(String identifierMasked) {
        this.identifierMasked = identifierMasked;
    }

    public String getIdentifierValueHash() {
        return identifierValueHash;
    }

    public void setIdentifierValueHash(String identifierValueHash) {
        this.identifierValueHash = identifierValueHash;
    }

    public Boolean getUnknownIdentifier() {
        return unknownIdentifier;
    }

    public void setUnknownIdentifier(Boolean unknownIdentifier) {
        this.unknownIdentifier = unknownIdentifier;
    }

    public Boolean getSentToCentral() {
        return sentToCentral;
    }

    public void setSentToCentral(Boolean sentToCentral) {
        this.sentToCentral = sentToCentral;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}