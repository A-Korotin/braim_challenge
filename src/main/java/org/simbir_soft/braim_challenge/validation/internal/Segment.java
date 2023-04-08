package org.simbir_soft.braim_challenge.validation.internal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Segment {
    private static final double THRESHOLD = 10e-8;

    // точки, ограничивающие отрезок
    private Point start, end;


}
