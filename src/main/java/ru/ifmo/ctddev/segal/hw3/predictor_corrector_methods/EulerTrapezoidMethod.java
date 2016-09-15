package ru.ifmo.ctddev.segal.hw3.predictor_corrector_methods;

import ru.ifmo.ctddev.segal.hw3.MethodForLorenzSystem;
import ru.ifmo.ctddev.segal.hw3.Result;
import ru.ifmo.ctddev.segal.hw3.ResultBuilder;

public class EulerTrapezoidMethod extends MethodForLorenzSystem {

    private static final double HALF = 0.5;

    public EulerTrapezoidMethod(double sigma, double b, double r, double ta, double tb, double dt, double x0, double y0, double z0) {
        super(sigma, b, r, ta, tb, dt, x0, y0, z0);
    }

    @Override
    public Result call() {
        ResultBuilder resultBuilder = new ResultBuilder();
        double curX = x0;
        double curY = y0;
        double curZ = z0;
        double nextX = 0, nextY = 0, nextZ = 0;
        for (double curT = ta; curT <= tb; curT += dt, curX = nextX, curY = nextY, curZ = nextZ) {
            resultBuilder.append(curX, curY, curZ, curT);
            // predictor (Euler method)
            double midX = curX + firstEquation(curX, curY, curZ) * dt;
            double midY = curY + secondEquation(curX, curY, curZ) * dt;
            double midZ = curZ + thirdEquation(curX, curY, curZ) * dt;
            for (int it = 0; it < 2; it++, midX = nextX, midY = nextY, midZ = nextZ) {
                // corrector (Trapezoid method)
                nextX = curX + HALF * dt * (firstEquation(curX, curY, curZ) + firstEquation(midX, midY, midZ));
                nextY = curY + HALF * dt * (secondEquation(curX, curY, curZ) + secondEquation(midX, midY, midZ));
                nextZ = curZ + HALF * dt * (thirdEquation(curX, curY, curZ) + thirdEquation(midX, midY, midZ));
            }
        }
        return resultBuilder.toResult();
    }
}