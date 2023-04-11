package org.simbir_soft.braim_challenge.service.impl;

import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.aspect.annotation.ExistingId;
import org.simbir_soft.braim_challenge.domain.Area;
import org.simbir_soft.braim_challenge.domain.OrderedLocation;
import org.simbir_soft.braim_challenge.domain.dto.Dto;
import org.simbir_soft.braim_challenge.exception.DataInvalidException;
import org.simbir_soft.braim_challenge.exception.DataMissingException;
import org.simbir_soft.braim_challenge.repository.AreaRepository;
import org.simbir_soft.braim_challenge.service.AreaService;
import org.simbir_soft.braim_challenge.service.OrderedLocationService;
import org.simbir_soft.braim_challenge.validation.CollisionValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {
    private final AreaRepository repository;
    private final OrderedLocationService orderedLocationService;
    private final CollisionValidator validator;

    private Area saveArea(Area area) {
        List<OrderedLocation> saved = area.getAreaPoints().stream().map(orderedLocationService::save).toList();
        area = repository.save(area);
        area.setAreaPoints(saved);
        return area;

    }
    @Override
    public Area save(Dto<Area> dto) {
        Area area = dto.fromDto();
        if (!validator.isValid(area, findAll())) {
            throw new DataInvalidException();
        }

        return saveArea(area);
    }

    @Override
    @ExistingId(validator = AreaService.class)
    public Area update(Long id, Dto<Area> dto) {
        Area area = dto.fromDto();
        area.setId(id);

        List<Area> areas = StreamSupport.stream(findAll().spliterator(), false)
                .filter(a -> !a.getId().equals(id))
                .toList();

        if (!validator.isValid(area, areas)) {
            throw new DataInvalidException();
        }

        return saveArea(area);
    }

    @Override
    @ExistingId(validator = AreaService.class)
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Area> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<Area> findAllById(Iterable<Long> ids) {
        Iterable<Area> found = repository.findAllById(ids);
        if (found.spliterator().getExactSizeIfKnown() != ids.spliterator().getExactSizeIfKnown()) {
            throw new DataMissingException();
        }
        return found;
    }

    @Override
    public Iterable<Area> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
