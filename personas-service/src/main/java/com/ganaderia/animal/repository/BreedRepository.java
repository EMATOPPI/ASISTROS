package com.ganaderia.animal.repository;

import com.ganaderia.animal.entity.Breed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long> {

    List<Breed> findByActiveTrue();

    Optional<Breed> findByNameAndActiveTrue(String name);

    boolean existsByNameAndActiveTrue(String name);

    List<Breed> findByAnimalTypeAndActiveTrue(Breed.AnimalType animalType);
}