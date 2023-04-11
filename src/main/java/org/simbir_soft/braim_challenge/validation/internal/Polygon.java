package org.simbir_soft.braim_challenge.validation.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.simbir_soft.braim_challenge.domain.Area;

import java.util.ArrayList;
import java.util.List;

@Data
public class Polygon {
    private final List<Point> points;

    private final List<Segment> segments;

    public Polygon(Area area) {
        points = area.getAreaPoints().stream().map(l ->
                new Point(l.getLatitude().doubleValue(),
                          l.getLongitude().doubleValue())).toList();

        segments = new ArrayList<>();
        for (int i = 0; i < points.size(); ++i) {
            Segment segment = new Segment(points.get(i), points.get((i + 1) % points.size()));
            segments.add(segment);
        }
    }

    public boolean hasIntersection(Polygon other) {
        for(Segment s1: segments) {
            for (Segment s2: other.segments) {
                if (s1.hasIntersection(s2)) {
                    return true;
                }
            }
        }
        return false;
    }

    // алгоритм трассировки лучей
    public boolean isInside(Polygon other) {
        return points.stream().allMatch(p -> p.isInsidePolygon(other));
    }
}
