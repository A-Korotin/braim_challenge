package org.simbir_soft.braim_challenge.service.impl;

import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.OrderedLocation;
import org.simbir_soft.braim_challenge.repository.OrderedLocationRepository;
import org.simbir_soft.braim_challenge.service.OrderedLocationService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderedLocationServiceImpl implements OrderedLocationService {
    private final OrderedLocationRepository orderedLocationRepository;

    @Override
    public OrderedLocation save(OrderedLocation entity) {

        return orderedLocationRepository.save(entity);
    }

    @Override
    public Optional<OrderedLocation> findById(Long id) {
        return orderedLocationRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return orderedLocationRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        orderedLocationRepository.deleteById(id);
    }
}
