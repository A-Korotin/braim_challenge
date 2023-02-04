package org.simbir_soft.braim_challenge.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.dto.AnimalDto;
import org.simbir_soft.braim_challenge.service.AnimalService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/animals")
@Validated
public class AnimalController {
    private final AnimalService animalService;

    @PostMapping
    public ResponseEntity<?> createAnimal(@Valid @RequestBody AnimalDto animalDto) {
        System.out.println(animalDto);
        return ResponseEntity.ok(animalService.save(animalDto));
    }
}
