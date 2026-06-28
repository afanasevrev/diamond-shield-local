package ru.diamondshield.local.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diamondshield.local.entity.LocalAccessPoint;

import java.util.UUID;

public interface LocalAccessPointRepository extends JpaRepository<LocalAccessPoint, UUID> {
}