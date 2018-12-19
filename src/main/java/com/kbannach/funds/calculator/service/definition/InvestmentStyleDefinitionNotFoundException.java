package com.kbannach.funds.calculator.service.definition;

import com.kbannach.funds.calculator.api.InvestmentStyle;

class InvestmentStyleDefinitionNotFoundException extends RuntimeException {

    static InvestmentStyleDefinitionNotFoundException create(InvestmentStyle investmentStyle) {

        String message = String.format("%s style not defined.", investmentStyle.name());
        return new InvestmentStyleDefinitionNotFoundException(message);
    }

    private InvestmentStyleDefinitionNotFoundException(String message) {
        super(message);
    }
}
