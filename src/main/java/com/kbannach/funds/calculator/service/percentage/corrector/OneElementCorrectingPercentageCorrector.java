package com.kbannach.funds.calculator.service.percentage.corrector;

import com.kbannach.funds.calculator.api.CalculationResult;

import java.math.BigDecimal;
import java.util.Collection;

public class OneElementCorrectingPercentageCorrector implements PercentageCorrector {

    public void correctPercentage(CalculationResult toCorrect, BigDecimal sumUpTo) {

        BigDecimal sum = sumPercentage(toCorrect);

        BigDecimal difference = getDifference(sumUpTo, sum);
        if (percentageNotSumsUpToOneHundred(difference)) {

            correctResult(toCorrect, difference);
        }
    }

    private BigDecimal getDifference(BigDecimal sumUpTo, BigDecimal sum) {

        BigDecimal difference = sumUpTo.subtract(sum);
        if (differenceNotExceedTotal(difference)) {
            throw PercentageSumExceededException.create(sumUpTo, sum);
        }
        return difference;
    }

    private boolean differenceNotExceedTotal(BigDecimal difference) {

        return BigDecimal.ZERO.compareTo(difference) > 0;
    }

    private void correctResult(CalculationResult toCorrect, BigDecimal difference) {

        CalculationResult.FundCalculationResult resultToCorrect = toCorrect.getFundCalculationResults().values().iterator().next();
        resultToCorrect.setPercentage(resultToCorrect.getPercentage().add(difference));
    }

    private BigDecimal sumPercentage(CalculationResult toCorrect) {

        Collection<CalculationResult.FundCalculationResult> fundCalculationResults = toCorrect.getFundCalculationResults().values();
        return fundCalculationResults.stream()
                .reduce(
                        BigDecimal.ZERO,
                        (sum, fundCalculationResult) -> sum.add(fundCalculationResult.getPercentage()),
                        BigDecimal::add
                );
    }

    private boolean percentageNotSumsUpToOneHundred(BigDecimal difference) {

        return BigDecimal.ZERO.compareTo(difference) != 0;
    }
}
