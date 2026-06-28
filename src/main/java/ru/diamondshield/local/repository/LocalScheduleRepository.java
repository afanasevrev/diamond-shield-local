package ru.diamondshield.local.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diamondshield.local.entity.LocalSchedule;

import java.util.UUID;

public interface LocalScheduleRepository extends JpaRepository<LocalSchedule, UUID> {
}