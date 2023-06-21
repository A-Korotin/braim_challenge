package org.simbir_soft.braim_challenge.domain.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.simbir_soft.braim_challenge.domain.Location;

import java.math.BigDecimal;

@Data
public class LocationDto implements Dto<Location> {
    @Override
    public Location fromDto() {
        return Location.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
    @NotNull
    @DecimalMin("-90")
    @DecimalMax("90")
    private BigDecimal latitude;

    @NotNull
    @DecimalMin("-180")
    @DecimalMax("180")
    private BigDecimal longitude;
}
