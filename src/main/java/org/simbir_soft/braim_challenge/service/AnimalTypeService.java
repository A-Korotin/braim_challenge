package org.simbir_soft.braim_challenge.service;

import org.simbir_soft.braim_challenge.domain.AnimalType;

public interface AnimalTypeService extends CrudService<AnimalType, Long> {
    boolean animalWithTypeExists(Long typeId);
}
