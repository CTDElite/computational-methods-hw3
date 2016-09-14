package ru.ifmo.ctddev.segal.hw3;

public abstract class MethodForLorentzSystem {

    protected final double sigma;
    protected final double b;
    protected final double r;

    protected final double ta;
    protected final double tb;
    protected final double dt;

    protected final double x0;
    protected final double y0;
    protected final double z0;

    /**
     * Instantiates method for solving of Lorentz system.
     *
     * @param sigma parameter for Lorentz system (10 by default)
     * @param b parameter for Lorentz system (8/3 by default)
     * @param r parameter for Lorentz system (assume it's in [0; 30] bounds)
     * @param ta left bound for temperature
     * @param tb right bound for temperature
     * @param dt temperature augmentation
     * @param x0 initial value of x
     * @param y0 initial value of y
     * @param z0 initial value of z
     */
    public MethodForLorentzSystem(double sigma, double b, double r,
                                  double ta, double tb, double dt,
                                  double x0, double y0, double z0) {
        this.sigma = sigma;
        this.b = b;
        this.r = r;
        this.ta = ta;
        this.tb = tb;
        this.dt = dt;
        this.x0 = x0;
        this.y0 = y0;
        this.z0 = z0;
    }

    /**
     * Represents the right part of the first equation in Lorentz system.
     *
     * dx/dt = -σ * x + σ * y
     *
     * @param x parameter of equation
     * @param y parameter of equation
     * @param z parameter of equation
     * @return value of the right part of the first equation
     */
    protected double firstEquation(double x, double y, double z) {
        return -sigma * x + sigma * y;
    }

    /**
     * Represents the right part of the second equation in Lorentz system.
     *
     * dy/dt = -x * z + r * x - y
     *
     * @param x parameter of equation
     * @param y parameter of equation
     * @param z parameter of equation
     * @return value of the right part of the second equation
     */
    protected double secondEquation(double x, double y, double z) {
        return -x * z + r * x - y;
    }

    /**
     * Represents the right part of the third equation in Lorentz system.
     *
     * dz/dt = x * y - b * z
     *
     * @param x parameter of equation
     * @param y parameter of equation
     * @param z parameter of equation
     * @return value of the right part of the third equation
     */
    protected double thirdEquation(double x, double y, double z) {
        return x * y - b * z;
    }

    /**
     * Runs this method.
     *
     * Your code should like something like:
     *
     * ```
     * List<Double> x, y, z, t; // Should be instantiated of
     * for (double i = ta; i <= tb; i += dt) {
     *     // Some math
     * }
     * return new Result(x, y, z, t);
     * ```
     *
     * Result of this method will be used for plotting.
     *
     * WARNING! Make sure result you return contains initial values.
     * WARNING! Make sure you return doubles, which are not NaNs and
     *          not infs. Throw IllegalStateException in that case or
     *          something similar to that (checkout
     *          {@link Utils#doublesAreCorrect}).
     *
     * Also please make sure your implementation really works. At least
     * write one-two tests for basic behaviours.
     *
     * @return result with x(t), y(t), z(t) and t(x, y, z) values.
     */
    public abstract Result run();
}
