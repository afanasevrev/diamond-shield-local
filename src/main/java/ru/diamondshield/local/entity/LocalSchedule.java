package ru.diamondshield.local.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "local_schedules")
public class LocalSchedule {

    @Id
    private UUID id;

    @Column(length = 255)
    private String name;

    @Column(nullable = false)
    private Boolean active;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public LocalSchedule() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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