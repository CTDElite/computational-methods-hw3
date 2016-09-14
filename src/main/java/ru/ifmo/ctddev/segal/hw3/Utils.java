package ru.ifmo.ctddev.segal.hw3;

public class Utils {

    /**
     * Checks if all doubles are not NaNs and not Infs.
     * @param doubles numbers to check
     * @return true, if everything is ok
     *         false, if there is at least one illegal number
     */
    public static boolean doublesAreCorrect(Double... doubles) {
        for (Double d : doubles) {
            if (d.isNaN() || d.isInfinite()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param value double to convert
     * @return converts double to string with 2 digits precision
     */
    public static String doublePrecision(double value) {
        return String.format("%.2f", value);
    }
}
