package org.simbir_soft.braim_challenge.validation.internal;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Line {
    private static final double THRESHOLD = 10e-8;
    private Point p1, p2;

    public boolean pointOnLine(Point p) {
        // уравнение прямой, проходящей через 2 точки
        return (p1.getY() - p2.getY()) * p.getX() + (p2.getX() - p1.getX()) * p.getY()
                - (p1.getX() * p2.getY() - p2.getX() * p1.getY()) < THRESHOLD;
    }
}
