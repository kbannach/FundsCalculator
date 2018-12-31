package com.kbannach.funds.calculator.service.percentage.calculator;

import com.kbannach.funds.calculator.entity.Fund;
import com.kbannach.funds.calculator.service.definition.InvestmentStyleDefinition;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public interface FundPercentageCalculator {

    Map<Fund, BigDecimal> calculateByFundsKind(Set<Fund> allFunds, Fund.Kind kind, InvestmentStyleDefinition styleDefinition);
}
