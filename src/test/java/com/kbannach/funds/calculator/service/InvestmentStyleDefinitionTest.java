package com.kbannach.funds.calculator.service;

import com.kbannach.UnitTest;
import com.kbannach.funds.calculator.api.InvestmentStyle;
import com.kbannach.funds.calculator.service.definition.InvestmentStyleDefinition;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InvestmentStyleDefinitionTest extends UnitTest {

    @Test
    public void getByInvestmentStyleGivenStyleThenReturnRelatedDefinition() {
        // given
        InvestmentStyle safe = InvestmentStyle.SAFE;
        InvestmentStyle aggressive = InvestmentStyle.AGGRESSIVE;
        InvestmentStyle balanced = InvestmentStyle.BALANCED;

        // when
        InvestmentStyleDefinition safeDef = InvestmentStyleDefinition.getByInvestmentStyle(safe);
        InvestmentStyleDefinition aggressiveDef = InvestmentStyleDefinition.getByInvestmentStyle(aggressive);
        InvestmentStyleDefinition balancedDef = InvestmentStyleDefinition.getByInvestmentStyle(balanced);

        // then
        assertEquals(InvestmentStyleDefinition.SAFE, safeDef);
        assertEquals(InvestmentStyleDefinition.AGGRESSIVE, aggressiveDef);
        assertEquals(InvestmentStyleDefinition.BALANCED, balancedDef);
    }
}