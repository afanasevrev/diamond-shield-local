package ru.diamondshield.local.dto.central;

import java.time.LocalTime;
import java.util.UUID;

public class CentralConfigScheduleIntervalDto {

    private UUID id;
    private UUID scheduleId;

    private Integer dayOfWeek;

    private LocalTime startTime;
    private LocalTime endTime;

    public CentralConfigScheduleIntervalDto() {
    }

    public UUID getId() {
        return id;
    }

    public UUID getScheduleId() {
        return scheduleId;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setScheduleId(UUID scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}