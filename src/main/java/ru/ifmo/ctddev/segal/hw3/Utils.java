package ru.ifmo.ctddev.segal.hw3;

import java.util.Arrays;
import java.util.Locale;

public class Utils {

    /**
     * Checks if all doubles are not NaNs and not Infs.
     * @param doubles numbers to check
     * @return true, if everything is ok
     *         false, if there is at least one illegal number
     */
    public static boolean doublesAreCorrect(Double... doubles) {
        return Arrays.stream(doubles).noneMatch(d -> d.isNaN() || d.isInfinite());
    }

    /**
     * @param value double to convert
     * @return converts double to string with 2 digits precision
     */
    public static String doublePrecision(double value) {
        return String.format(Locale.US, "%.2f", value);
    }

    /**
     * Shift array to the right and set the first element with {@code toFirstPlace}
     *
     * @param elements -- array for shifting
     * @param toFirstPlace -- element for the first place after shifting
     */
    public static void shiftDoubleArrayRight(double[] elements, double toFirstPlace) {
        System.arraycopy(elements, 0, elements, 1, elements.length - 1);
        elements[0] = toFirstPlace;
    }
}
