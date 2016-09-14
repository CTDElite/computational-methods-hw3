package ru.ifmo.ctddev.segal.hw3;

public class ImplicitEulerMethod extends MethodForLorentzSystem {
    /**
     * Instantiates implicit Euler method.
     *
     * See: https://en.wikipedia.org/wiki/Backward_Euler_method.
     */
    public ImplicitEulerMethod(double sigma, double b, double r, double ta, double tb, double dt, double x0, double y0, double z0) {
        super(sigma, b, r, ta, tb, dt, x0, y0, z0);
    }

    public Result run() {
        return null; // TODO: implement by @dimatomp
    }
}
