package com.kbannach.funds.calculator.api;

import com.kbannach.funds.calculator.entity.Fund;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class CalculationResult {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FundCalculationResult {

        private BigDecimal amount;
        private BigDecimal percentage;
    }

    private Map<Fund, FundCalculationResult> fundCalculationResults = new HashMap<>();
    private BigDecimal unassignedAmount = BigDecimal.ZERO;

    public void addFundCalculation(Fund fund, BigDecimal percentage) {

        FundCalculationResult result = new FundCalculationResult();
        result.setPercentage(percentage);
        fundCalculationResults.put(fund, result);
    }
}
