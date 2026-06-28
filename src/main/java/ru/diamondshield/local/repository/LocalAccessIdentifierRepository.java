package ru.diamondshield.local.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diamondshield.local.entity.LocalAccessIdentifier;

import java.util.Optional;
import java.util.UUID;

public interface LocalAccessIdentifierRepository extends JpaRepository<LocalAccessIdentifier, UUID> {

    Optional<LocalAccessIdentifier> findByIdentifierTypeAndIdentifierValueHash(String identifierType,
                                                                               String identifierValueHash);
}