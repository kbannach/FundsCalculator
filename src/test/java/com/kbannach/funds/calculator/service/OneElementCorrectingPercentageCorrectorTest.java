package com.kbannach.funds.calculator.service;

import com.kbannach.UnitTest;
import com.kbannach.funds.calculator.api.CalculationResult;
import com.kbannach.funds.calculator.entity.Fund;
import com.kbannach.funds.calculator.service.percentage.corrector.OneElementCorrectingPercentageCorrector;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.util.Map;

import static com.kbannach.funds.calculator.utils.ConversionUtils.toBigDecimal;
import static org.junit.Assert.assertTrue;

public class OneElementCorrectingPercentageCorrectorTest extends UnitTest {

    private static final Fund FIRST_FUND = new Fund("first", Fund.Kind.PL);
    private static final Fund SECOND_FUND = new Fund("second", Fund.Kind.PL);
    private static final Fund THIRD_FUND = new Fund("third", Fund.Kind.PL);

    @InjectMocks
    private OneElementCorrectingPercentageCorrector percentageCorrector;

    @Test
    public void correctPercentageGivenCorrectionNotNeededThenNoCorrection() {
        // given
        BigDecimal sumUpTo = toBigDecimal("15");
        BigDecimal fundPercentage = toBigDecimal("5");

        performTest(sumUpTo, fundPercentage);
    }

    @Test
    public void correctPercentageGivenCorrectionNecessaryThenCorrectOnePercentage() {
        // given
        BigDecimal sumUpTo = toBigDecimal("20");
        BigDecimal fundPercentage = toBigDecimal("6.66");

        performTest(sumUpTo, fundPercentage);
    }

    private void performTest(BigDecimal sumUpTo, BigDecimal fundPercentage) {
        // given
        CalculationResult toCorrect = new CalculationResult();
        toCorrect.addFundCalculation(FIRST_FUND, fundPercentage);
        toCorrect.addFundCalculation(SECOND_FUND, fundPercentage);
        toCorrect.addFundCalculation(THIRD_FUND, fundPercentage);

        // when
        percentageCorrector.correctPercentage(toCorrect, sumUpTo);

        // then
        Map<Fund, CalculationResult.FundCalculationResult> results = toCorrect.getFundCalculationResults();
        BigDecimal total = results.values()
                .stream()
                .reduce(
                        BigDecimal.ZERO,
                        (sum, next) -> sum.add(next.getPercentage()),
                        BigDecimal::add
                );

        assertTrue(total.compareTo(sumUpTo) == 0);
    }
}