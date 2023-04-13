package org.simbir_soft.braim_challenge.service;

import org.simbir_soft.braim_challenge.domain.OrderedLocation;

import java.util.Optional;

public interface OrderedLocationService {
    OrderedLocation save(OrderedLocation entity);
    Optional<OrderedLocation> findById(Long id);
    boolean existsById(Long id);

    void delete(Long id);
}
