package com.kbannach.funds.calculator.service.definition;

import com.kbannach.funds.calculator.entity.Fund;

class InvestmentKindNotDefinedException extends RuntimeException {

    static InvestmentKindNotDefinedException create(Fund.Kind kind) {

        String message = String.format("No %s kind defined.", kind.name());
        return new InvestmentKindNotDefinedException(message);
    }

    private InvestmentKindNotDefinedException(String message) {
        super(message);
    }
}
