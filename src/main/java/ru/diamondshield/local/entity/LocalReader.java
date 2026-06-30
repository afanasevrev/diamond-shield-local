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

    @Column(name = "perco_exdev_number")
    private Integer percoExdevNumber;

    @Column(name = "perco_direction")
    private Integer percoDirection;

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

    public String getReaderType() {
        return readerType;
    }

    public String getDirection() {
        return direction;
    }

    public UUID getAccessPointId() {
        return accessPointId;
    }

    public Integer getPercoExdevNumber() {
        return percoExdevNumber;
    }

    public Integer getPercoDirection() {
        return percoDirection;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
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

    public void setAccessPointId(UUID accessPointId) {
        this.accessPointId = accessPointId;
    }

    public void setPercoExdevNumber(Integer percoExdevNumber) {
        this.percoExdevNumber = percoExdevNumber;
    }

    public void setPercoDirection(Integer percoDirection) {
        this.percoDirection = percoDirection;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}