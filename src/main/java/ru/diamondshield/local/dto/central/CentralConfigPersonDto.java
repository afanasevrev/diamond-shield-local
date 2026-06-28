package ru.diamondshield.local.dto.central;

import java.util.UUID;

public class CentralConfigPersonDto {

    private UUID id;
    private String personType;
    private String personnelNumber;
    private String lastName;
    private String firstName;
    private String middleName;
    private Boolean active;

    public CentralConfigPersonDto() {
    }

    public UUID getId() {
        return id;
    }

    public String getPersonType() {
        return personType;
    }

    public String getPersonnelNumber() {
        return personnelNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public void setPersonnelNumber(String personnelNumber) {
        this.personnelNumber = personnelNumber;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}