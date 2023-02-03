package org.simbir_soft.braim_challenge.service.impl;

import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.Location;
import org.simbir_soft.braim_challenge.domain.dto.Dto;
import org.simbir_soft.braim_challenge.exception.DataConflictException;
import org.simbir_soft.braim_challenge.repository.LocationRepository;
import org.simbir_soft.braim_challenge.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    private void checkUnique(Location location) {
        if(locationRepository.existsByLatitudeAndLongitude(location.getLatitude(), location.getLongitude())) {
            throw new DataConflictException();
        }
    }

    @Override
    public Location save(Dto<Location> dto) {
        Location location = dto.fromDto();
        checkUnique(location);

        return locationRepository.save(location);
    }

    @Override
    public Location update(Long id, Dto<Location> dto) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public Optional<Location> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Location> findAll() {
        return null;
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }
}
