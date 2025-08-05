package com.ganaderia.animal.dto.request;

import com.ganaderia.animal.entity.Animal;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UpdateAnimalRequest {

    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    private Long breedId;

    private Animal.Gender gender;

    private LocalDate birthDate;

    private Long motherId;

    private Long fatherId;

    private Animal.AnimalStatus status;

    private Double weightKg;

    private String notes;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getBreedId() { return breedId; }
    public void setBreedId(Long breedId) { this.breedId = breedId; }

    public Animal.Gender getGender() { return gender; }
    public void setGender(Animal.Gender gender) { this.gender = gender; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public Long getMotherId() { return motherId; }
    public void setMotherId(Long motherId) { this.motherId = motherId; }

    public Long getFatherId() { return fatherId; }
    public void setFatherId(Long fatherId) { this.fatherId = fatherId; }

    public Animal.AnimalStatus getStatus() { return status; }
    public void setStatus(Animal.AnimalStatus status) { this.status = status; }

    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}