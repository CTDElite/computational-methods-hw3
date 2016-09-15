package ru.ifmo.ctddev.segal.hw3;

/**
 * @author Aleksei Latyshev
 */
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

    private static final double HALF = 0.5;

    public Result call() {
        // TODO: implement by @alex-700
        ResultBuilder resultBuilder = new ResultBuilder();
        double curX = x0;
        double curY = y0;
        double curZ = z0;
        double nextX, nextY, nextZ;
        for (double curT = ta; curT <= tb; curT += dt, curX = nextX, curY = nextY, curZ = nextZ) {
            resultBuilder.append(curX, curY, curZ, curT);
            // predictor (Euler method)
            double midX = curX + firstEquation(curX, curY, curZ) * dt;
            double midY = curY + secondEquation(curX, curY, curZ) * dt;
            double midZ = curZ + thirdEquation(curX, curY, curZ) * dt;
            // corrector (Trapezoid method)
            nextX = curX + HALF * dt * (firstEquation(curX, curY, curZ) + firstEquation(midX, midY, midZ));
            nextY = curY + HALF * dt * (secondEquation(curX, curY, curZ) + secondEquation(midX, midY, midZ));
            nextZ = curZ + HALF * dt * (thirdEquation(curX, curY, curZ) + thirdEquation(midX, midY, midZ));
        }
        return resultBuilder.toResult();
    }
}
