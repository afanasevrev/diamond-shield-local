package ru.diamondshield.local.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diamondshield.local.entity.LocalControllerDevice;

import java.util.UUID;

public interface LocalControllerDeviceRepository extends JpaRepository<LocalControllerDevice, UUID> {
}