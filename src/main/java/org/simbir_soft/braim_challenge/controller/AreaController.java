package org.simbir_soft.braim_challenge.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.dto.AreaDto;
import org.simbir_soft.braim_challenge.exception.DataMissingException;
import org.simbir_soft.braim_challenge.service.AreaService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
public class AreaController {

    private final AreaService areaService;

    @PostMapping("/areas")
    public ResponseEntity<?> createArea(@Valid @RequestBody AreaDto dto) {
        return ResponseEntity.status(201).body(areaService.save(dto));
    }

    @GetMapping("/areas/{areaId}")
    public ResponseEntity<?> getArea(@PathVariable @Min(1) Long areaId) {
        return ResponseEntity.ok(areaService.findById(areaId).orElseThrow(DataMissingException::new));
    }
}
