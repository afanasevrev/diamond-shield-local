package ru.diamondshield.local.dto.central;

import java.util.UUID;

public class CentralConfigScheduleDto {

    private UUID id;
    private String name;
    private Boolean active;

    public CentralConfigScheduleDto() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}