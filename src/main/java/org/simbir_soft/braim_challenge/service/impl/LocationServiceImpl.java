package org.simbir_soft.braim_challenge.service.impl;

import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.aspect.annotation.ExistingId;
import org.simbir_soft.braim_challenge.domain.Location;
import org.simbir_soft.braim_challenge.domain.dto.Dto;
import org.simbir_soft.braim_challenge.exception.DataConflictException;
import org.simbir_soft.braim_challenge.exception.DataInvalidException;
import org.simbir_soft.braim_challenge.exception.DataMissingException;
import org.simbir_soft.braim_challenge.repository.AnimalRepository;
import org.simbir_soft.braim_challenge.repository.LocationRepository;
import org.simbir_soft.braim_challenge.service.AnimalService;
import org.simbir_soft.braim_challenge.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    private final AnimalRepository animalRepository;

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
    @ExistingId(validator = LocationService.class)
    public Location update(Long id, Dto<Location> dto) {
        Location location = dto.fromDto();
        checkUnique(location);
        location.setId(id);
        return locationRepository.save(location);
    }

    @Override
    @ExistingId(validator = LocationService.class)
    public void delete(Long id) {
        try {
            locationRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new DataInvalidException();
        }
    }

    @Override
    public Optional<Location> findById(Long id) {
        return locationRepository.findById(id);
    }

    @Override
    public Iterable<Location> findAllById(Iterable<Long> ids) {
        Iterable<Location> locations = locationRepository.findAllById(ids);
        if (locations.spliterator().getExactSizeIfKnown() != ids.spliterator().getExactSizeIfKnown()) {
            throw new DataMissingException();
        }
        return locationRepository.findAllById(ids);
    }

    @Override
    public Iterable<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return locationRepository.existsById(id);
    }
}
