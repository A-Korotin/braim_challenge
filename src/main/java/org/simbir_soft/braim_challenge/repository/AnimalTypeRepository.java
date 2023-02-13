package org.simbir_soft.braim_challenge.repository;

import org.simbir_soft.braim_challenge.domain.AnimalType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AnimalTypeRepository extends CrudRepository<AnimalType, Long> {
    boolean existsByName(String name);

    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) > 0 FROM animal_type_relation WHERE animal_type_id = ?")
    boolean animalWithTypeExists(Long id);
}
