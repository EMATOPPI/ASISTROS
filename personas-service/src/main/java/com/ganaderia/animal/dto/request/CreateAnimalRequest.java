package com.ganaderia.animal.dto.request;

import com.ganaderia.animal.entity.Animal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CreateAnimalRequest {

    @NotBlank(message = "Animal code is required")
    @Size(max = 50, message = "Animal code must not exceed 50 characters")
    private String animalCode;

    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    private Long breedId;

    @NotNull(message = "Gender is required")
    private Animal.Gender gender;

    private LocalDate birthDate;

    private Long motherId;

    private Long fatherId;

    private Double weightKg;

    private String notes;

    // Getters and Setters
    public String getAnimalCode() { return animalCode; }
    public void setAnimalCode(String animalCode) { this.animalCode = animalCode; }

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

    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
