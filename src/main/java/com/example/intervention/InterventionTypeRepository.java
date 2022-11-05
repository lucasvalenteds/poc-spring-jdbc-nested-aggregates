package com.example.intervention;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterventionTypeRepository extends CrudRepository<InterventionType, Long> {

    Optional<InterventionType> findByName(String name);
}
