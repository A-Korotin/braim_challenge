package org.simbir_soft.braim_challenge.repository;

import org.simbir_soft.braim_challenge.domain.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
    boolean existsByLatitudeAndLongitude(BigDecimal latitude, BigDecimal longitude);
    Optional<Location> findByLatitudeAndLongitude(BigDecimal latitude, BigDecimal longitude);
}
