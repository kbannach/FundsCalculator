package com.kbannach.acceptance;

import com.kbannach.UnitTest;
import com.kbannach.funds.calculator.api.CalculationResult;
import com.kbannach.funds.calculator.api.CalculationResult.FundCalculationResult;
import com.kbannach.funds.calculator.api.FundsCalculator;
import com.kbannach.funds.calculator.api.InvestmentStyle;
import com.kbannach.funds.calculator.entity.Fund;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class AcceptanceTest extends UnitTest {

    private static final Fund FUND_PL_1 = new Fund("Fundusz Polski 1", Fund.Kind.PL);
    private static final Fund FUND_PL_2 = new Fund("Fundusz Polski 2", Fund.Kind.PL);
    private static final Fund FUND_PL_3 = new Fund("Fundusz Polski 3", Fund.Kind.PL);
    private static final Fund FUND_FOREIGN_1 = new Fund("Fundusz Zagraniczny 1", Fund.Kind.FOREIGN);
    private static final Fund FUND_FOREIGN_2 = new Fund("Fundusz Zagraniczny 2", Fund.Kind.FOREIGN);
    private static final Fund FUND_FOREIGN_3 = new Fund("Fundusz Zagraniczny 3", Fund.Kind.FOREIGN);
    private static final Fund FUND_MONETARY_1 = new Fund("Fundusz Pieniężny 1", Fund.Kind.MONETARY);

    private static final BigDecimal HALF_THOUSAND = new BigDecimal("500");
    private static final BigDecimal ONE_THOUSAND = new BigDecimal("1000");
    private static final BigDecimal TWO_AND_A_HALF_THOUSAND = new BigDecimal("2500");

    // user examples

    @Test
    public void calculateGivenFirstExample() {
        // given
        Set<Fund> funds = createFundsSet(
                FUND_PL_1,
                FUND_PL_2,
                FUND_FOREIGN_1,
                FUND_FOREIGN_2,
                FUND_FOREIGN_3,
                FUND_MONETARY_1
        );
        BigDecimal investmentAmount = new BigDecimal("10000");
        InvestmentStyle investmentStyle = InvestmentStyle.SAFE;

        // when
        CalculationResult result = FundsCalculator.calculate(investmentAmount, investmentStyle, funds);

        // then
        Map<Fund, FundCalculationResult> resultMap = result.getFundCalculationResults();
        assertFundResult(resultMap.get(FUND_PL_1), ONE_THOUSAND, BigDecimal.TEN);
        assertFundResult(resultMap.get(FUND_PL_2), ONE_THOUSAND, BigDecimal.TEN);
        assertFundResult(resultMap.get(FUND_FOREIGN_1), TWO_AND_A_HALF_THOUSAND, new BigDecimal("25"));
        assertFundResult(resultMap.get(FUND_FOREIGN_2), TWO_AND_A_HALF_THOUSAND, new BigDecimal("25"));
        assertFundResult(resultMap.get(FUND_FOREIGN_3), TWO_AND_A_HALF_THOUSAND, new BigDecimal("25"));
        assertFundResult(resultMap.get(FUND_MONETARY_1), HALF_THOUSAND, new BigDecimal("5"));

        assertTrue(BigDecimal.ZERO.compareTo(result.getUnassignedAmount()) == 0);
    }

    @Test
    public void calculateGivenSecondExample() {
        // given
        Set<Fund> funds = createFundsSet(
                FUND_PL_1,
                FUND_PL_2,
                FUND_FOREIGN_1,
                FUND_FOREIGN_2,
                FUND_FOREIGN_3,
                FUND_MONETARY_1
        );
        BigDecimal investmentAmount = new BigDecimal("10001");
        InvestmentStyle investmentStyle = InvestmentStyle.SAFE;

        // when
        CalculationResult result = FundsCalculator.calculate(investmentAmount, investmentStyle, funds);

        // then
        Map<Fund, FundCalculationResult> resultMap = result.getFundCalculationResults();
        assertFundResult(resultMap.get(FUND_PL_1), ONE_THOUSAND, BigDecimal.TEN);
        assertFundResult(resultMap.get(FUND_PL_2), ONE_THOUSAND, BigDecimal.TEN);
        assertFundResult(resultMap.get(FUND_FOREIGN_1), TWO_AND_A_HALF_THOUSAND, new BigDecimal("25"));
        assertFundResult(resultMap.get(FUND_FOREIGN_2), TWO_AND_A_HALF_THOUSAND, new BigDecimal("25"));
        assertFundResult(resultMap.get(FUND_FOREIGN_3), TWO_AND_A_HALF_THOUSAND, new BigDecimal("25"));
        assertFundResult(resultMap.get(FUND_MONETARY_1), HALF_THOUSAND, new BigDecimal("5"));

        assertTrue(BigDecimal.ONE.compareTo(result.getUnassignedAmount()) == 0);
    }

    @Test
    public void calculateGivenThirdExample() {
        // given
        Set<Fund> funds = createFundsSet(
                FUND_PL_1,
                FUND_PL_2,
                FUND_PL_3,
                FUND_FOREIGN_1,
                FUND_FOREIGN_2,
                FUND_MONETARY_1
        );
        BigDecimal investmentAmount = new BigDecimal("10000");
        InvestmentStyle investmentStyle = InvestmentStyle.SAFE;

        // when
        CalculationResult result = FundsCalculator.calculate(investmentAmount, investmentStyle, funds);

        // then
        Map<Fund, FundCalculationResult> resultMap = result.getFundCalculationResults();
        assertFundResult(resultMap.get(FUND_PL_1), new BigDecimal("668"), new BigDecimal("6.68"));
        assertFundResult(resultMap.get(FUND_PL_2), new BigDecimal("666"), new BigDecimal("6.66"));
        assertFundResult(resultMap.get(FUND_PL_3), new BigDecimal("666"), new BigDecimal("6.66"));
        assertFundResult(resultMap.get(FUND_FOREIGN_1), new BigDecimal("3750"), new BigDecimal("37.5"));
        assertFundResult(resultMap.get(FUND_FOREIGN_2), new BigDecimal("3750"), new BigDecimal("37.5"));
        assertFundResult(resultMap.get(FUND_MONETARY_1), HALF_THOUSAND, new BigDecimal("5"));

        assertTrue(BigDecimal.ONE.compareTo(result.getUnassignedAmount()) == 0);
    }

    private Set<Fund> createFundsSet(Fund... funds) {
        return new HashSet<>(Arrays.asList(funds));
    }

    private void assertFundResult(FundCalculationResult fund, BigDecimal expectedAmount, BigDecimal expectedPercentage) {

        assertTrue(expectedAmount.compareTo(fund.getAmount()) == 0);
        assertTrue(expectedPercentage.compareTo(fund.getPercentage()) == 0);
    }
}
