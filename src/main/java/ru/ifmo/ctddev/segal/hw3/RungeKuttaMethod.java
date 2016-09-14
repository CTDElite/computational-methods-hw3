package ru.ifmo.ctddev.segal.hw3;

public class RungeKuttaMethod extends MethodForLorentzSystem {
    /**
     * Instantiates Runge-Kutta fourth-order (but you can write general if you want) method.
     *
     * See: https://en.wikipedia.org/wiki/Runge%E2%80%93Kutta_methods.
     */
    public RungeKuttaMethod(double sigma, double b, double r, double ta, double tb, double dt, double x0, double y0, double z0) {
        super(sigma, b, r, ta, tb, dt, x0, y0, z0);
    }

    public Result run() {
        return null; // TODO: implement by @loskutov
    }
}
