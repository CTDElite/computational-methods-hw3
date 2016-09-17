package ru.ifmo.ctddev.segal.hw3.niftystuff;

/**
 * @author Ignat Loskutov.
 */
public class Point {
    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point add(Point p) {
        return new Point(x + p.x, y + p.y, z + p.z);
    }

    public Point mul(double k) {
        return new Point(k * x, k * y, k * z);
    }

    public final double x, y, z;
}
