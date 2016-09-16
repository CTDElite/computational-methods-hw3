package ru.ifmo.ctddev.segal.hw3.predictor_corrector_methods;

import ru.ifmo.ctddev.segal.hw3.MethodForLorenzSystem;
import ru.ifmo.ctddev.segal.hw3.Result;
import ru.ifmo.ctddev.segal.hw3.ResultBuilder;
import ru.ifmo.ctddev.segal.hw3.Utils;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * @author Aleksei Latyshev
 */
public class AdamsBashforthMoultonMethod extends MethodForLorenzSystem {
    public enum Steps {
        TWO, FOUR
    }
    private final Supplier<Result> function;
    private static final double HALF = 0.5;

    /**
     * Instantiates Adams-Bashforth-Moulton (Adams-Moulton as
     * predictor and Adams-Bashforth as corrector) method.
     *
     * WARNING! I can be wrong here (I mean in description above or in
     * link below), so before implementing investigate it yourself.
     *
     * See: https://en.wikipedia.org/wiki/Linear_multistep_method#Adams.E2.80.93Moulton_methods.
     * See: http://demonstrations.wolfram.com/TwoStepAndFourStepAdamsPredictorCorrectorMethod/
     */
    public AdamsBashforthMoultonMethod(double sigma, double b, double r, double ta, double tb, double dt, double x0, double y0, double z0, Steps steps) {
        super(sigma, b, r, ta, tb, dt, x0, y0, z0);
        switch (steps) {
            case TWO:
                function = this::supplierForTwoSteps;
                break;
            case FOUR:
                function = this::supplierForFourSteps;
                break;
            default:
                throw new IllegalArgumentException("fuck java");
        }
    }

    public Result call() {
        return function.get();
    }

    private Result supplierForTwoSteps() {
        ResultBuilder resultBuilder = new ResultBuilder();
        double curX = x0;
        double curY = y0;
        double curZ = z0;
        double prevX = curX;
        double prevY = curY;
        double prevZ = curZ;
        double nextX, nextY, nextZ;
        for (double curT = ta; curT <= tb; curT += dt,
                prevX = curX, prevY = curY, prevZ = curZ,
                curX = nextX, curY = nextY, curZ = nextZ) {
            resultBuilder.append(curX, curY, curZ, curT);
            // predictor (Two-step Adams-Bashfort)
            double midX = curX + HALF * dt * (3 * firstEquation(curX, curY, curZ) - firstEquation(prevX, prevY, prevZ));
            double midY = curY + HALF * dt * (3 * secondEquation(curX, curY, curZ) - secondEquation(prevX, prevY, prevZ));
            double midZ = curZ + HALF * dt * (3 * thirdEquation(curX, curY, curZ) - thirdEquation(prevX, prevY, prevZ));
            // corrector (Two-step Adams-Moulton)
            nextX = curX + HALF * dt * (firstEquation(midX, midY, midZ) + firstEquation(curX, curY, curZ));
            nextY = curY + HALF * dt * (secondEquation(midX, midY, midZ) + secondEquation(curX, curY, curZ));
            nextZ = curZ + HALF * dt * (thirdEquation(midX, midY, midZ) + thirdEquation(curX, curY, curZ));
        }
        return resultBuilder.toResult();
    }

    private Result supplierForFourSteps() {
        ResultBuilder resultBuilder = new ResultBuilder();

        final int four = 4;
        final int[] predictorCoefficients = new int[] {55, -59, 37, -9};
        final int[] correctorCoefficients = new int[] {9, 19, -5, 1};

        double[] curX = new double[four];
        Arrays.fill(curX, x0);
        double[] curY = new double[four];
        Arrays.fill(curY, y0);
        double[] curZ = new double[four];
        Arrays.fill(curZ, z0);

        for (double curT = ta; curT <= tb; curT += dt) {
            resultBuilder.append(curX[0], curY[0], curZ[0], curT);

            // predictor (Four-step Adams-Bashfort)
            double midX = 0, midY = 0, midZ = 0;
            for (int i = 0; i < predictorCoefficients.length; i++) {
                midX += firstEquation(curX[i], curY[i], curZ[i]) * predictorCoefficients[i];
                midY += secondEquation(curX[i], curY[i], curZ[i]) * predictorCoefficients[i];
                midZ += thirdEquation(curX[i], curY[i], curZ[i]) * predictorCoefficients[i];
            }
            midX = curX[0] + midX * dt / 24;
            midY = curY[0] + midY * dt / 24;
            midZ = curZ[0] + midZ * dt / 24;

            // corrector (Four-step Adams-Moulton)
            Utils.shiftDoubleArrayRight(curX, midX);
            Utils.shiftDoubleArrayRight(curY, midY);
            Utils.shiftDoubleArrayRight(curZ, midZ);
            double nextX = 0, nextY = 0, nextZ = 0;
            for (int i = 0; i < correctorCoefficients.length; i++) {
                nextX += firstEquation(curX[i], curY[i], curZ[i]) * correctorCoefficients[i];
                nextY += secondEquation(curX[i], curY[i], curZ[i]) * correctorCoefficients[i];
                nextZ += thirdEquation(curX[i], curY[i], curZ[i]) * correctorCoefficients[i];
            }
            nextX = curX[0] + nextX * dt / 24;
            nextY = curY[0] + nextY * dt / 24;
            nextZ = curZ[0] + nextZ * dt / 24;
            curX[0] = nextX;
            curY[0] = nextY;
            curZ[0] = nextZ;
        }
        return resultBuilder.toResult();
    }
}
