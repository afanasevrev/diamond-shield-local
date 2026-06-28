package ru.diamondshield.local.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diamondshield.local.entity.LocalSchedule;
import ru.diamondshield.local.entity.LocalScheduleInterval;
import ru.diamondshield.local.repository.LocalScheduleIntervalRepository;
import ru.diamondshield.local.repository.LocalScheduleRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class LocalScheduleCheckService {

    private final LocalScheduleRepository scheduleRepository;
    private final LocalScheduleIntervalRepository intervalRepository;

    public LocalScheduleCheckService(LocalScheduleRepository scheduleRepository,
                                     LocalScheduleIntervalRepository intervalRepository) {
        this.scheduleRepository = scheduleRepository;
        this.intervalRepository = intervalRepository;
    }

    @Transactional(readOnly = true)
    public boolean isAllowed(UUID scheduleId, LocalDateTime dateTime) {
        if (scheduleId == null) {
            return true;
        }

        LocalSchedule schedule = scheduleRepository.findById(scheduleId).orElse(null);

        if (schedule == null) {
            return false;
        }

        if (!Boolean.TRUE.equals(schedule.getActive())) {
            return false;
        }

        Integer dayOfWeek = dateTime.getDayOfWeek().getValue();
        LocalTime currentTime = dateTime.toLocalTime();

        List<LocalScheduleInterval> intervals =
                intervalRepository.findByScheduleIdAndDayOfWeek(scheduleId, dayOfWeek);

        for (LocalScheduleInterval interval : intervals) {
            boolean afterOrEqualsStart = !currentTime.isBefore(interval.getStartTime());
            boolean beforeOrEqualsEnd = !currentTime.isAfter(interval.getEndTime());

            if (afterOrEqualsStart && beforeOrEqualsEnd) {
                return true;
            }
        }

        return false;
    }
}