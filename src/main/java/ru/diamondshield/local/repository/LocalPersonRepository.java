package ru.diamondshield.local.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diamondshield.local.entity.LocalPerson;

import java.util.UUID;

public interface LocalPersonRepository extends JpaRepository<LocalPerson, UUID> {
}