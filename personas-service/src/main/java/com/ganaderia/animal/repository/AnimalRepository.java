package com.ganaderia.animal.repository;

import com.ganaderia.animal.entity.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    // Buscar por establecimiento
    Page<Animal> findByEstablishmentIdAndActiveTrue(Long establishmentId, Pageable pageable);

    List<Animal> findByEstablishmentIdAndActiveTrue(Long establishmentId);

    // Buscar por código
    Optional<Animal> findByAnimalCodeAndActiveTrue(String animalCode);

    boolean existsByAnimalCodeAndActiveTrue(String animalCode);

    // Buscar por establecimiento y estado
    List<Animal> findByEstablishmentIdAndStatusAndActiveTrue(Long establishmentId, Animal.AnimalStatus status);

    // Buscar por establecimiento y género
    List<Animal> findByEstablishmentIdAndGenderAndActiveTrue(Long establishmentId, Animal.Gender gender);

    // Buscar por raza
    List<Animal> findByBreedIdAndActiveTrue(Long breedId);

    // Buscar por padre o madre
    List<Animal> findByMotherIdAndActiveTrue(Long motherId);
    List<Animal> findByFatherIdAndActiveTrue(Long fatherId);

    // Query personalizada para buscar con filtros
    @Query("SELECT a FROM Animal a WHERE " +
            "a.establishmentId = :establishmentId AND " +
            "a.active = true AND " +
            "(:name IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:animalCode IS NULL OR LOWER(a.animalCode) LIKE LOWER(CONCAT('%', :animalCode, '%'))) AND " +
            "(:gender IS NULL OR a.gender = :gender) AND " +
            "(:status IS NULL OR a.status = :status) AND " +
            "(:breedId IS NULL OR a.breed.id = :breedId)")
    Page<Animal> findAnimalsWithFilters(
            @Param("establishmentId") Long establishmentId,
            @Param("name") String name,
            @Param("animalCode") String animalCode,
            @Param("gender") Animal.Gender gender,
            @Param("status") Animal.AnimalStatus status,
            @Param("breedId") Long breedId,
            Pageable pageable);

    // Contar animales por establecimiento
    long countByEstablishmentIdAndActiveTrue(Long establishmentId);

    // Contar por género
    long countByEstablishmentIdAndGenderAndActiveTrue(Long establishmentId, Animal.Gender gender);
}