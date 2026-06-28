package ru.diamondshield.local.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diamondshield.local.entity.LocalReader;

import java.util.List;
import java.util.UUID;

public interface LocalReaderRepository extends JpaRepository<LocalReader, UUID> {

    List<LocalReader> findByControllerId(UUID controllerId);

    List<LocalReader> findByAccessPointId(UUID accessPointId);
}