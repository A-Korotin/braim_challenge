package org.simbir_soft.braim_challenge.repository;

import org.simbir_soft.braim_challenge.domain.TimedLocation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimedLocationRepository extends CrudRepository<TimedLocation, Long> {
}
