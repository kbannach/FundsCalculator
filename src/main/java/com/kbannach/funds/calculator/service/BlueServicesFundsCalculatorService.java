package com.kbannach.funds.calculator.service;

import com.kbannach.funds.calculator.api.CalculationResult;
import com.kbannach.funds.calculator.api.CalculationResult.FundCalculationResult;
import com.kbannach.funds.calculator.api.InvestmentStyle;
import com.kbannach.funds.calculator.entity.Fund;
import com.kbannach.funds.calculator.service.definition.InvestmentStyleDefinition;
import com.kbannach.funds.calculator.service.percentage.calculator.FundPercentageCalculator;
import com.kbannach.funds.calculator.utils.ConversionUtils;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
public class BlueServicesFundsCalculatorService implements FundsCalculatorService {

    private FundPercentageCalculator fundPercentageCalculator;

    public CalculationResult calculate(BigDecimal investmentAmount, InvestmentStyle investmentStyle, Set<Fund> funds) {

        InvestmentStyleDefinition styleDefinition = InvestmentStyleDefinition.getByInvestmentStyle(investmentStyle);

        Map<Fund, BigDecimal> percentages = calculateEveryFundPercentage(funds, styleDefinition);

        CalculationResult result = new CalculationResult();
        percentages.forEach(result::addFundCalculation);
        calculateEveryFundAmount(result, investmentAmount);
        calculateUnassignedValue(result, investmentAmount);

        return result;
    }

    private Map<Fund, BigDecimal> calculateEveryFundPercentage(Set<Fund> funds, InvestmentStyleDefinition styleDefinition) {

        return Arrays.stream(Fund.Kind.values())
                .map(kind -> fundPercentageCalculator.calculateByFundsKind(funds, kind, styleDefinition))
                .reduce(new HashMap<>(funds.size()), BlueServicesFundsCalculatorService::sumCalcResult);
    }

    private void calculateEveryFundAmount(CalculationResult result, BigDecimal investmentAmount) {

        result.getFundCalculationResults()
                .values()
                .forEach(calcResult -> calculateFundAmount(calcResult, investmentAmount));
    }

    private void calculateFundAmount(FundCalculationResult result, BigDecimal investmentAmount) {

        BigDecimal resultPercentage = result.getPercentage();
        BigDecimal newAmount = ConversionUtils.percentageOfValue(investmentAmount, resultPercentage);
        result.setAmount(newAmount);
    }

    private void calculateUnassignedValue(CalculationResult result, BigDecimal investmentAmount) {

        BigDecimal amountSum = result.getFundCalculationResults().values()
                .stream()
                .map(FundCalculationResult::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal unassignedValue = investmentAmount.subtract(amountSum);
        assertNotNegative(unassignedValue);

        result.setUnassignedAmount(unassignedValue);
    }

    private void assertNotNegative(BigDecimal unassignedValue) {

        if (BigDecimal.ZERO.compareTo(unassignedValue) > 0) {
            throw new UnassignedAmountNegativeException(unassignedValue);
        }
    }

    private static Map<Fund, BigDecimal> sumCalcResult(Map<Fund, BigDecimal> l, Map<Fund, BigDecimal> r) {

        if (Objects.isNull(l)) {
            return r;
        } else if (Objects.isNull(r)) {
            return l;
        } else {
            l.putAll(r);
            return l;
        }
    }
}
