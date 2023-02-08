package org.simbir_soft.braim_challenge.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.Animal;
import org.simbir_soft.braim_challenge.domain.dto.AnimalDto;
import org.simbir_soft.braim_challenge.domain.dto.EditAnimalTypeDto;
import org.simbir_soft.braim_challenge.exception.DataMissingException;
import org.simbir_soft.braim_challenge.service.AnimalService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
        return ResponseEntity.ok(animalService.findById(animalId).orElseThrow(DataMissingException::new));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchAnimals(@RequestParam LocalDateTime startDateTime,
                                           @RequestParam LocalDateTime endDateTime,
                                           @RequestParam @Min(value = 1) Long chipperId,
                                           @RequestParam @Min(value = 1) Long chippingLocationId,
                                           @RequestParam Animal.LifeStatus lifeStatus,
                                           @RequestParam Animal.Gender gender,
                                           @RequestParam(defaultValue = "0") @Min(value = 0) Long from,
                                           @RequestParam(defaultValue = "10") @Min(value = 1) Long size) {
        Iterable<Animal> iterable = animalService.findAll();
        System.out.println(iterable);
        List<Animal> filtered = StreamSupport.stream(iterable.spliterator(), false)
                .filter(a -> a.getChippingDateTime().isAfter(startDateTime))
                .filter(a -> a.getChippingDateTime().isBefore(endDateTime))
                .filter(a -> a.getChipper().getId().equals(chipperId))
                .filter(a -> a.getChippingLocation().getId().equals(chippingLocationId))
                .filter(a -> a.getLifeStatus().equals(lifeStatus))
                .filter(a -> a.getGender().equals(gender))
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
                                          @Valid @RequestBody AnimalDto animalDto) {

        return ResponseEntity.ok(animalService.update(animalId, animalDto));
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
