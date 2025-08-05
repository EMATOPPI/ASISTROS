package com.ganaderia.animal.dto.response;

import com.ganaderia.animal.entity.Breed;

public class BreedResponse {
    private Long id;
    private String name;
    private String description;
    private Breed.AnimalType animalType;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Breed.AnimalType getAnimalType() { return animalType; }
    public void setAnimalType(Breed.AnimalType animalType) { this.animalType = animalType; }
}