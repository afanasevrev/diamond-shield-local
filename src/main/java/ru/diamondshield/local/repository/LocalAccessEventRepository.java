package ru.diamondshield.local.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diamondshield.local.entity.LocalAccessEvent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocalAccessEventRepository extends JpaRepository<LocalAccessEvent, UUID> {

    List<LocalAccessEvent> findTop100BySentToCentralFalseOrderByCreatedAtAsc();

    Optional<LocalAccessEvent> findByLocalEventId(String localEventId);

    long countBySentToCentralFalse();

    long countBySentToCentralTrue();
}