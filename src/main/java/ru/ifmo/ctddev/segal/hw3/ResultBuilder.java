package ru.ifmo.ctddev.segal.hw3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Aleksei Latyshev
 */

public class ResultBuilder {
    private final List<Double> xs, ys, zs, ts;

    public ResultBuilder() {
        xs = new ArrayList<>();
        ys = new ArrayList<>();
        zs = new ArrayList<>();
        ts = new ArrayList<>();
    }

    public ResultBuilder append(double x, double y, double z, double t) {
        if (!Utils.doublesAreCorrect(x, y, z, t)) {
            throw new IllegalArgumentException("Append incorrect doubles in ResultBuilder");
        }
        xs.add(x);
        ys.add(y);
        zs.add(z);
        ts.add(t);
        return this;
    }

    public Result toResult() {
        List<Double> uxs = Collections.unmodifiableList(new ArrayList<>(xs));
        List<Double> uys = Collections.unmodifiableList(new ArrayList<>(ys));
        List<Double> uzs = Collections.unmodifiableList(new ArrayList<>(zs));
        List<Double> uts = Collections.unmodifiableList(new ArrayList<>(ts));
        return new Result(uxs, uys, uzs, uts);
    }
}