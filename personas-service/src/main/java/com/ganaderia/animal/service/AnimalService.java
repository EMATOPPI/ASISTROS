package com.ganaderia.animal.service;

import com.ganaderia.animal.dto.request.CreateAnimalRequest;
import com.ganaderia.animal.dto.request.UpdateAnimalRequest;
import com.ganaderia.animal.dto.response.AnimalResponse;
import com.ganaderia.animal.entity.Animal;
import com.ganaderia.animal.entity.Breed;
import com.ganaderia.animal.mapper.AnimalMapper;
import com.ganaderia.animal.repository.AnimalRepository;
import com.ganaderia.animal.repository.BreedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private BreedRepository breedRepository;

    @Autowired
    private AnimalMapper animalMapper;

    public AnimalResponse createAnimal(CreateAnimalRequest request, Long establishmentId, Long userId) {
        // Validar código único
        if (animalRepository.existsByAnimalCodeAndActiveTrue(request.getAnimalCode())) {
            throw new RuntimeException("Código de Animal ya existe: " + request.getAnimalCode());
        }

        // Validar raza si se proporciona
        Breed breed = null;
        if (request.getBreedId() != null) {
            breed = breedRepository.findById(request.getBreedId())
                    .orElseThrow(() -> new RuntimeException("Breed not found: " + request.getBreedId()));
        }

        // Validar padres si se proporcionan
        if (request.getMotherId() != null) {
            Animal mother = animalRepository.findById(request.getMotherId())
                    .orElseThrow(() -> new RuntimeException("Mother not found: " + request.getMotherId()));
            if (!mother.getGender().equals(Animal.Gender.FEMALE)) {
                throw new RuntimeException("Mother must be female");
            }
        }

        if (request.getFatherId() != null) {
            Animal father = animalRepository.findById(request.getFatherId())
                    .orElseThrow(() -> new RuntimeException("Father not found: " + request.getFatherId()));
            if (!father.getGender().equals(Animal.Gender.MALE)) {
                throw new RuntimeException("Father must be male");
            }
        }

        // Crear animal
        Animal animal = animalMapper.toEntity(request);
        animal.setEstablishmentId(establishmentId);
        animal.setBreed(breed);
        animal.setCreatedBy(userId);
        animal.setUpdatedBy(userId);

        Animal savedAnimal = animalRepository.save(animal);
        return animalMapper.toResponse(savedAnimal);
    }

    public AnimalResponse updateAnimal(Long id, UpdateAnimalRequest request, Long establishmentId, Long userId) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal not found: " + id));

        // Verificar que pertenece al establecimiento
        if (!animal.getEstablishmentId().equals(establishmentId)) {
            throw new RuntimeException("Animal does not belong to establishment");
        }

        // Validar raza si se proporciona
        if (request.getBreedId() != null) {
            Breed breed = breedRepository.findById(request.getBreedId())
                    .orElseThrow(() -> new RuntimeException("Breed not found: " + request.getBreedId()));
            animal.setBreed(breed);
        }

        // Actualizar campos
        animalMapper.updateEntityFromRequest(request, animal);
        animal.setUpdatedBy(userId);

        Animal savedAnimal = animalRepository.save(animal);
        return animalMapper.toResponse(savedAnimal);
    }

    @Transactional(readOnly = true)
    public AnimalResponse getAnimalById(Long id, Long establishmentId) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal not found: " + id));

        if (!animal.getEstablishmentId().equals(establishmentId)) {
            throw new RuntimeException("Animal does not belong to establishment");
        }

        return animalMapper.toResponse(animal);
    }

    @Transactional(readOnly = true)
    public Page<AnimalResponse> getAllAnimals(Long establishmentId, Pageable pageable) {
        Page<Animal> animals = animalRepository.findByEstablishmentIdAndActiveTrue(establishmentId, pageable);
        return animals.map(animalMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<AnimalResponse> searchAnimals(Long establishmentId, String name, String animalCode,
                                              Animal.Gender gender, Animal.AnimalStatus status,
                                              Long breedId, Pageable pageable) {
        Page<Animal> animals = animalRepository.findAnimalsWithFilters(
                establishmentId, name, animalCode, gender, status, breedId, pageable);
        return animals.map(animalMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<AnimalResponse> getAnimalsByGender(Long establishmentId, Animal.Gender gender) {
        List<Animal> animals = animalRepository.findByEstablishmentIdAndGenderAndActiveTrue(establishmentId, gender);
        return animals.stream()
                .map(animalMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AnimalResponse> getAnimalsByStatus(Long establishmentId, Animal.AnimalStatus status) {
        List<Animal> animals = animalRepository.findByEstablishmentIdAndStatusAndActiveTrue(establishmentId, status);
        return animals.stream()
                .map(animalMapper::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteAnimal(Long id, Long establishmentId, Long userId) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal not found: " + id));

        if (!animal.getEstablishmentId().equals(establishmentId)) {
            throw new RuntimeException("Animal does not belong to establishment");
        }

        // Soft delete
        animal.setActive(false);
        animal.setUpdatedBy(userId);
        animalRepository.save(animal);
    }

    @Transactional(readOnly = true)
    public long getAnimalCount(Long establishmentId) {
        return animalRepository.countByEstablishmentIdAndActiveTrue(establishmentId);
    }

    @Transactional(readOnly = true)
    public long getAnimalCountByGender(Long establishmentId, Animal.Gender gender) {
        return animalRepository.countByEstablishmentIdAndGenderAndActiveTrue(establishmentId, gender);
    }
}