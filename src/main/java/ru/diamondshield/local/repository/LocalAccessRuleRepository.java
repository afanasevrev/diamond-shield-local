package ru.diamondshield.local.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diamondshield.local.entity.LocalAccessRule;

import java.util.Optional;
import java.util.UUID;

public interface LocalAccessRuleRepository extends JpaRepository<LocalAccessRule, UUID> {

    Optional<LocalAccessRule> findByPersonIdAndAccessPointId(UUID personId, UUID accessPointId);
}