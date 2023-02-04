package org.simbir_soft.braim_challenge.domain.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.simbir_soft.braim_challenge.domain.Account;
import org.simbir_soft.braim_challenge.domain.Animal;
import org.simbir_soft.braim_challenge.domain.AnimalType;
import org.simbir_soft.braim_challenge.domain.Location;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AnimalDto extends Dto<Animal> {

    @NotEmpty
    private List<@Min(value = 1) Long> animalTypes;

    @NotNull
    @DecimalMin(value = "0.0000000001")
    private BigDecimal weight;

    @NotNull
    @DecimalMin(value = "0.0000000001")
    private BigDecimal height;

    @NotNull
    @DecimalMin(value = "0.0000000001")
    private BigDecimal length;

    @NotNull
    private Animal.Gender gender;

    @Min(value = 1)
    private Long chipperId;

    @Min(value = 1)
    private long chippingLocationId;

    @Override
    public Animal fromDto() {
        Account chipper = new Account();
        chipper.setId(chipperId);

        Location chippingLocation = new Location();
        chippingLocation.setId(chippingLocationId);
        return Animal.builder()
                .animalTypes(animalTypes.stream().map(id -> {
                    AnimalType type = new AnimalType();
                    type.setId(id);
                    return type;
                }).collect(Collectors.toList()))
                .weight(weight)
                .height(height)
                .length(length)
                .gender(gender)
                .chipper(chipper)
                .chippingLocation(chippingLocation)
                .build();
    }
}
