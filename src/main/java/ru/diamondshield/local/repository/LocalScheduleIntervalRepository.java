package ru.diamondshield.local.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diamondshield.local.entity.LocalScheduleInterval;

import java.util.List;
import java.util.UUID;

public interface LocalScheduleIntervalRepository extends JpaRepository<LocalScheduleInterval, UUID> {

    List<LocalScheduleInterval> findByScheduleIdAndDayOfWeek(UUID scheduleId, Integer dayOfWeek);

    void deleteByScheduleId(UUID scheduleId);
}