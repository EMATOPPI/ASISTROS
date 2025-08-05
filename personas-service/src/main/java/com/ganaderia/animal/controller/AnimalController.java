package com.ganaderia.animal.controller;

import com.ganaderia.animal.dto.request.CreateAnimalRequest;
import com.ganaderia.animal.dto.request.UpdateAnimalRequest;
import com.ganaderia.animal.dto.response.AnimalResponse;
import com.ganaderia.animal.dto.response.ApiResponse;
import com.ganaderia.animal.entity.Animal;
import com.ganaderia.animal.service.AnimalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
@CrossOrigin(origins = "*")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @PostMapping("crear-animal")
    public ResponseEntity<ApiResponse<AnimalResponse>> createAnimal(
            @Valid @RequestBody CreateAnimalRequest request,
            @RequestHeader(value = "X-Establishment-Id", required = false) Long establishmentId,
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {

        try {
            // Validar headers requeridos
            if (establishmentId == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Establishment ID is required"));
            }
            if (userId == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("User ID is required"));
            }


            AnimalResponse response = animalService.createAnimal(request, establishmentId, userId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(response, "Animal created successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to create animal: " + e.getMessage()));
        }
    }

    @GetMapping("obtener-animales")
    public ResponseEntity<ApiResponse<Page<AnimalResponse>>> getAllAnimals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestHeader(value = "X-Establishment-Id", required = false) Long establishmentId) {

        try {
            if (establishmentId == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Establishment ID is required"));
            }

            Sort sort = sortDir.equalsIgnoreCase("desc") ?
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);

            Page<AnimalResponse> animals = animalService.getAllAnimals(establishmentId, pageable);
            return ResponseEntity.ok(ApiResponse.success(animals, "Animals retrieved successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve animals: " + e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<AnimalResponse>>> searchAnimals(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String animalCode,
            @RequestParam(required = false) Animal.Gender gender,
            @RequestParam(required = false) Animal.AnimalStatus status,
            @RequestParam(required = false) Long breedId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestHeader(value = "X-Establishment-Id", required = false) Long establishmentId) {

        try {
            if (establishmentId == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Establishment ID is required"));
            }

            Sort sort = sortDir.equalsIgnoreCase("desc") ?
                    Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);

            Page<AnimalResponse> animals = animalService.searchAnimals(
                    establishmentId, name, animalCode, gender, status, breedId, pageable);
            return ResponseEntity.ok(ApiResponse.success(animals, "Animals found"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to search animals: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AnimalResponse>> getAnimalById(
            @PathVariable Long id,
            @RequestHeader(value = "X-Establishment-Id", required = false) Long establishmentId) {

        try {
            if (establishmentId == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Establishment ID is required"));
            }

            AnimalResponse animal = animalService.getAnimalById(id, establishmentId);
            return ResponseEntity.ok(ApiResponse.success(animal, "Animal retrieved successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve animal: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AnimalResponse>> updateAnimal(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAnimalRequest request,
            @RequestHeader(value = "X-Establishment-Id", required = false) Long establishmentId,
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {

        try {
            if (establishmentId == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Establishment ID is required"));
            }
            if (userId == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("User ID is required"));
            }

            AnimalResponse response = animalService.updateAnimal(id, request, establishmentId, userId);
            return ResponseEntity.ok(ApiResponse.success(response, "Animal updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to update animal: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteAnimal(
            @PathVariable Long id,
            @RequestHeader(value = "X-Establishment-Id", required = false) Long establishmentId,
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {

        try {
            if (establishmentId == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Establishment ID is required"));
            }
            if (userId == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("User ID is required"));
            }

            animalService.deleteAnimal(id, establishmentId, userId);
            return ResponseEntity.ok(ApiResponse.success("Animal deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to delete animal: " + e.getMessage()));
        }
    }

    @GetMapping("/gender/{gender}")
    public ResponseEntity<ApiResponse<List<AnimalResponse>>> getAnimalsByGender(
            @PathVariable Animal.Gender gender,
            @RequestHeader(value = "X-Establishment-Id", required = false) Long establishmentId) {

        try {
            if (establishmentId == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Establishment ID is required"));
            }

            List<AnimalResponse> animals = animalService.getAnimalsByGender(establishmentId, gender);
            return ResponseEntity.ok(ApiResponse.success(animals, "Animals retrieved by gender"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve animals: " + e.getMessage()));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<AnimalResponse>>> getAnimalsByStatus(
            @PathVariable Animal.AnimalStatus status,
            @RequestHeader(value = "X-Establishment-Id", required = false) Long establishmentId) {

        try {
            if (establishmentId == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Establishment ID is required"));
            }

            List<AnimalResponse> animals = animalService.getAnimalsByStatus(establishmentId, status);
            return ResponseEntity.ok(ApiResponse.success(animals, "Animals retrieved by status"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve animals: " + e.getMessage()));
        }
    }

    @GetMapping("/stats/count")
    public ResponseEntity<ApiResponse<Long>> getAnimalCount(
            @RequestHeader(value = "X-Establishment-Id", required = false) Long establishmentId) {

        try {
            if (establishmentId == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Establishment ID is required"));
            }

            long count = animalService.getAnimalCount(establishmentId);
            return ResponseEntity.ok(ApiResponse.success(count, "Animal count retrieved"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to get animal count: " + e.getMessage()));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("Animal Service is running", "Health check"));
    }
}