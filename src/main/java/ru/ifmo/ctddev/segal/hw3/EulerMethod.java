package ru.ifmo.ctddev.segal.hw3;

public class EulerMethod extends MethodForLorenzSystem {
    /**
     * Instantiates Euler method.
     *
     * See: https://en.wikipedia.org/wiki/Euler_method.
     */
    public EulerMethod(double sigma, double b, double r, double ta, double tb, double dt, double x0, double y0, double z0) {
        super(sigma, b, r, ta, tb, dt, x0, y0, z0);
    }

    @Override
    public Result call() {
        ResultBuilder builder = new ResultBuilder();
        double curX = x0;
        double curY = y0;
        double curZ = z0;
        double nextX;
        double nextY;
        double nextZ;
        for (double t = ta; t <= tb; t += dt) {
            builder.append(curX, curY, curZ, t);
            nextX = firstEquation(curX, curY, curZ) * dt + curX;
            nextY = secondEquation(curX, curY, curZ) * dt + curY;
            nextZ = thirdEquation(curX, curY, curZ) * dt + curZ;
            curX = nextX;
            curY = nextY;
            curZ = nextZ;
        }
        return builder.toResult();
    }
}
