package com.kbannach.funds.calculator.service.definition;

import com.kbannach.funds.calculator.api.InvestmentStyle;
import com.kbannach.funds.calculator.entity.Fund;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.kbannach.funds.calculator.utils.ConversionUtils.toBigDecimal;

public enum InvestmentStyleDefinition {

    SAFE(InvestmentStyle.SAFE, toBigDecimal(20), toBigDecimal(75), toBigDecimal(5)),
    BALANCED(InvestmentStyle.BALANCED, toBigDecimal(30), toBigDecimal(60), toBigDecimal(10)),
    AGGRESSIVE(InvestmentStyle.AGGRESSIVE, toBigDecimal(40), toBigDecimal(20), toBigDecimal(40));

    private InvestmentStyle style;
    private BigDecimal plPercentage;
    private BigDecimal foreignPercentage;
    private BigDecimal monetaryPercentage;

    InvestmentStyleDefinition(InvestmentStyle style, BigDecimal plPercentage, BigDecimal foreignPercentage, BigDecimal monetaryPercentage) {

        this.style = style;
        this.plPercentage = plPercentage;
        this.foreignPercentage = foreignPercentage;
        this.monetaryPercentage = monetaryPercentage;
    }

    public BigDecimal getTotalPercentageByKind(Fund.Kind kind) {

        switch (kind) {
            case PL:
                return plPercentage;
            case FOREIGN:
                return foreignPercentage;
            case MONETARY:
                return monetaryPercentage;
            default:
                throw InvestmentKindNotDefinedException.create(kind);
        }
    }

    public static InvestmentStyleDefinition getByInvestmentStyle(InvestmentStyle investmentStyle) {

        return Arrays.stream(values())
                .filter(def -> investmentStyle.equals(def.style))
                .findFirst()
                .orElseThrow(() -> InvestmentStyleDefinitionNotFoundException.create(investmentStyle));
    }
}
