package com.ganaderia.animal.mapper;

import com.ganaderia.animal.dto.request.CreateAnimalRequest;
import com.ganaderia.animal.dto.request.UpdateAnimalRequest;
import com.ganaderia.animal.dto.response.AnimalResponse;
import com.ganaderia.animal.dto.response.BreedResponse;
import com.ganaderia.animal.entity.Animal;
import com.ganaderia.animal.entity.Breed;
import org.springframework.stereotype.Component;

@Component
public class AnimalMapper {

    public Animal toEntity(CreateAnimalRequest request) {
        Animal animal = new Animal();
        animal.setAnimalCode(request.getAnimalCode());
        animal.setName(request.getName());
        animal.setGender(request.getGender());
        animal.setBirthDate(request.getBirthDate());
        animal.setMotherId(request.getMotherId());
        animal.setFatherId(request.getFatherId());
        animal.setWeightKg(request.getWeightKg());
        animal.setNotes(request.getNotes());
        animal.setStatus(Animal.AnimalStatus.ACTIVE);
        return animal;
    }

    public void updateEntityFromRequest(UpdateAnimalRequest request, Animal animal) {
        if (request.getName() != null) {
            animal.setName(request.getName());
        }
        if (request.getGender() != null) {
            animal.setGender(request.getGender());
        }
        if (request.getBirthDate() != null) {
            animal.setBirthDate(request.getBirthDate());
        }
        if (request.getMotherId() != null) {
            animal.setMotherId(request.getMotherId());
        }
        if (request.getFatherId() != null) {
            animal.setFatherId(request.getFatherId());
        }
        if (request.getStatus() != null) {
            animal.setStatus(request.getStatus());
        }
        if (request.getWeightKg() != null) {
            animal.setWeightKg(request.getWeightKg());
        }
        if (request.getNotes() != null) {
            animal.setNotes(request.getNotes());
        }
    }

    public AnimalResponse toResponse(Animal animal) {
        AnimalResponse response = new AnimalResponse();
        response.setId(animal.getId());
        response.setAnimalCode(animal.getAnimalCode());
        response.setName(animal.getName());
        response.setGender(animal.getGender());
        response.setBirthDate(animal.getBirthDate());
        response.setMotherId(animal.getMotherId());
        response.setFatherId(animal.getFatherId());
        response.setStatus(animal.getStatus());
        response.setWeightKg(animal.getWeightKg());
        response.setNotes(animal.getNotes());
        response.setActive(animal.getActive());
        response.setCreatedAt(animal.getCreatedAt());
        response.setUpdatedAt(animal.getUpdatedAt());

        // Mapear raza si existe
        if (animal.getBreed() != null) {
            response.setBreed(toBreedResponse(animal.getBreed()));
        }

        return response;
    }

    public BreedResponse toBreedResponse(Breed breed) {
        BreedResponse response = new BreedResponse();
        response.setId(breed.getId());
        response.setName(breed.getName());
        response.setDescription(breed.getDescription());
        response.setAnimalType(breed.getAnimalType());
        return response;
    }
}