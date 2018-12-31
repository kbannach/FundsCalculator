package com.kbannach.funds.calculator.service.percentage.corrector;

import com.kbannach.UnitTest;
import com.kbannach.funds.calculator.entity.Fund;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.kbannach.funds.calculator.utils.ConversionUtils.toBigDecimal;

public class OneElementCorrectingPercentageCorrectorTest extends UnitTest {

    private static final Fund FIRST_FUND = new Fund("first", Fund.Kind.PL);
    private static final Fund SECOND_FUND = new Fund("second", Fund.Kind.PL);
    private static final Fund THIRD_FUND = new Fund("third", Fund.Kind.PL);

    @InjectMocks
    private OneElementCorrectingPercentageCorrector oneElementCorrectingPercentageCorrector;

    @Test(expected = PercentageSumExceededException.class)
    public void correctPercentageGivenSumUpToHigherThanPercentageSumThenException() {
        // given
        BigDecimal sumUpTo = toBigDecimal("15");
        BigDecimal fundPercentage = toBigDecimal("5.01");

        performTest(sumUpTo, fundPercentage);
    }

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
        Map<Fund, BigDecimal> toCorrect = new HashMap<>(3);
        toCorrect.put(FIRST_FUND, fundPercentage);
        toCorrect.put(SECOND_FUND, fundPercentage);
        toCorrect.put(THIRD_FUND, fundPercentage);

        // when
        oneElementCorrectingPercentageCorrector.correctPercentage(toCorrect, sumUpTo);

        // then
        Collection<BigDecimal> results = toCorrect.values();
        BigDecimal total = results.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        assertBigDecimalValues(total, sumUpTo);
    }
}