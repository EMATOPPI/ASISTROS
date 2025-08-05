package com.ganaderia.animal.dto.response;

import com.ganaderia.animal.entity.Animal;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AnimalResponse {
    private Long id;
    private String animalCode;
    private String name;
    private BreedResponse breed;
    private Animal.Gender gender;
    private LocalDate birthDate;
    private Long motherId;
    private Long fatherId;
    private Animal.AnimalStatus status;
    private Double weightKg;
    private String notes;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAnimalCode() { return animalCode; }
    public void setAnimalCode(String animalCode) { this.animalCode = animalCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BreedResponse getBreed() { return breed; }
    public void setBreed(BreedResponse breed) { this.breed = breed; }

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

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
