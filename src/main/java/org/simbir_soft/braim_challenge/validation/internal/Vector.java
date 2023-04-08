package org.simbir_soft.braim_challenge.validation.internal;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Vector {
    private double x,y;

    public Vector (Point p1, Point p2) {
        x = p2.getX() - p1.getX();
        y = p2.getY() - p1.getX();
    }

    // pseudo dot product - псевдоскалярное произведение векторов
    public double pdt(Vector other) {
        return x * other.y - other.x * y;
    }
}
