package org.simbir_soft.braim_challenge.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditVisitedLocationDto {
    @NotNull
    @Min(value = 1)
    private Long visitedLocationPointId;

    @NotNull
    @Min(value = 1)
    private Long locationPointId;
}
