package ru.ifmo.ctddev.segal.hw3

/**
 * Instantiates implicit Euler method.

 * See: https://en.wikipedia.org/wiki/Backward_Euler_method.
 */
class ImplicitEulerMethod (sigma: Double, b: Double, r: Double, ta: Double, tb: Double, dt: Double, x0: Double, y0: Double, z0: Double) : MethodForLorenzSystem(sigma, b, r, ta, tb, dt, x0, y0, z0) {
    private fun solveCubicEq(a: Double, b: Double, c: Double, d: Double, x: Double): Double {
        // Temporary decision to avoid any math here. It proves to be applicable (see assert in the end).
        var res = x
        fun value() = a * res * res * res + b * res * res + c * res + d
        for (i in 1..100) {
            val next = res - value() / (3 * a * res * res + 2 * b * res + c)
            if (next == res)
                break
            res = next
        }
        if (Math.abs(value()) > 1e-6)
            throw IllegalStateException("Found a root with value ${value()}")
        return res
    }

    override fun call(): Result {
        var xc = x0
        var yc = y0
        var zc = z0
        var t = ta
        val builder = ResultBuilder()
        while (t < tb) {
            builder.append(xc, yc, zc, t)
            val a = dt * sigma;
            val c = dt * r;
            val d = dt * b;
            val b = dt
            val alpha = zc / (1 + d)
            val beta = b / (1 + d);
            val gamma = yc / (1 + b)
            val delta = (-alpha * b + c) / (1 + b)
            val eta = -b * beta / (1 + b)
            xc = solveCubicEq(-eta * (1 + a), eta * xc, 1 + a - a * delta, -xc - a * gamma, xc)
            yc = (gamma + delta * xc) / (1 - eta * xc * xc)
            zc = alpha + beta * xc * yc
            t += dt
        }
        return builder.toResult()
    }
}
