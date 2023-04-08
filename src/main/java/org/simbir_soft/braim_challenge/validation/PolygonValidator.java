package org.simbir_soft.braim_challenge.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.simbir_soft.braim_challenge.domain.dto.LocationDto;
import org.simbir_soft.braim_challenge.validation.annotation.NonIntersectingPolygon;
import org.simbir_soft.braim_challenge.validation.internal.*;

import java.util.ArrayList;
import java.util.List;

public class PolygonValidator implements ConstraintValidator<NonIntersectingPolygon, List<LocationDto>> {

    @Override
    public void initialize(NonIntersectingPolygon constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(List<LocationDto> value, ConstraintValidatorContext context) {
        if (value.size() < 3) {
            return false;
        }

        // есть повторяющиеся точки
        if (value.stream().distinct().count() != value.size()) {
            return false;
        }

        List<Point> points = value.stream()
                .map(dto -> new Point(dto.getLatitude().doubleValue(), dto.getLongitude().doubleValue()))
                .toList();

        Line directionLine = new Line(points.get(0), points.get(1));

        // все точки лежат на одной прямой
        if (points.stream().allMatch(directionLine::pointOnLine)) {
            return false;
        }

        List<Segment> segments = new ArrayList<>();

        for (int i = 0; i < points.size(); ++i) {
            Segment segment = new Segment(points.get(i), points.get((i + 1) % points.size()));
            segments.add(segment);
        }

        for (int i = 0; i < segments.size(); ++i) {
            Segment segment1 = segments.get(i);
            for (int j = i + 2; j < segments.size(); ++j) {
                Segment segment2 = segments.get(j);

                // есть пересечение не соседних сторон многоугольника
                if (segment1.getInterceptionPoint(segment2) != null) {
                    return false;
                }
            }
        }

        // пересечений не соседних сторон не выявлено
        return true;

    }
}
