package org.simbir_soft.braim_challenge.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.simbir_soft.braim_challenge.domain.Area;
import org.simbir_soft.braim_challenge.domain.OrderedLocation;
import org.simbir_soft.braim_challenge.validation.annotation.NonIntersectingPolygon;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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
        AtomicLong order = new AtomicLong();

        return Area.builder().name(name)
                .areaPoints(areaPoints.stream().map(dto -> new OrderedLocation(order.getAndIncrement(), dto.fromDto())).toList())
                .build();
    }
}
