package ru.ifmo.ctddev.segal.hw3;

import java.util.ArrayList;
import java.util.List;

public class Result {
    public final List<Double> x;
    public final List<Double> y;
    public final List<Double> z;
    public final List<Double> t;

    public Result() {
        x = new ArrayList<>();
        y = new ArrayList<>();
        z = new ArrayList<>();
        t = new ArrayList<>();
    }

    public Result(List<Double> x,
                  List<Double> y,
                  List<Double> z,
                  List<Double> t) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
    }

    public void addPoint(double x, double y, double z, double t) {
        this.x.add(x);
        this.y.add(y);
        this.z.add(z);
        this.t.add(t);
    }

    public int size() {
        return x.size();
    }

    private void check(List<Double> aa, String var) {
        for (Double a: aa) {
            if (!Utils.doublesAreCorrect(a)) throw new IllegalStateException(var + " is " + a);
        }
    }
    public void checkValues() {
        if (x.size() != y.size()) throw new IllegalStateException("size x != size y");
        if (y.size() != z.size()) throw new IllegalStateException("size y != size z");
        if (z.size() != t.size()) throw new IllegalStateException("size z != size t");
        check(x, "x");
        check(y, "y");
        check(z, "z");
        check(t, "t");
    }

    public String getPointAt(int pos) {
        return "[" + x.get(pos) + ", " + y.get(pos) + ", " + z.get(pos) + "t: " + t.get(pos) + "]";
    }
}
