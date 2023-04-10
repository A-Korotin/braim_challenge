package org.simbir_soft.braim_challenge.service.impl;

import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.aspect.annotation.ExistingId;
import org.simbir_soft.braim_challenge.domain.AnimalType;
import org.simbir_soft.braim_challenge.domain.dto.Dto;
import org.simbir_soft.braim_challenge.exception.DataConflictException;
import org.simbir_soft.braim_challenge.exception.DataInvalidException;
import org.simbir_soft.braim_challenge.exception.DataMissingException;
import org.simbir_soft.braim_challenge.repository.AnimalTypeRepository;
import org.simbir_soft.braim_challenge.service.AnimalTypeService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalTypeServiceImpl implements AnimalTypeService {
    private final AnimalTypeRepository animalTypeRepository;

    private void checkUnique(AnimalType type) {
        if (animalTypeRepository.existsByType(type.getType())) {
            throw new DataConflictException();
        }
    }

    @Override
    public AnimalType save(Dto<AnimalType> dto) {
        AnimalType type = dto.fromDto();
        checkUnique(type);
        return animalTypeRepository.save(type);
    }

    @Override
    @ExistingId(validator = AnimalTypeService.class)
    public AnimalType update(Long id, Dto<AnimalType> dto) {
        AnimalType type = dto.fromDto();
        checkUnique(type);
        type.setId(id);
        return animalTypeRepository.save(type);
    }

    @Override
    @ExistingId(validator = AnimalTypeService.class)
    public void delete(Long id) {
        if (animalWithTypeExists(id)) {
            throw new DataInvalidException();
        }

        try {
            animalTypeRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new DataInvalidException();
        }
    }

    @Override
    public Optional<AnimalType> findById(Long id) {
        return animalTypeRepository.findById(id);
    }

    @Override
    public Iterable<AnimalType> findAllById(Iterable<Long> ids) {
        Iterable<AnimalType> types = animalTypeRepository.findAllById(ids);
        if (types.spliterator().getExactSizeIfKnown() != ids.spliterator().getExactSizeIfKnown()) {
            throw new DataMissingException();
        }
        return animalTypeRepository.findAllById(ids);
    }

    @Override
    public Iterable<AnimalType> findAll() {
        return animalTypeRepository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return animalTypeRepository.existsById(id);
    }

    @Override
    public boolean animalWithTypeExists(Long typeId) {
        return animalTypeRepository.animalWithTypeExists(typeId);
    }
}
