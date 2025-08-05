package com.ganaderia.auth.repository;

import com.ganaderia.auth.entity.Establishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {

    List<Establishment> findByActiveTrue();

    boolean existsByName(String name);
}