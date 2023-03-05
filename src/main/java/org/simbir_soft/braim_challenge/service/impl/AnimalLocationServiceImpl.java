package org.simbir_soft.braim_challenge.service.impl;

import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.Animal;
import org.simbir_soft.braim_challenge.domain.Location;
import org.simbir_soft.braim_challenge.domain.TimedLocation;
import org.simbir_soft.braim_challenge.exception.DataInvalidException;
import org.simbir_soft.braim_challenge.exception.DataMissingException;
import org.simbir_soft.braim_challenge.repository.AnimalRepository;
import org.simbir_soft.braim_challenge.repository.TimedLocationRepository;
import org.simbir_soft.braim_challenge.service.AnimalLocationService;
import org.simbir_soft.braim_challenge.service.AnimalService;
import org.simbir_soft.braim_challenge.service.LocationService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalLocationServiceImpl implements AnimalLocationService {

    private final AnimalService animalService;
    private final LocationService locationService;

    private final TimedLocationRepository timedLocationRepository;
    private final AnimalRepository animalRepository;


    private Location loadLocation(Long locationId) {
        return locationService.findById(locationId).orElseThrow(DataMissingException::new);
    }

    private void checkAdd(Animal animal, Long newLocationId) {
        if (animal.getLifeStatus().equals(Animal.LifeStatus.DEAD)) {
            throw new DataInvalidException();
        }

        int size = animal.getVisitedLocations().size();
        if ( size == 0 && animal.getChippingLocation().getId().equals(newLocationId)) {
            throw new DataInvalidException();
        }

        if (size > 0 && animal.getVisitedLocations().get(size - 1).getLocation().getId().equals(newLocationId)) {
            throw new DataInvalidException();
        }
    }

    @Override
    public TimedLocation addLocationById(Long animalId, Long locationId) {
        Animal animal = animalService.findByIdOrThrowException(animalId);
        checkAdd(animal, locationId);
        Location location = loadLocation(locationId);
        TimedLocation timedLocation = new TimedLocation(location, animal, ZonedDateTime.now());
        timedLocation = timedLocationRepository.save(timedLocation);
        return timedLocation;
    }

    private void checkUpdate(Animal animal, Location oldLocation, Location newLocation) {
        if (animal.getVisitedLocations().get(0).getLocation().equals(oldLocation) &&
            animal.getChippingLocation().equals(newLocation)) {
            throw new DataInvalidException();
        }

        int oldIndex = animal.getVisitedLocations().stream()
                .map(TimedLocation::getLocation).toList().indexOf(oldLocation);

        Location before = null;
        Location after = null;
        try {
            before = animal.getVisitedLocations().get(oldIndex - 1).getLocation();
        } catch (IndexOutOfBoundsException ignored) {}
        try {
            after = animal.getVisitedLocations().get(oldIndex + 1).getLocation();
        } catch (IndexOutOfBoundsException ignored) {}

        if (newLocation.equals(before) || newLocation.equals(after)) {
            throw new DataInvalidException();
        }
    }

    @Override
    public TimedLocation editLocationById(Long animalId, Long oldId, Long newId) {
        if (oldId.equals(newId)) {
            throw new DataInvalidException();
        }

        Animal animal = animalService.findByIdOrThrowException(animalId);
        Location newLocation = loadLocation(newId);

        TimedLocation timedLocation = animal.getVisitedLocations().stream()
                .filter(l -> l.getLocation().getId().equals(oldId))
                .findAny()
                .orElseThrow(DataMissingException::new);

        checkUpdate(animal, timedLocation.getLocation(), newLocation);
        timedLocation.setLocation(newLocation);
        return timedLocationRepository.save(timedLocation);
    }

    @Override
    public void deleteLocationById(Long animalId, Long locationId) {
        Animal animal = animalService.findByIdOrThrowException(animalId);
        TimedLocation location = animal.getVisitedLocations().stream()
                .filter(l -> l.getLocation().getId().equals(locationId))
                .findAny()
                .orElseThrow(DataMissingException::new);
        animal.setVisitedLocations(animal.getVisitedLocations()
                .stream()
                .filter(l -> !l.getLocation().getId().equals(locationId))
                .collect(Collectors.toList()));
        timedLocationRepository.deleteById(location.getId());
        animalRepository.save(animal);
    }
}
