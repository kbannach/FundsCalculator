package com.kbannach.funds.calculator.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class ConversionUtils {

    private ConversionUtils() {
    }

    public static BigDecimal toBigDecimal(int value) {
        return new BigDecimal(value);
    }

    public static BigDecimal toBigDecimal(String value) {
        return new BigDecimal(value);
    }

    public static BigDecimal roundPercentage(BigDecimal percentageToRound) {
        return percentageToRound.setScale(2, RoundingMode.HALF_DOWN);
    }
}
