package org.simbir_soft.braim_challenge.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.dto.AnimalTypeDto;
import org.simbir_soft.braim_challenge.exception.DataMissingException;
import org.simbir_soft.braim_challenge.service.AnimalTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/animals/types")
public class AnimalTypeController {
    private final AnimalTypeService animalTypeService;

    @GetMapping("/{typeId}")
    public ResponseEntity<?> getType(@PathVariable @Min(value = 1) Long typeId) {
        return ResponseEntity.ok(animalTypeService.findById(typeId).orElseThrow(DataMissingException::new));
    }

    @PostMapping
    public ResponseEntity<?> createType(@Valid @RequestBody AnimalTypeDto animalTypeDto) {
        return ResponseEntity.ok(animalTypeService.save(animalTypeDto));
    }

    @PutMapping("/{typeId}")
    public ResponseEntity<?> editType(@PathVariable @Min(value = 1) Long typeId,
                                      @Valid @RequestBody AnimalTypeDto animalTypeDto) {
        return ResponseEntity.ok(animalTypeService.update(typeId, animalTypeDto));
    }

    @DeleteMapping("/{typeId}")
    public ResponseEntity<?> deleteType(@PathVariable @Min(value = 1) Long typeId) {
        // TODO: 04.02.2023 animal owns this type 
        animalTypeService.delete(typeId);
        return ResponseEntity.ok().build();
    }
}
