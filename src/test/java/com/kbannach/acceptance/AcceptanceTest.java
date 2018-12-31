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

import static com.kbannach.funds.calculator.utils.ConversionUtils.toBigDecimal;

public class AcceptanceTest extends UnitTest {

    private static final Fund FUND_PL_1 = new Fund("Fundusz Polski 1", Fund.Kind.PL);
    private static final Fund FUND_PL_2 = new Fund("Fundusz Polski 2", Fund.Kind.PL);
    private static final Fund FUND_PL_3 = new Fund("Fundusz Polski 3", Fund.Kind.PL);
    private static final Fund FUND_FOREIGN_1 = new Fund("Fundusz Zagraniczny 1", Fund.Kind.FOREIGN);
    private static final Fund FUND_FOREIGN_2 = new Fund("Fundusz Zagraniczny 2", Fund.Kind.FOREIGN);
    private static final Fund FUND_FOREIGN_3 = new Fund("Fundusz Zagraniczny 3", Fund.Kind.FOREIGN);
    private static final Fund FUND_MONETARY_1 = new Fund("Fundusz Pieniężny 1", Fund.Kind.MONETARY);

    private static final BigDecimal HALF_THOUSAND = toBigDecimal("500");
    private static final BigDecimal ONE_THOUSAND = toBigDecimal("1000");
    private static final BigDecimal TWO_AND_A_HALF_THOUSAND = toBigDecimal("2500");

    private FundsCalculator fundsCalculator = new FundsCalculator();


    // tests based on user examples

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
        BigDecimal investmentAmount = toBigDecimal("10000");
        InvestmentStyle investmentStyle = InvestmentStyle.SAFE;

        // when
        CalculationResult result = fundsCalculator.calculate(investmentAmount, investmentStyle, funds);

        // then
        Map<Fund, FundCalculationResult> resultMap = result.getFundCalculationResults();
        assertFundResult(resultMap.get(FUND_PL_1), ONE_THOUSAND, BigDecimal.TEN);
        assertFundResult(resultMap.get(FUND_PL_2), ONE_THOUSAND, BigDecimal.TEN);
        assertFundResult(resultMap.get(FUND_FOREIGN_1), TWO_AND_A_HALF_THOUSAND, toBigDecimal("25"));
        assertFundResult(resultMap.get(FUND_FOREIGN_2), TWO_AND_A_HALF_THOUSAND, toBigDecimal("25"));
        assertFundResult(resultMap.get(FUND_FOREIGN_3), TWO_AND_A_HALF_THOUSAND, toBigDecimal("25"));
        assertFundResult(resultMap.get(FUND_MONETARY_1), HALF_THOUSAND, toBigDecimal("5"));

        assertBigDecimalValues(BigDecimal.ZERO, result.getUnassignedAmount());
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
        BigDecimal investmentAmount = toBigDecimal("10001");
        InvestmentStyle investmentStyle = InvestmentStyle.SAFE;

        // when
        CalculationResult result = fundsCalculator.calculate(investmentAmount, investmentStyle, funds);

        // then
        Map<Fund, FundCalculationResult> resultMap = result.getFundCalculationResults();
        assertFundResult(resultMap.get(FUND_PL_1), ONE_THOUSAND, BigDecimal.TEN);
        assertFundResult(resultMap.get(FUND_PL_2), ONE_THOUSAND, BigDecimal.TEN);
        assertFundResult(resultMap.get(FUND_FOREIGN_1), TWO_AND_A_HALF_THOUSAND, toBigDecimal("25"));
        assertFundResult(resultMap.get(FUND_FOREIGN_2), TWO_AND_A_HALF_THOUSAND, toBigDecimal("25"));
        assertFundResult(resultMap.get(FUND_FOREIGN_3), TWO_AND_A_HALF_THOUSAND, toBigDecimal("25"));
        assertFundResult(resultMap.get(FUND_MONETARY_1), HALF_THOUSAND, toBigDecimal("5"));

        assertBigDecimalValues(BigDecimal.ONE, result.getUnassignedAmount());
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
        BigDecimal investmentAmount = toBigDecimal("10000");
        InvestmentStyle investmentStyle = InvestmentStyle.SAFE;

        // when
        CalculationResult result = fundsCalculator.calculate(investmentAmount, investmentStyle, funds);

        // then
        Map<Fund, FundCalculationResult> resultMap = result.getFundCalculationResults();

        assertFundResult(resultMap.get(FUND_PL_1), toBigDecimal("666"), toBigDecimal("6.66"));
        assertFundResult(resultMap.get(FUND_PL_2), toBigDecimal("666"), toBigDecimal("6.66"));
        assertFundResult(resultMap.get(FUND_PL_3), toBigDecimal("668"), toBigDecimal("6.68"));
        assertFundResult(resultMap.get(FUND_FOREIGN_1), toBigDecimal("3750"), toBigDecimal("37.5"));
        assertFundResult(resultMap.get(FUND_FOREIGN_2), toBigDecimal("3750"), toBigDecimal("37.5"));
        assertFundResult(resultMap.get(FUND_MONETARY_1), HALF_THOUSAND, toBigDecimal("5"));

        assertBigDecimalValues(BigDecimal.ZERO, result.getUnassignedAmount());
    }

    private Set<Fund> createFundsSet(Fund... funds) {
        return new HashSet<>(Arrays.asList(funds));
    }

    private void assertFundResult(FundCalculationResult fund, BigDecimal expectedAmount, BigDecimal expectedPercentage) {

        assertBigDecimalValues(expectedAmount, fund.getAmount());
        assertBigDecimalValues(expectedPercentage, fund.getPercentage());
    }
}
