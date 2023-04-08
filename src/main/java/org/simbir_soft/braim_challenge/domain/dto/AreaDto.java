package org.simbir_soft.braim_challenge.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.simbir_soft.braim_challenge.domain.Area;
import org.simbir_soft.braim_challenge.validation.annotation.NonIntersectingPolygon;

import java.util.List;

@Data
@Builder
public class AreaDto implements Dto<Area> {

    @NotBlank
    @NotNull
    private String name;

    @NotNull
    @NonIntersectingPolygon
    private List<LocationDto> areaPoints;

    @Override
    public Area fromDto() {
        return null;
    }
}
