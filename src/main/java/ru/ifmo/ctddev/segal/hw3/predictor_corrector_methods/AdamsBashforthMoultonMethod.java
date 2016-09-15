package ru.ifmo.ctddev.segal.hw3.predictor_corrector_methods;

import ru.ifmo.ctddev.segal.hw3.MethodForLorenzSystem;
import ru.ifmo.ctddev.segal.hw3.Result;
import ru.ifmo.ctddev.segal.hw3.ResultBuilder;

/**
 * @author Aleksei Latyshev
 */
public class AdamsBashforthMoultonMethod extends MethodForLorenzSystem {

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
    public AdamsBashforthMoultonMethod(double sigma, double b, double r, double ta, double tb, double dt, double x0, double y0, double z0) {
        super(sigma, b, r, ta, tb, dt, x0, y0, z0);
    }

    public Result call() {
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
}
