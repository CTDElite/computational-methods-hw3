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
}
