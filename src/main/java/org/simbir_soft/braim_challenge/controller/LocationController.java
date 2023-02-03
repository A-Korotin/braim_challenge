package org.simbir_soft.braim_challenge.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.dto.LocationDto;
import org.simbir_soft.braim_challenge.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/locations")
public class LocationController {
    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<?> createLocation(@Valid @RequestBody LocationDto locationDto) {
        return ResponseEntity.ok(locationService.save(locationDto));
    }
}
