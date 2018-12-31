package com.kbannach.funds.calculator.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class ConversionUtils {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    private static final int DEFAULT_PRECISION = 2;
    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.DOWN;

    private ConversionUtils() {
    }

    public static BigDecimal toBigDecimal(int value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return round(bigDecimal);
    }

    public static BigDecimal toBigDecimal(int value, int precision, RoundingMode roundingMode) {
        return new BigDecimal(value).setScale(precision, roundingMode);
    }

    public static BigDecimal toBigDecimal(String value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return round(bigDecimal);
    }

    public static BigDecimal toBigDecimal(String value, int precision, RoundingMode roundingMode) {
        return new BigDecimal(value).setScale(precision, roundingMode);
    }

    public static BigDecimal round(BigDecimal percentageToRound) {
        return percentageToRound.setScale(DEFAULT_PRECISION, DEFAULT_ROUNDING_MODE);
    }

    public static BigDecimal percentageOfValue(BigDecimal value, BigDecimal percentage) {
        return value.multiply(percentage).divide(ONE_HUNDRED, RoundingMode.HALF_DOWN);
    }
}
