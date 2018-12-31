package com.kbannach.funds.calculator.service;

import com.kbannach.UnitTest;
import com.kbannach.funds.calculator.entity.Fund;
import com.kbannach.funds.calculator.service.definition.InvestmentStyleDefinition;
import com.kbannach.funds.calculator.service.percentage.calculator.EqualPercentageFundPercentageCalculator;
import com.kbannach.funds.calculator.service.percentage.corrector.PercentageCorrector;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.kbannach.funds.calculator.utils.ConversionUtils.toBigDecimal;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Every.everyItem;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class EqualPercentageFundPercentageCalculatorTest extends UnitTest {

    private static final InvestmentStyleDefinition STYLE_DEFINITION = InvestmentStyleDefinition.SAFE;

    @Mock
    private PercentageCorrector percentageCorrector;

    @InjectMocks
    private EqualPercentageFundPercentageCalculator equalPercentageFundPercentageCalculator;

    @Test(expected = NullPointerException.class)
    public void calculateByFundsKindGivenNullFundsThenNPE() {
        // when
        equalPercentageFundPercentageCalculator.calculateByFundsKind(null, Fund.Kind.PL, STYLE_DEFINITION);
    }

    @Test(expected = NullPointerException.class)
    public void calculateByFundsKindGivenNullKindThenNPE() {
        // given
        Fund fund = createFund("plFirst", Fund.Kind.PL);

        // when
        equalPercentageFundPercentageCalculator.calculateByFundsKind(Collections.singleton(fund), null, STYLE_DEFINITION);
    }

    @Test
    public void calculateByFundsKindGivenEmptySetThenResultIsEmpty() {
        // given
        Set<Fund> funds = Collections.emptySet();

        // when
        Map<Fund, BigDecimal> calculationResult = equalPercentageFundPercentageCalculator.calculateByFundsKind(funds, Fund.Kind.PL, STYLE_DEFINITION);

        // then
        Set<Fund> evaluatedFunds = calculationResult.keySet();
        assertTrue(evaluatedFunds.isEmpty());
    }

    @Test
    public void calculateByFundsKindGivenKindThenResultContainsOnlySpecifiedKindFunds() {
        // given
        Fund plFirst = createFund("plFirst", Fund.Kind.PL);
        Fund plSecond = createFund("plSecond", Fund.Kind.PL);
        Fund plThird = createFund("plThird", Fund.Kind.PL);
        Fund forFirst = createFund("forFirst", Fund.Kind.FOREIGN);
        Set<Fund> funds = new HashSet<>(Arrays.asList(plFirst, plSecond, plThird, forFirst));

        // when
        Map<Fund, BigDecimal> calculationResult = equalPercentageFundPercentageCalculator.calculateByFundsKind(funds, Fund.Kind.PL, STYLE_DEFINITION);

        // then
        Set<Fund> evaluatedFunds = calculationResult.keySet();
        assertThat(evaluatedFunds, containsInAnyOrder(plFirst, plSecond, plThird));
        assertThat(evaluatedFunds, not(containsInAnyOrder(forFirst)));
    }

    @Test
    public void calculateByFundsKindGivenKindThenEveryFundHasEqualPercentage() {
        // given
        Fund.Kind kind = Fund.Kind.PL;
        Fund plFirst = createFund("plFirst", kind);
        Fund plSecond = createFund("plSecond", kind);
        Fund plThird = createFund("plThird", kind);
        HashSet<Fund> funds = new HashSet<>(Arrays.asList(plFirst, plSecond, plThird));

        // when
        Map<Fund, BigDecimal> calculationResult = equalPercentageFundPercentageCalculator.calculateByFundsKind(funds, kind, STYLE_DEFINITION);

        // then
        Collection<BigDecimal> percentages = calculationResult.values();
        assertTrue(percentages.iterator().hasNext());
        BigDecimal p = percentages.iterator().next();

        assertThat(percentages, everyItem(is(p)));

        BigDecimal actualSum = percentages.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expectedTotalPercentage = STYLE_DEFINITION.getTotalPercentageByKind(kind);
        BigDecimal divisor = toBigDecimal(3);
        expectedTotalPercentage = expectedTotalPercentage.divide(divisor, RoundingMode.DOWN).multiply(divisor);

        assertBigDecimalValues(expectedTotalPercentage, actualSum);
    }

    private Fund createFund(String name, Fund.Kind kind) {
        return new Fund(name, kind);
    }
}