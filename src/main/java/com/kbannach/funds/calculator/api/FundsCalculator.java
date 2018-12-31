package com.kbannach.funds.calculator.api;

import com.kbannach.funds.calculator.dependency.DependencyContainer;
import com.kbannach.funds.calculator.entity.Fund;
import com.kbannach.funds.calculator.service.FundsCalculatorService;

import java.math.BigDecimal;
import java.util.Set;

public class FundsCalculator {

    private FundsCalculatorService fundsCalculatorService = DependencyContainer.getFundsCalculatorService();

    public CalculationResult calculate(BigDecimal investmentAmount, InvestmentStyle investmentStyle, Set<Fund> funds) {
        return fundsCalculatorService.calculate(investmentAmount, investmentStyle, funds);
    }
}
