package com.kbannach.funds.calculator.service.corrector;

import java.math.BigDecimal;

class PercentageSumExceededException extends RuntimeException {

    static PercentageSumExceededException create(BigDecimal sumUpTo, BigDecimal sum) {

        String message = String.format("Percentage sum should ot exceed %s, but is %s.", sumUpTo.toString(), sum.toString());
        return new PercentageSumExceededException(message);
    }

    private PercentageSumExceededException(String message) {
        super(message);
    }
}
