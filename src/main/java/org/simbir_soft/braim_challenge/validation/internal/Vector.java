package org.simbir_soft.braim_challenge.validation.internal;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Vector {
    private double x,y;

    public Vector (Point p1, Point p2) {
        x = p2.getX() - p1.getX();
        y = p2.getY() - p1.getY();
    }

    // pseudo dot product - псевдоскалярное произведение векторов
    public double pdp(Vector other) {
        return x * other.y - other.x * y;
    }
}
