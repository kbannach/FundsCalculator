package com.kbannach.funds.calculator.service.percentage.calculator;

import com.kbannach.funds.calculator.api.CalculationResult;
import com.kbannach.funds.calculator.entity.Fund;
import com.kbannach.funds.calculator.service.definition.InvestmentStyleDefinition;
import com.kbannach.funds.calculator.service.percentage.corrector.PercentageCorrector;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kbannach.funds.calculator.utils.ConversionUtils.roundPercentage;
import static com.kbannach.funds.calculator.utils.ConversionUtils.toBigDecimal;

@AllArgsConstructor
public class EqualPercentageFundPercentageCalculator implements FundPercentageCalculator {

    private PercentageCorrector percentageCorrector;

    public CalculationResult calculateByFundsKind(Set<Fund> allFunds, Fund.Kind kind, InvestmentStyleDefinition styleDefinition) {

        CalculationResult result = new CalculationResult();
        BigDecimal totalPercentage = styleDefinition.getTotalPercentageByKind(kind);

        Set<Fund> fundsByKind = findFundsByKind(allFunds, kind);
        if (fundsByKind.isEmpty()) {
            return new CalculationResult();
        }

        BigDecimal equalPercentage = calculatePercentageToSet(fundsByKind.size(), totalPercentage);
        fundsByKind.forEach(f -> result.addFundCalculation(f, equalPercentage));

        percentageCorrector.correctPercentage(result, totalPercentage);
        return result;
    }

    private Set<Fund> findFundsByKind(Set<Fund> funds, Fund.Kind kind) {

        return funds.stream()
                .filter(f -> kind.equals(f.getKind()))
                .collect(Collectors.toSet());
    }

    private BigDecimal calculatePercentageToSet(int fundsCount, BigDecimal totalPercentage) {

        BigDecimal divisor = toBigDecimal(fundsCount);
        BigDecimal percentage = totalPercentage.divide(divisor, RoundingMode.HALF_DOWN);
        return roundPercentage(percentage);
    }
}
