package com.kbannach.funds.calculator.dependency;

import com.kbannach.funds.calculator.service.BlueServicesFundsCalculatorService;
import com.kbannach.funds.calculator.service.FundsCalculatorService;
import com.kbannach.funds.calculator.service.percentage.calculator.EqualPercentageFundPercentageCalculator;
import com.kbannach.funds.calculator.service.percentage.calculator.FundPercentageCalculator;
import com.kbannach.funds.calculator.service.percentage.corrector.OneElementCorrectingPercentageCorrector;

public final class DependencyContainer {

    private static FundsCalculatorService fundsCalculatorService;

    private DependencyContainer() {
    }

    public static FundsCalculatorService getFundsCalculatorService() {

        if (fundsCalculatorService == null) {
            OneElementCorrectingPercentageCorrector percentageCorrector = new OneElementCorrectingPercentageCorrector();
            FundPercentageCalculator fundPercentageCalculator = new EqualPercentageFundPercentageCalculator(percentageCorrector);
            fundsCalculatorService = new BlueServicesFundsCalculatorService(fundPercentageCalculator);
        }
        return fundsCalculatorService;
    }
}
