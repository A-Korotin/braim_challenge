package org.simbir_soft.braim_challenge.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditAnimalTypeDto {
    @NotNull
    @Min(value = 1)
    private Long oldTypeId;

    @NotNull
    @Min(value = 1)
    private Long newTypeId;
}
