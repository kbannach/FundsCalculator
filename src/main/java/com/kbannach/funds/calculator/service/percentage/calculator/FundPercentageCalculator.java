package com.kbannach.funds.calculator.service.percentage.calculator;

import com.kbannach.funds.calculator.api.CalculationResult;
import com.kbannach.funds.calculator.entity.Fund;
import com.kbannach.funds.calculator.service.definition.InvestmentStyleDefinition;

import java.util.Set;

public interface FundPercentageCalculator {

    CalculationResult calculateByFundsKind(Set<Fund> allFunds, Fund.Kind kind, InvestmentStyleDefinition styleDefinition);
}
