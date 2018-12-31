package com.kbannach.funds.calculator.service.percentage.corrector;

import com.kbannach.funds.calculator.entity.Fund;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

public class OneElementCorrectingPercentageCorrector implements PercentageCorrector {

    public void correctPercentage(Map<Fund, BigDecimal> toCorrect, BigDecimal sumUpTo) {

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

    private void correctResult(Map<Fund, BigDecimal> toCorrect, BigDecimal difference) {

        Fund correctionKey = toCorrect.keySet().iterator().next();

        BigDecimal oldPercentage = toCorrect.get(correctionKey);
        toCorrect.put(correctionKey, oldPercentage.add(difference));
    }

    private BigDecimal sumPercentage(Map<Fund, BigDecimal> toCorrect) {

        Collection<BigDecimal> fundCalculationResults = toCorrect.values();
        return fundCalculationResults.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private boolean percentageNotSumsUpToOneHundred(BigDecimal difference) {

        return BigDecimal.ZERO.compareTo(difference) != 0;
    }
}
