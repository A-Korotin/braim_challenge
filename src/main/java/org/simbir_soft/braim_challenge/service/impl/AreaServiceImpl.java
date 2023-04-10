package org.simbir_soft.braim_challenge.service.impl;

import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.aspect.annotation.ExistingId;
import org.simbir_soft.braim_challenge.domain.Area;
import org.simbir_soft.braim_challenge.domain.OrderedLocation;
import org.simbir_soft.braim_challenge.domain.dto.Dto;
import org.simbir_soft.braim_challenge.repository.AreaRepository;
import org.simbir_soft.braim_challenge.service.AreaService;
import org.simbir_soft.braim_challenge.service.OrderedLocationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {
    private final AreaRepository repository;
    private final OrderedLocationService orderedLocationService;

    @Override
    public Area save(Dto<Area> dto) {
        Area area = dto.fromDto();
        List<OrderedLocation> saved = area.getAreaPoints().stream().map(orderedLocationService::save).toList();
        area.setAreaPoints(saved);
        return repository.save(area);
    }

    @Override
    @ExistingId(validator = AreaService.class)
    public Area update(Long id, Dto<Area> dto) {
        return null;
    }

    @Override
    @ExistingId(validator = AreaService.class)
    public void delete(Long id) {

    }

    @Override
    public Optional<Area> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<Area> findAllById(Iterable<Long> ids) {
        return null;
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
