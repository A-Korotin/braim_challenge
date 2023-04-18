package org.simbir_soft.braim_challenge.validation.internal;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Point {
    private double x, y;

    public boolean isInsidePolygon(Polygon polygon) {
        Point outerPoint = new Point(1000, 1000);
        int intersectionCount = 0;
        Segment s = new Segment(this, outerPoint);

        // точка лежит на стороне многоугольника
        if(polygon.getSegments().stream().anyMatch(seg -> seg.contains(this))) {
            return true;
        }

        for (Segment segment : polygon.getSegments()) {
            if (segment.hasIntersection(s,true)) {
                intersectionCount++;
            }
        }
        // точка лежит внутри
        return intersectionCount % 2 != 0;
    }
}
