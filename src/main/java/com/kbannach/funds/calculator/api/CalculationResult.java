package com.kbannach.funds.calculator.api;

import com.kbannach.funds.calculator.entity.Fund;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class CalculationResult {

    @Getter
    @Setter
    public static class FundCalculationResult {

        private BigDecimal amount;
        private BigDecimal percentage;
    }

    private Map<Fund, FundCalculationResult> fundCalculationResults = new HashMap<>();
    private BigDecimal unassignedAmount;

    public void addFundCalculation(Fund fund, BigDecimal percentage) {

        FundCalculationResult result = new FundCalculationResult();
        result.setPercentage(percentage);
        fundCalculationResults.put(fund, result);
    }
}
