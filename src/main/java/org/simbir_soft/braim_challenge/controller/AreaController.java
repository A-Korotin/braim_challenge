package org.simbir_soft.braim_challenge.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.dto.AreaDto;
import org.simbir_soft.braim_challenge.exception.DataMissingException;
import org.simbir_soft.braim_challenge.service.AnalyticsService;
import org.simbir_soft.braim_challenge.service.AreaService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Validated
public class AreaController {

    private final AreaService areaService;
    private final AnalyticsService analyticsService;

    @PostMapping("/areas")
    public ResponseEntity<?> createArea(@Valid @RequestBody AreaDto dto) {
        return ResponseEntity.status(201).body(areaService.save(dto));
    }

    @GetMapping("/areas/{areaId}")
    public ResponseEntity<?> getArea(@PathVariable @Min(1) Long areaId) {
        return ResponseEntity.ok(areaService.findById(areaId).orElseThrow(DataMissingException::new));
    }

    @PutMapping("/areas/{areaId}")
    public ResponseEntity<?> editArea(@PathVariable @Min(1) Long areaId,
                                      @RequestBody AreaDto dto) {
        return ResponseEntity.ok(areaService.update(areaId, dto));
    }

    @DeleteMapping("/areas/{areaId}")
    public ResponseEntity<?> deleteArea(@PathVariable @Min(1) Long areaId) {
        areaService.delete(areaId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/areas/{areaId}/analytics")
    public ResponseEntity<?> analytics(@PathVariable @Min(1) Long areaId,
                                       @RequestParam LocalDate startDate,
                                       @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(analyticsService.getAnalytics(areaId, startDate, endDate));
    }
}
