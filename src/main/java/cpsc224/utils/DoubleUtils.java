package cpsc224.utils;

import java.text.DecimalFormat;

public class DoubleUtils {
    /**
     * Rounds a double and converts to a String.
     * @param d the double
     * @param digitsAfterDecimal number of digits after the decimal
     * @return the rounded number as a String
     */
    public static String roundDouble(double d, int digitsAfterDecimal) {
        StringBuilder format = new StringBuilder("#.");
        for (int i = 0; i < digitsAfterDecimal; i++)
            format.append("#");

        DecimalFormat df = new DecimalFormat(format.toString());
        return df.format(d);
    }

    /**
     * Rounds a double to one digit after the decimal and converts to a String.
     * @param d the double
     * @return the rounded double as a String
     */
    public static String roundDouble(double d) {
        return roundDouble(d, 1);
    }
}
