package ru.ifmo.ctddev.segal.hw3;

import ru.ifmo.ctddev.segal.hw3.niftystuff.Point;
import ru.ifmo.ctddev.segal.hw3.niftystuff.TriDimensionalFunction;

/**
 * @author Ignat Loskutov
 */
public class RungeKuttaMethod extends MethodForLorenzSystem {
    /**
     * Instantiates Runge—Kutta fourth-order method.
     *
     * See: https://en.wikipedia.org/wiki/Runge%E2%80%93Kutta_methods.
     */
    public RungeKuttaMethod(double sigma, double b, double r, double ta, double tb, double dt, double x0, double y0, double z0) {
        super(sigma, b, r, ta, tb, dt, x0, y0, z0);
    }

    @Override
    public Result call() {
        ResultBuilder builder = new ResultBuilder();

        TriDimensionalFunction f = p -> new Point(firstEquation(p.x, p.y, p.z),
                                                  secondEquation(p.x, p.y, p.z),
                                                  thirdEquation(p.x, p.y, p.z));

        Point p = new Point(x0, y0, z0);
        for (double t = ta; t <= tb; t += dt) {
            builder.append(p.x, p.y, p.z, t);

            Point k1 = f.apply(p);
            Point k2 = f.apply(p.add(k1.mul(dt/2)));  // fuck Java :/
            Point k3 = f.apply(p.add(k2.mul(dt/2)));
            Point k4 = f.apply(p.add(k3.mul(dt)));

            p = p.add(k1.add(k2.mul(2)).add(k3.mul(2)).add(k4).mul(dt/6));
        }
        return builder.toResult();
    }
}
