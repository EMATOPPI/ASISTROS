package com.ganaderia.animal.controller;

import com.ganaderia.animal.dto.response.ApiResponse;
import com.ganaderia.animal.dto.response.BreedResponse;
import com.ganaderia.animal.entity.Breed;
import com.ganaderia.animal.mapper.AnimalMapper;
import com.ganaderia.animal.repository.BreedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/breeds")
@CrossOrigin(origins = "*")
public class BreedController {

    @Autowired
    private BreedRepository breedRepository;

    @Autowired
    private AnimalMapper animalMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BreedResponse>>> getAllBreeds() {
        try {
            List<Breed> breeds = breedRepository.findByActiveTrue();
            List<BreedResponse> breedResponses = breeds.stream()
                    .map(animalMapper::toBreedResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(ApiResponse.success(breedResponses, "Breeds retrieved successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve breeds: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BreedResponse>> getBreedById(@PathVariable Long id) {
        try {
            Breed breed = breedRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Breed not found: " + id));

            BreedResponse response = animalMapper.toBreedResponse(breed);
            return ResponseEntity.ok(ApiResponse.success(response, "Breed retrieved successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve breed: " + e.getMessage()));
        }
    }

    @GetMapping("/type/{animalType}")
    public ResponseEntity<ApiResponse<List<BreedResponse>>> getBreedsByType(@PathVariable Breed.AnimalType animalType) {
        try {
            List<Breed> breeds = breedRepository.findByAnimalTypeAndActiveTrue(animalType);
            List<BreedResponse> breedResponses = breeds.stream()
                    .map(animalMapper::toBreedResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(ApiResponse.success(breedResponses, "Breeds retrieved by type"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve breeds: " + e.getMessage()));
        }
    }
}