package org.simbir_soft.braim_challenge.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.simbir_soft.braim_challenge.domain.AnimalType;

@Data
public class AnimalTypeDto extends Dto<AnimalType> {
    @Override
    public AnimalType fromDto() {
        return new AnimalType(name);
    }

    @NotNull
    @NotBlank
    private String name;
}
