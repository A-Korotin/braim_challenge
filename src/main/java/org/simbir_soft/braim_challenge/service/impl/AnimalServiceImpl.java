package org.simbir_soft.braim_challenge.service.impl;

import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.Animal;
import org.simbir_soft.braim_challenge.domain.AnimalType;
import org.simbir_soft.braim_challenge.domain.dto.Dto;
import org.simbir_soft.braim_challenge.exception.DataMissingException;
import org.simbir_soft.braim_challenge.repository.AnimalRepository;
import org.simbir_soft.braim_challenge.service.AccountService;
import org.simbir_soft.braim_challenge.service.AnimalService;
import org.simbir_soft.braim_challenge.service.AnimalTypeService;
import org.simbir_soft.braim_challenge.service.LocationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Override
    public Animal save(Dto<Animal> dto) {
        Animal animal = dto.fromDto();
        fillDummyFields(animal);
        animal.setLifeStatus(Animal.LifeStatus.ALIVE);
        animal.setChippingDateTime(LocalDateTime.now());
        return animalRepository.save(animal);
    }

    @Override
    public Animal update(Long aLong, Dto<Animal> dto) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public Optional<Animal> findById(Long id) {
        return animalRepository.findById(id);
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
        return null;
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }
}
