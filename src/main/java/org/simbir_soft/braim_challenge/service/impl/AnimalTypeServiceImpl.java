package org.simbir_soft.braim_challenge.service.impl;

import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.AnimalType;
import org.simbir_soft.braim_challenge.domain.dto.AnimalTypeDto;
import org.simbir_soft.braim_challenge.domain.dto.Dto;
import org.simbir_soft.braim_challenge.exception.DataConflictException;
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
        if (animalTypeRepository.existsByName(type.getName())) {
            throw new DataConflictException();
        }
    }

    private void checkId(Long id) {
        if (!animalTypeRepository.existsById(id)) {
            throw new DataMissingException();
        }
    }

    @Override
    public AnimalType save(Dto<AnimalType> dto) {
        AnimalType type = dto.fromDto();
        checkUnique(type);
        return animalTypeRepository.save(type);
    }

    @Override
    public AnimalType update(Long id, Dto<AnimalType> dto) {
        checkId(id);
        AnimalType type = dto.fromDto();
        checkUnique(type);
        type.setId(id);
        return animalTypeRepository.save(type);
    }

    @Override
    public void delete(Long id) {
        checkId(id);

        animalTypeRepository.deleteById(id);
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
}
