package org.simbir_soft.braim_challenge.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.Area;
import org.simbir_soft.braim_challenge.domain.dto.AreaDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class AreaController {

    @PostMapping("/areas")
    public ResponseEntity<?> createArea(@Valid @RequestBody AreaDto dto) {
        Area area = dto.fromDto();
        return ResponseEntity.ok(area);
    }
}
