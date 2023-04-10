package org.simbir_soft.braim_challenge.service.impl;

import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.Location;
import org.simbir_soft.braim_challenge.domain.OrderedLocation;
import org.simbir_soft.braim_challenge.repository.LocationRepository;
import org.simbir_soft.braim_challenge.repository.OrderedLocationRepository;
import org.simbir_soft.braim_challenge.service.LocationService;
import org.simbir_soft.braim_challenge.service.OrderedLocationService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderedLocationServiceImpl implements OrderedLocationService {
    private final LocationRepository locationRepository;
    private final OrderedLocationRepository orderedLocationRepository;

    @Override
    public OrderedLocation save(OrderedLocation entity) {
        Location enitityLocation = entity.getLocation();
        Location location = locationRepository.
                findByLatitudeAndLongitude(enitityLocation.getLatitude(), enitityLocation.getLongitude())
                .orElse(locationRepository.save(enitityLocation));

        entity.setLocation(location);

        return orderedLocationRepository.save(entity);
    }

    @Override
    public Optional<OrderedLocation> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public void delete(Long id) {

    }
}