package com.kbannach.funds.calculator.service.percentage.corrector;

import com.kbannach.funds.calculator.entity.Fund;

import java.math.BigDecimal;
import java.util.Map;

public interface PercentageCorrector {

    void correctPercentage(Map<Fund, BigDecimal> toCorrect, BigDecimal sumUpTo);
}
