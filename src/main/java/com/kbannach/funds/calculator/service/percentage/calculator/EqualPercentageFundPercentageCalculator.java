package com.kbannach.funds.calculator.service.percentage.calculator;

import com.kbannach.funds.calculator.entity.Fund;
import com.kbannach.funds.calculator.service.definition.InvestmentStyleDefinition;
import com.kbannach.funds.calculator.service.percentage.corrector.PercentageCorrector;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kbannach.funds.calculator.utils.ConversionUtils.DEFAULT_ROUNDING_MODE;
import static com.kbannach.funds.calculator.utils.ConversionUtils.round;
import static com.kbannach.funds.calculator.utils.ConversionUtils.toBigDecimal;

@AllArgsConstructor
public class EqualPercentageFundPercentageCalculator implements FundPercentageCalculator {

    private PercentageCorrector percentageCorrector;

    public Map<Fund, BigDecimal> calculateByFundsKind(Set<Fund> allFunds, Fund.Kind kind, InvestmentStyleDefinition styleDefinition) {

        Set<Fund> fundsByKind = findFundsByKind(allFunds, kind);
        if (fundsByKind.isEmpty()) {
            return new HashMap<>(0);
        }

        BigDecimal totalPercentage = styleDefinition.getTotalPercentageByKind(kind);
        BigDecimal equalPercentage = calculatePercentageToSet(fundsByKind.size(), totalPercentage);

        Map<Fund, BigDecimal> result = new HashMap<>(allFunds.size());
        fundsByKind.forEach(f -> result.put(f, equalPercentage));

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
        BigDecimal percentage = totalPercentage.divide(divisor, DEFAULT_ROUNDING_MODE);
        return round(percentage);
    }
}
