package org.simbir_soft.braim_challenge.repository;

import org.simbir_soft.braim_challenge.domain.AnimalType;
import org.springframework.data.repository.CrudRepository;

public interface AnimalTypeRepository extends CrudRepository<AnimalType, Long> {
    boolean existsByName(String name);
}
