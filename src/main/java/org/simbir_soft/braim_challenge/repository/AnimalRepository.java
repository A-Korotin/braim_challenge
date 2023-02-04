package org.simbir_soft.braim_challenge.repository;

import org.simbir_soft.braim_challenge.domain.Animal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends CrudRepository<Animal, Long> {
}
