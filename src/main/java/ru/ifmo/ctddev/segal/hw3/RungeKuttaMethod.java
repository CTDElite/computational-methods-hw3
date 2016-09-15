package ru.ifmo.ctddev.segal.hw3;

import java.util.ArrayList;
import java.util.List;

public class RungeKuttaMethod extends MethodForLorenzSystem {
    /**
     * Instantiates Runge-Kutta fourth-order (but you can write general if you want) method.
     *
     * See: https://en.wikipedia.org/wiki/Runge%E2%80%93Kutta_methods.
     */
    public RungeKuttaMethod(double sigma, double b, double r, double ta, double tb, double dt, double x0, double y0, double z0) {
        super(sigma, b, r, ta, tb, dt, x0, y0, z0);
    }

    @Override
    public Result call() {
        int capacity = (int) ((tb - ta) / dt) + 1;
        List<Double> xs = new ArrayList<>(capacity);
        List<Double> ys = new ArrayList<>(capacity);
        List<Double> zs = new ArrayList<>(capacity);
        List<Double> ts = new ArrayList<>(capacity);
        double x = x0;
        double y = y0;
        double z = z0;
        for (double t = ta; t <= tb; t += dt) {
            xs.add(x);
            ys.add(y);
            zs.add(z);
            ts.add(t);
            double k11 = firstEquation(x, y, z);
            double k12 = secondEquation(x, y, z);
            double k13 = thirdEquation(x, y, z);

            double k21 = firstEquation(x + dt/2 * k11, y + dt/2 * k12, z + dt/2 * k13);
            double k22 = secondEquation(x + dt/2 * k11, y + dt/2 * k12, z + dt/2 * k13);
            double k23 = thirdEquation(x + dt/2 * k11, y + dt/2 * k12, z + dt/2 * k13);

            double k31 = firstEquation(x + dt/2 * k21, y + dt/2 * k22, z + dt/2 * k23);
            double k32 = secondEquation(x + dt/2 * k21, y + dt/2 * k22, z + dt/2 * k23);
            double k33 = thirdEquation(x + dt/2 * k21, y + dt/2 * k22, z + dt/2 * k23);

            double k41 = firstEquation(x + dt * k31, y + dt * k32, z + dt * k33);
            double k42 = secondEquation(x + dt * k31, y + dt * k32, z + dt * k33);
            double k43 = thirdEquation(x + dt * k31, y + dt * k32, z + dt * k33);

            x += dt/6 * firstEquation(k11 + 2*k21 + 2*k31 + k41, k12 + 2*k22 + 2*k32 + k42, k13 + 2*k23 + 2*k33 + k43);
            y += dt/6 * secondEquation(k11 + 2*k21 + 2*k31 + k41, k12 + 2*k22 + 2*k32 + k42, k13 + 2*k23 + 2*k33 + k43);
            z += dt/6 * thirdEquation(k11 + 2*k21 + 2*k31 + k41, k12 + 2*k22 + 2*k32 + k42, k13 + 2*k23 + 2*k33 + k43);
        }
        Result result = new Result(xs, ys, zs, ts);
        result.checkValues();
        return result;
    }
}
