package ru.diamondshield.local.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diamondshield.local.entity.LocalConfigState;

import java.util.Optional;
import java.util.UUID;

public interface LocalConfigStateRepository extends JpaRepository<LocalConfigState, UUID> {

    Optional<LocalConfigState> findFirstByLocalServerId(UUID localServerId);
}