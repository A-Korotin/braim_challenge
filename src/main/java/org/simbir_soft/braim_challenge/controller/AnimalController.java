package org.simbir_soft.braim_challenge.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.Animal;
import org.simbir_soft.braim_challenge.domain.BaseEntity;
import org.simbir_soft.braim_challenge.domain.dto.AnimalDto;
import org.simbir_soft.braim_challenge.domain.dto.EditAnimalDto;
import org.simbir_soft.braim_challenge.domain.dto.EditAnimalTypeDto;
import org.simbir_soft.braim_challenge.exception.DataMissingException;
import org.simbir_soft.braim_challenge.service.AnimalService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequiredArgsConstructor
@RequestMapping("/animals")
@Validated
public class AnimalController {
    private final AnimalService animalService;

    @GetMapping("/{animalId}")
    public ResponseEntity<?> getAnimal(@PathVariable @Min(value = 1) Long animalId) {
        return ResponseEntity.ok(animalService.findByIdOrThrowException(animalId));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchAnimals(@RequestParam(required = false) LocalDateTime startDateTime,
                                           @RequestParam(required = false) LocalDateTime endDateTime,
                                           @RequestParam(required = false) @Min(value = 1) Long chipperId,
                                           @RequestParam(required = false) @Min(value = 1) Long chippingLocationId,
                                           @RequestParam(required = false) Animal.LifeStatus lifeStatus,
                                           @RequestParam(required = false) Animal.Gender gender,
                                           @RequestParam(defaultValue = "0") @Min(value = 0) Long from,
                                           @RequestParam(defaultValue = "10") @Min(value = 1) Long size) {
        Iterable<Animal> iterable = animalService.findAll();
        List<Animal> filtered = StreamSupport.stream(iterable.spliterator(), false)
                .filter(a -> startDateTime == null || a.getChippingDateTime().isAfter(startDateTime))
                .filter(a -> endDateTime == null || a.getChippingDateTime().isBefore(endDateTime))
                .filter(a -> chipperId == null || a.getChipper().getId().equals(chipperId))
                .filter(a -> chippingLocationId == null || a.getChippingLocation().getId().equals(chippingLocationId))
                .filter(a -> lifeStatus == null || a.getLifeStatus().equals(lifeStatus))
                .filter(a -> gender == null || a.getGender().equals(gender))
                .sorted(Comparator.comparingLong(BaseEntity::getId))
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
        return ResponseEntity.ok(filtered);
    }

    @PostMapping
    public ResponseEntity<?> createAnimal(@Valid @RequestBody AnimalDto animalDto) {
        return ResponseEntity.ok(animalService.save(animalDto));
    }

    @PutMapping("/{animalId}")
    public ResponseEntity<?> updateAnimal(@PathVariable @Min(value = 1) Long animalId,
                                          @Valid @RequestBody EditAnimalDto animalDto) {

        return ResponseEntity.ok(animalService.update(animalId, animalDto));
    }

    @DeleteMapping("/{animalId}")
    public ResponseEntity<?> deleteAnimal(@PathVariable @Min(value = 1) Long animalId) {
        animalService.delete(animalId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{animalId}/types/{typeId}")
    public ResponseEntity<?> addAnimalType(@PathVariable @Min(value = 1) Long animalId,
                                           @PathVariable @Min(value = 1) Long typeId) {
        return ResponseEntity.ok(animalService.addTypeById(animalId, typeId));
    }

    @PutMapping("/{animalId}/types")
    public ResponseEntity<?> editAnimalType(@PathVariable @Min(value = 1) Long animalId,
                                            @Valid @RequestBody EditAnimalTypeDto dto) {
        return ResponseEntity.ok(animalService.editTypeById(animalId, dto.getOldTypeId(), dto.getNewTypeId()));
    }

    @DeleteMapping("/{animalId}/types/{typeId}")
    public ResponseEntity<?> deleteAnimalType(@PathVariable @Min(value = 1) Long animalId,
                                              @PathVariable @Min(value = 1) Long typeId) {
        return ResponseEntity.ok(animalService.deleteTypeById(animalId, typeId));
    }

}
