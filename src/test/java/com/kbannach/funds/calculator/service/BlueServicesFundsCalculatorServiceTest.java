package com.kbannach.funds.calculator.service;

import com.kbannach.UnitTest;
import com.kbannach.funds.calculator.api.CalculationResult;
import com.kbannach.funds.calculator.api.InvestmentStyle;
import com.kbannach.funds.calculator.entity.Fund;
import com.kbannach.funds.calculator.service.percentage.calculator.FundPercentageCalculator;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static com.kbannach.funds.calculator.api.CalculationResult.FundCalculationResult;
import static com.kbannach.funds.calculator.utils.ConversionUtils.toBigDecimal;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class BlueServicesFundsCalculatorServiceTest extends UnitTest {

    @Mock
    private FundPercentageCalculator fundPercentageCalculator;

    @InjectMocks
    private BlueServicesFundsCalculatorService blueServicesFundsCalculatorService;

    @Test
    public void calculateGivenOneFundForEachKindThenPercentageEqualsDefinition() {
        // given
        Fund plFund = new Fund("fund1", Fund.Kind.PL);
        Fund foreignFund = new Fund("fund2", Fund.Kind.FOREIGN);
        Fund monetaryFund = new Fund("fund3", Fund.Kind.MONETARY);
        HashSet<Fund> funds = new HashSet<>(Arrays.asList(plFund, foreignFund, monetaryFund));

        BigDecimal investmentAmount = toBigDecimal(1000);

        BigDecimal expectedPlPercentage = toBigDecimal(20);
        BigDecimal expectedForeignPercentage = toBigDecimal(75);
        BigDecimal expectedMonetaryPercentage = toBigDecimal(5);

        mockCalculationResult(plFund, Fund.Kind.PL, expectedPlPercentage);
        mockCalculationResult(foreignFund, Fund.Kind.FOREIGN, expectedForeignPercentage);
        mockCalculationResult(monetaryFund, Fund.Kind.MONETARY, expectedMonetaryPercentage);

        // when
        CalculationResult result = blueServicesFundsCalculatorService.calculate(investmentAmount, InvestmentStyle.SAFE, funds);

        // then
        BigDecimal expectedPlAmount = toBigDecimal(200);
        BigDecimal expectedForeignAmount = toBigDecimal(750);
        BigDecimal expectedMonetaryAmount = toBigDecimal(50);

        Map<Fund, FundCalculationResult> fundResults = result.getFundCalculationResults();
        FundCalculationResult actualPlResult = fundResults.get(plFund);
        assertBigDecimalValues(expectedPlPercentage, actualPlResult.getPercentage());
        assertBigDecimalValues(expectedPlAmount, actualPlResult.getAmount());

        FundCalculationResult actualForeignResult = fundResults.get(foreignFund);
        assertBigDecimalValues(expectedForeignPercentage, actualForeignResult.getPercentage());
        assertBigDecimalValues(expectedForeignAmount, actualForeignResult.getAmount());

        FundCalculationResult actualMonetaryResult = fundResults.get(monetaryFund);
        assertBigDecimalValues(expectedMonetaryPercentage, actualMonetaryResult.getPercentage());
        assertBigDecimalValues(expectedMonetaryAmount, actualMonetaryResult.getAmount());
    }

    @Test
    public void calculateGivenUnassignedAmountToSumUpToElevenThenUnassignedAmountEleven() {
        // given
        Fund plFund = new Fund("fund1", Fund.Kind.PL);
        Fund foreignFund = new Fund("fund2", Fund.Kind.FOREIGN);
        Fund monetaryFund = new Fund("fund3", Fund.Kind.MONETARY);
        HashSet<Fund> funds = new HashSet<>(Arrays.asList(plFund, foreignFund, monetaryFund));

        BigDecimal investmentAmount = toBigDecimal(1000);

        BigDecimal expectedPlPercentage = toBigDecimal(20);
        BigDecimal expectedForeignPercentage = toBigDecimal(70);
        BigDecimal expectedMonetaryPercentage = toBigDecimal(5);

        mockCalculationResult(plFund, Fund.Kind.PL, expectedPlPercentage);
        mockCalculationResult(foreignFund, Fund.Kind.FOREIGN, expectedForeignPercentage);
        mockCalculationResult(monetaryFund, Fund.Kind.MONETARY, expectedMonetaryPercentage);

        // when
        CalculationResult result = blueServicesFundsCalculatorService.calculate(investmentAmount, InvestmentStyle.SAFE, funds);

        // then
        assertBigDecimalValues(toBigDecimal(50), result.getUnassignedAmount());
    }

    @Test(expected = UnassignedAmountNegativeException.class)
    public void calculateGivenTooHighPercentageThenUnassignedAmountNegativeException() {
        // given
        Fund plFund = new Fund("fund1", Fund.Kind.PL);
        Fund foreignFund = new Fund("fund2", Fund.Kind.FOREIGN);
        Fund monetaryFund = new Fund("fund3", Fund.Kind.MONETARY);
        HashSet<Fund> funds = new HashSet<>(Arrays.asList(plFund, foreignFund, monetaryFund));

        BigDecimal investmentAmount = toBigDecimal(1000);

        BigDecimal expectedPlPercentage = toBigDecimal(20);
        BigDecimal expectedForeignPercentage = toBigDecimal(80);
        BigDecimal expectedMonetaryPercentage = toBigDecimal(5);

        mockCalculationResult(plFund, Fund.Kind.PL, expectedPlPercentage);
        mockCalculationResult(foreignFund, Fund.Kind.FOREIGN, expectedForeignPercentage);
        mockCalculationResult(monetaryFund, Fund.Kind.MONETARY, expectedMonetaryPercentage);

        // when
        blueServicesFundsCalculatorService.calculate(investmentAmount, InvestmentStyle.SAFE, funds);
    }

    private void mockCalculationResult(Fund fund, Fund.Kind fundKind, BigDecimal fundPercentage) {

        HashMap<Fund, BigDecimal> resultsMap = new HashMap<>();
        resultsMap.put(fund, fundPercentage);
        when(fundPercentageCalculator.calculateByFundsKind(any(), eq(fundKind), any())).thenReturn(resultsMap);
    }
}