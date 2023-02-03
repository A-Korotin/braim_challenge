package org.simbir_soft.braim_challenge.domain.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.simbir_soft.braim_challenge.domain.Location;

import java.math.BigDecimal;

@Data
public class LocationDto extends Dto<Location> {
    @Override
    public Location fromDto() {
        return Location.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
    @NotNull
    @DecimalMin("-89.9999999999")
    @DecimalMax("89.99999999999")
    private BigDecimal latitude;

    @NotNull
    @DecimalMin("-179.9999999999")
    @DecimalMax("179.99999999999")
    private BigDecimal longitude;
}
