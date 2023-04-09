package org.simbir_soft.braim_challenge.validation.internal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Segment {
    private static final double THRESHOLD = 10e-8;

    // точки, ограничивающие отрезок
    private Point start, end;

    private static boolean pdpIsDifferentBySign(Segment main, Segment other) {
        Vector mainVec = new Vector(main.start, main.end);
        Vector aux1 = new Vector(main.start, other.end);
        Vector aux2 = new Vector(main.start, other.start);
        double pdp1 = mainVec.pdp(aux1);
        double pdp2 = mainVec.pdp(aux2);

        if (Math.abs(pdp1) <= THRESHOLD || Math.abs(pdp2) <= THRESHOLD) {
            return true;
        }

        pdp1 /= Math.abs(pdp1);
        pdp2 /= Math.abs(pdp2);

        // псевдоскалярное произведение разных знаков
        return pdp1 + pdp2 <= THRESHOLD;
    }

    private boolean rangeIntersection(double sFirst, double eFirst, double sSecond, double eSecond) {
        if (sFirst > eFirst) {
            double tmp = sFirst;
            sFirst = eFirst;
            eFirst = tmp;
        }
        if (sSecond > eSecond) {
            double tmp = sSecond;
            sSecond = eSecond;
            eSecond = tmp;
        }

        return Math.max(sFirst, sSecond) <= Math.min(eFirst, eSecond);
    }
    private boolean projectionIntersection(Segment other) {
        return rangeIntersection(start.getX(), end.getX(), other.start.getX(), other.end.getX()) &&
                rangeIntersection(start.getY(), end.getY(), other.start.getY(), other.end.getY());
    }
    public boolean hasIntersection(Segment other) {
        if (!projectionIntersection(other)) {
            return false;
        }

        // в одну сторону
        boolean oneWay = pdpIsDifferentBySign(this, other);
        // в обратную сторону
        boolean otherWay = pdpIsDifferentBySign(other, this);
        return oneWay && otherWay;
    }
}
