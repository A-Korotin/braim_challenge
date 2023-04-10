package org.simbir_soft.braim_challenge.repository;

import org.simbir_soft.braim_challenge.domain.OrderedLocation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedLocationRepository extends CrudRepository<OrderedLocation, Long> {
}
