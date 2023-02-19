package org.simbir_soft.braim_challenge.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.Animal;
import org.simbir_soft.braim_challenge.domain.TimedLocation;
import org.simbir_soft.braim_challenge.domain.dto.EditVisitedLocationDto;
import org.simbir_soft.braim_challenge.exception.DataMissingException;
import org.simbir_soft.braim_challenge.service.AnimalLocationService;
import org.simbir_soft.braim_challenge.service.AnimalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/animals/{animalId}/locations")
@RequiredArgsConstructor
public class AnimalLocationController {

    private final AnimalLocationService service;
    private final AnimalService animalService;

    @GetMapping
    public ResponseEntity<?> searchLocations(@PathVariable @Min(value = 1) Long animalId,
                                             @RequestParam(required = false) ZonedDateTime startDateTime,
                                             @RequestParam(required = false) ZonedDateTime endDateTime,
                                             @RequestParam(defaultValue = "0") @Min(value = 0) Long from,
                                             @RequestParam(defaultValue = "10") @Min(value = 1) Long size) {
        Animal animal = animalService.findById(animalId).orElseThrow(DataMissingException::new);
        List<TimedLocation> filtered = animal.getVisitedLocations().stream()
                .filter(l -> startDateTime == null || l.getVisitTime().isAfter(startDateTime))
                .filter(l -> endDateTime == null || l.getVisitTime().isBefore(endDateTime))
                .sorted(Comparator.comparing(TimedLocation::getVisitTime))
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
        return ResponseEntity.ok(filtered);
    }

    @PostMapping("/{pointId}")
    public ResponseEntity<?> addLocation(@PathVariable @Min(value = 1) Long animalId,
                                         @PathVariable @Min(value = 1) Long pointId) {
        return ResponseEntity.ok(service.addLocationById(animalId, pointId));
    }

    @PutMapping
    public ResponseEntity<?> editLocation(@PathVariable @Min(value = 1) Long animalId,
                                          @Valid @RequestBody EditVisitedLocationDto dto) {
        return ResponseEntity.ok(service
                .editLocationById(animalId, dto.getVisitedLocationPointId(), dto.getLocationPointId()));
    }

    @DeleteMapping("/{visitedPointId}")
    public ResponseEntity<?> deleteLocation(@PathVariable @Min(value = 1) Long animalId,
                                            @PathVariable @Min(value = 1) Long visitedPointId) {
        service.deleteLocationById(animalId, visitedPointId);
        return ResponseEntity.ok().build();
    }
}
