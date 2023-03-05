package org.simbir_soft.braim_challenge.service.impl;

import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.Animal;
import org.simbir_soft.braim_challenge.domain.AnimalType;
import org.simbir_soft.braim_challenge.domain.TimedLocation;
import org.simbir_soft.braim_challenge.domain.dto.Dto;
import org.simbir_soft.braim_challenge.exception.DataConflictException;
import org.simbir_soft.braim_challenge.exception.DataInvalidException;
import org.simbir_soft.braim_challenge.exception.DataMissingException;
import org.simbir_soft.braim_challenge.repository.AnimalRepository;
import org.simbir_soft.braim_challenge.service.AccountService;
import org.simbir_soft.braim_challenge.service.AnimalService;
import org.simbir_soft.braim_challenge.service.AnimalTypeService;
import org.simbir_soft.braim_challenge.service.LocationService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final AccountService accountService;
    private final LocationService locationService;
    private final AnimalTypeService animalTypeService;

    private void fillDummyFields(Animal animal) {
        if (animal.getAnimalTypes().stream().distinct().count() != animal.getAnimalTypes().size()) { // contains duplicates
            throw new DataConflictException();
        }

        List<AnimalType> validTypes = new ArrayList<>(animal.getAnimalTypes().size());
        animalTypeService
                .findAllById(animal.getAnimalTypes().stream()
                        .map(AnimalType::getId)
                        .collect(Collectors.toList()))
                .forEach(validTypes::add);
        animal.setAnimalTypes(validTypes);

        Long chipperId = animal.getChipper().getId();
        animal.setChipper(accountService.findById(chipperId).orElseThrow(DataMissingException::new));

        Long chippingLocationId = animal.getChippingLocation().getId();
        animal.setChippingLocation(locationService.findById(chippingLocationId).orElseThrow(DataMissingException::new));
    }

    private Animal loadAnimal(Long id) {
        Animal animal = animalRepository.findById(id).orElseThrow(DataMissingException::new);
        animal.getVisitedLocations().sort(Comparator.comparing(TimedLocation::getVisitTime));
        return animal;
    }

    private AnimalType loadType(Long id) {
        return animalTypeService.findById(id).orElseThrow(DataMissingException::new);
    }

    private void checkUpdate(Animal oldAnimal, Animal newAnimal) {
        if (oldAnimal.isDead() && newAnimal.isAlive()) {
            throw new DataInvalidException();
        }
        if (oldAnimal.getVisitedLocations().size() > 0 &&
            oldAnimal.getVisitedLocations().get(0).getLocation().equals(newAnimal.getChippingLocation())) {
            throw new DataInvalidException();
        }
    }

    private void preformUpdate(Animal oldAnimal, Animal newAnimal) {
        newAnimal.setId(oldAnimal.getId());
        newAnimal.setVisitedLocations(oldAnimal.getVisitedLocations());
        if (newAnimal.isDead()) {
            newAnimal.setDeathDateTime(ZonedDateTime.now());
        }
        newAnimal.setChippingDateTime(oldAnimal.getChippingDateTime());
        newAnimal.setAnimalTypes(oldAnimal.getAnimalTypes());
    }

    @Override
    public Animal save(Dto<Animal> dto) {
        Animal animal = dto.fromDto();
        fillDummyFields(animal);
        animal.setLifeStatus(Animal.LifeStatus.ALIVE);
        animal.setChippingDateTime(ZonedDateTime.now());
        return animalRepository.save(animal);
    }

    @Override
    public Animal update(Long id, Dto<Animal> dto) {
        Animal oldAnimal = loadAnimal(id);
        Animal newAnimal = dto.fromDto();
        fillDummyFields(newAnimal);
        checkUpdate(oldAnimal, newAnimal);
        preformUpdate(oldAnimal, newAnimal);

        return animalRepository.save(newAnimal);
    }

    @Override
    public void delete(Long id) {
        Animal animal = loadAnimal(id);
        if (animal.leftChippingLocation()) {
            throw new DataInvalidException();
        }

        animalRepository.deleteById(id);
    }

    @Override
    public Optional<Animal> findById(Long id) {
        return animalRepository.findById(id);
    }

    @Override
    public Animal findByIdOrThrowException(Long id) {
        return loadAnimal(id);
    }

    @Override
    public Iterable<Animal> findAllById(Iterable<Long> ids) {
        Iterable<Animal> animals = animalRepository.findAllById(ids);
        if (animals.spliterator().getExactSizeIfKnown() != ids.spliterator().getExactSizeIfKnown()) {
            throw new DataMissingException();
        }
        return animals;
    }

    @Override
    public Iterable<Animal> findAll() {
        return animalRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return animalRepository.existsById(id);
    }


    @Override
    public Animal addTypeById(Long animalId, Long typeId) {
        Animal animal = loadAnimal(animalId);
        AnimalType animalType = loadType(typeId);

        if (animal.hasType(typeId)) {
            throw new DataConflictException();
        }

        animal.getAnimalTypes().add(animalType);
        return animalRepository.save(animal);
    }

    private void performReplace(Animal animal, Long oldTypeId, Long newTypeId) {
        List<AnimalType> replacedTypeList = animal.getAnimalTypes().stream()
                .map(t -> t.getId().equals(oldTypeId)? loadType(newTypeId): t)
                .collect(Collectors.toList());

        animal.setAnimalTypes(replacedTypeList);
    }

    @Override
    public Animal editTypeById(Long animalId, Long oldTypeId, Long newTypeId) {
        Animal animal = loadAnimal(animalId);
        if (!animal.hasType(oldTypeId)) {
            throw new DataMissingException();
        }
        if (animal.hasType(newTypeId)) {
            throw new DataConflictException();
        }

        performReplace(animal, oldTypeId, newTypeId);

        return animalRepository.save(animal);
    }

    @Override
    public Animal deleteTypeById(Long animalId, Long typeId) {
        Animal animal = loadAnimal(animalId);

        if (!animalTypeService.existsById(typeId) ||
                !animal.hasType(typeId)) {
            throw new DataMissingException();
        }

        if (!animal.removeType(typeId)) {
            throw new DataInvalidException();
        }

        return animalRepository.save(animal);
    }
}
