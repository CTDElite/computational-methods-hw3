package ru.ifmo.ctddev.segal.hw3;

import java.util.List;

public class Result {
    public final List<Double> x;
    public final List<Double> y;
    public final List<Double> z;
    public final List<Double> t;

    public Result(List<Double> x,
                  List<Double> y,
                  List<Double> z,
                  List<Double> t) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
    }
}
