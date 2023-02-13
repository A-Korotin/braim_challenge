package org.simbir_soft.braim_challenge.service;

import org.simbir_soft.braim_challenge.domain.TimedLocation;

public interface AnimalLocationService {
    TimedLocation addLocationById(Long animalId, Long locationId);
    TimedLocation editLocationById(Long animalId, Long oldId, Long newId);
    void deleteLocationById(Long animalId, Long locationId);
}
