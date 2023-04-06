package org.simbir_soft.braim_challenge.domain.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.simbir_soft.braim_challenge.domain.Account;
import org.simbir_soft.braim_challenge.domain.Animal;
import org.simbir_soft.braim_challenge.domain.Location;

import java.math.BigDecimal;

@Data
public class EditAnimalDto implements Dto<Animal> {

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

    private Animal.LifeStatus lifeStatus;

    @NotNull
    @Min(value = 1)
    private Long chipperId;

    @NotNull
    @Min(value = 1)
    private Long chippingLocationId;

    @Override
    public Animal fromDto() {
        Account chipper = new Account();
        chipper.setId(chipperId);

        Location chippingLocation = new Location();
        chippingLocation.setId(chippingLocationId);
        return Animal.builder()
                .weight(weight)
                .height(height)
                .length(length)
                .gender(gender)
                .lifeStatus(lifeStatus)
                .chipper(chipper)
                .chippingLocation(chippingLocation)
                .build();
    }
}
