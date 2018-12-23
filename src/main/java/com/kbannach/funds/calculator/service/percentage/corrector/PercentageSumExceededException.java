package com.kbannach.funds.calculator.service.percentage.corrector;

import java.math.BigDecimal;

public class PercentageSumExceededException extends RuntimeException {

    static PercentageSumExceededException create(BigDecimal sumUpTo, BigDecimal sum) {

        String message = String.format("Percentage sum should not exceed %s, but is %s.", sumUpTo.toString(), sum.toString());
        return new PercentageSumExceededException(message);
    }

    private PercentageSumExceededException(String message) {
        super(message);
    }
}
