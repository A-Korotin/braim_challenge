package org.simbir_soft.braim_challenge.service;

import org.simbir_soft.braim_challenge.domain.Animal;
import org.simbir_soft.braim_challenge.domain.TimedLocation;

public interface AnimalService extends CrudService<Animal, Long> {
    Animal findByIdOrThrowException(Long id);

    Animal addTypeById(Long animalId, Long typeId);
    Animal editTypeById(Long animalId, Long oldTypeId, Long newTypeId);
    Animal deleteTypeById(Long animalId, Long typeId);
}
