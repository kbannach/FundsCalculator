package com.kbannach.funds.calculator.service;

import java.math.BigDecimal;

class UnassignedAmountNegativeException extends RuntimeException {

    UnassignedAmountNegativeException(BigDecimal unassignedValue) {
        super(String.format("Unassigned amount cannot be negative! (Is %s)", unassignedValue.toString()));
    }
}
