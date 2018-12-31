package com.kbannach.funds.calculator.service;

import com.kbannach.funds.calculator.api.CalculationResult;
import com.kbannach.funds.calculator.api.InvestmentStyle;
import com.kbannach.funds.calculator.entity.Fund;

import java.math.BigDecimal;
import java.util.Set;

public interface FundsCalculatorService {

    CalculationResult calculate(BigDecimal investmentAmount, InvestmentStyle investmentStyle, Set<Fund> funds);
}
