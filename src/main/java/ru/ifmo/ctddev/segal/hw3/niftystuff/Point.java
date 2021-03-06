package ru.ifmo.ctddev.segal.hw3.niftystuff;

import java.util.Locale;

/**
 * @author Ignat Loskutov.
 */
public class Point {
    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point plus(Point p) {
        return new Point(x + p.x, y + p.y, z + p.z);
    }

    public Point times(double k) {
        return new Point(k * x, k * y, k * z);
    }

    @Override
    public String toString() {
        return String.format(Locale.CANADA, "[%f, %f, %f]", x, y, z);
    }

    public final double x, y, z;
}
