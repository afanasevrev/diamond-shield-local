package ru.diamondshield.local.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diamondshield.local.entity.LocalDeviceStatusEvent;

import java.util.List;
import java.util.UUID;

public interface LocalDeviceStatusEventRepository extends JpaRepository<LocalDeviceStatusEvent, UUID> {

    List<LocalDeviceStatusEvent> findTop100BySentToCentralFalseOrderByCreatedAtAsc();

    long countBySentToCentralFalse();

    long countBySentToCentralTrue();
}