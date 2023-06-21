package org.simbir_soft.braim_challenge.validation;

import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.Area;
import org.simbir_soft.braim_challenge.validation.internal.Polygon;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class CollisionValidator {

    public boolean isValid(Area area, Iterable<Area> areas) {
        Polygon target = new Polygon(area);

        List<Polygon> polygons = StreamSupport
                .stream(areas.spliterator(), false)
                .map(Polygon::new).toList();

        for(Polygon polygon: polygons) {
            if (target.hasIntersection(polygon)) {
                return false;
            }

            if (target.isInside(polygon) || polygon.isInside(target)) {
                return false;
            }
        }

        return true;
    }
}
