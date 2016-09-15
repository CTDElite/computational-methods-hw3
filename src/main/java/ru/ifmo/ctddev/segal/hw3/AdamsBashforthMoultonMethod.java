package ru.ifmo.ctddev.segal.hw3;

public class AdamsBashforthMoultonMethod extends MethodForLorenzSystem {
    /**
     * Instantiates Adams-Bashforth-Moulton (Adams-Moulton as
     * predictor and Adams-Bashforth as corrector) method.
     *
     * WARNING! I can be wrong here (I mean in description above or in
     * link below), so before implementing investigate it yourself.
     *
     * See: https://en.wikipedia.org/wiki/Linear_multistep_method#Adams.E2.80.93Moulton_methods.
     */
    public AdamsBashforthMoultonMethod(double sigma, double b, double r, double ta, double tb, double dt, double x0, double y0, double z0) {
        super(sigma, b, r, ta, tb, dt, x0, y0, z0);
    }

    public Result call() {
        return null; // TODO: implement by @alex-700
    }
}
