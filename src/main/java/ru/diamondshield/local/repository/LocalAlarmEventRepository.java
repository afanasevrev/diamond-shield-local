package ru.diamondshield.local.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diamondshield.local.entity.LocalAlarmEvent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocalAlarmEventRepository extends JpaRepository<LocalAlarmEvent, UUID> {

    List<LocalAlarmEvent> findTop100BySentToCentralFalseOrderByCreatedAtAsc();

    Optional<LocalAlarmEvent> findByLocalEventId(String localEventId);
}