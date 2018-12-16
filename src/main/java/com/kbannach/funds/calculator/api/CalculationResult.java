package com.kbannach.funds.calculator.api;

import com.kbannach.funds.calculator.entity.Fund;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class CalculationResult {

    @Getter
    @Setter
    @Builder
    public static class FundCalculationResult {

        private BigDecimal amount;
        private BigDecimal percentage;
    }

    private Map<Fund, FundCalculationResult> fundCalculationResults;
    private BigDecimal unassignedAmount;
}
