package com.kbannach.funds.calculator.service.corrector;

import com.kbannach.funds.calculator.api.CalculationResult;

import java.math.BigDecimal;

public interface PercentageCorrector {

    void correctPercentage(CalculationResult toCorrect, BigDecimal sumUpTo);
}
