package org.simbir_soft.braim_challenge.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.dto.LocationDto;
import org.simbir_soft.braim_challenge.exception.DataMissingException;
import org.simbir_soft.braim_challenge.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/locations")
public class LocationController {
    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<?> createLocation(@Valid @RequestBody LocationDto locationDto) {
        return ResponseEntity.status(201).body(locationService.save(locationDto));
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<?> getLocation(@PathVariable @Min(value = 1) Long locationId) {
        return ResponseEntity.ok(locationService.findById(locationId).orElseThrow(DataMissingException::new));
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<?> updateLocation(@PathVariable @Min(value = 1) Long locationId,
                                            @Valid @RequestBody LocationDto locationDto) {
        return ResponseEntity.ok(locationService.update(locationId, locationDto));
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<?> deleteLocation(@PathVariable @Min(value = 1) Long locationId) {
        locationService.delete(locationId);
        return ResponseEntity.ok().build();
    }
}
