package ru.diamondshield.local.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "local_persons")
public class LocalPerson {

    @Id
    private UUID id;

    @Column(name = "person_type", length = 50)
    private String personType;

    @Column(name = "personnel_number", length = 50)
    private String personnelNumber;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "middle_name", length = 100)
    private String middleName;

    @Column(nullable = false)
    private Boolean active;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public LocalPerson() {
    }

    @PrePersist
    @PreUpdate
    public void touch() {
        this.updatedAt = LocalDateTime.now();

        if (this.active == null) {
            this.active = true;
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getPersonnelNumber() {
        return personnelNumber;
    }

    public void setPersonnelNumber(String personnelNumber) {
        this.personnelNumber = personnelNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}