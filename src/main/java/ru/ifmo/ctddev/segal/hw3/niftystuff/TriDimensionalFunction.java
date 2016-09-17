package ru.ifmo.ctddev.segal.hw3.niftystuff;

/**
 * @author Ignat Loskutov.
 */
@FunctionalInterface
public interface TriDimensionalFunction {
    Point apply(Point p);
}
