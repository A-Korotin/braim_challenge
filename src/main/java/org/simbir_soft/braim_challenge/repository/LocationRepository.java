package org.simbir_soft.braim_challenge.repository;

import org.simbir_soft.braim_challenge.domain.Location;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;

public interface LocationRepository extends CrudRepository<Location, Long> {
    boolean existsByLatitudeAndLongitude(BigDecimal latitude, BigDecimal longitude);
}
