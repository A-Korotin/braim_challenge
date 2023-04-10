package org.simbir_soft.braim_challenge.repository;

import org.simbir_soft.braim_challenge.domain.Area;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends CrudRepository<Area, Long> {
}
