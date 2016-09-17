package ru.ifmo.ctddev.segal.hw3

import ru.ifmo.ctddev.segal.hw3.niftystuff.Point

/**
 * Implements Runge—Kutta fourth-order method.
 * @author Ignat Loskutov
 */

class RungeKuttaMethod(sigma: Double, b: Double, r: Double,
                       ta: Double, tb: Double, dt: Double,
                       x0: Double, y0: Double, z0: Double)
: MethodForLorenzSystem(sigma, b, r, ta, tb, dt, x0, y0, z0) {
    override fun call(): Result {
        val builder = ResultBuilder()
        val f = { p: Point -> Point(firstEquation(p.x, p.y, p.z),
                                    secondEquation(p.x, p.y, p.z),
                                    thirdEquation(p.x, p.y, p.z)) }
        var p = Point(x0, y0, z0)
        var t = ta
        while (t <= tb) {
            builder.append(p.x, p.y, p.z, t)

            val k1 = f(p)
            val k2 = f(p + k1 * (dt / 2))
            val k3 = f(p + k2 * (dt / 2))
            val k4 = f(p + k3 * dt)

            p += (k1 + k2 * 2.0 + k3 * 2.0 + k4) * (dt / 6)
            t += dt
        }
        return builder.toResult()
    }
}


