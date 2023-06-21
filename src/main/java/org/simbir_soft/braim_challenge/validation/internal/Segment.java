package org.simbir_soft.braim_challenge.validation.internal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Segment {
    private static final double THRESHOLD = 10e-8;

    // точки, ограничивающие отрезок
    private Point start, end;

    private static boolean pdpIsDifferentBySign(Segment main, Segment other, boolean countOn) {
        Vector mainVec = new Vector(main.start, main.end);
        Vector aux1 = new Vector(main.start, other.end);
        Vector aux2 = new Vector(main.start, other.start);
        double pdp1 = mainVec.pdp(aux1);
        double pdp2 = mainVec.pdp(aux2);

        if (Math.abs(pdp1) <= THRESHOLD || Math.abs(pdp2) <= THRESHOLD) {
            return countOn;
        }

        pdp1 /= Math.abs(pdp1);
        pdp2 /= Math.abs(pdp2);

        // псевдоскалярное произведение разных знаков
        return Math.abs(pdp1 + pdp2) <= THRESHOLD;
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
        return hasIntersection(other, false);
    }

    public boolean hasIntersection(Segment other, boolean countOn) {
        if (!projectionIntersection(other)) {
            return false;
        }

        // в одну сторону
        boolean oneWay = pdpIsDifferentBySign(this, other, countOn);
        // в обратную сторону
        boolean otherWay = pdpIsDifferentBySign(other, this, countOn);
        return oneWay && otherWay;
    }

    public boolean contains(Point other) {
        Line line = new Line(start, end);

        // точка лежит на направляющей прямой и её координаты находятся в пределах заданного отрезка
        return line.pointOnLine(other) &&
                Math.min(start.getX(), end.getX()) <= other.getX() &&
                Math.max(start.getX(), end.getX()) >= other.getX() &&
                Math.min(start.getY(), end.getY()) <= other.getY() &&
                Math.max(start.getY(), end.getY()) >= other.getY();
    }
}
