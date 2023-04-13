package org.simbir_soft.braim_challenge.service;

import org.simbir_soft.braim_challenge.domain.Location;

import java.math.BigDecimal;
import java.util.Optional;

public interface LocationService extends CrudService<Location, Long> {
    Optional<Location> findByLatitudeAndLongitude(BigDecimal latitude, BigDecimal longitude);
}
