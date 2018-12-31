package com.kbannach.funds.calculator.utils;

import com.kbannach.UnitTest;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConversionUtilsTest extends UnitTest {

    @Test
    public void toBigDecimalGivenInteger() {
        // given
        int toConvert = 1;

        // when
        BigDecimal actual = ConversionUtils.toBigDecimal(toConvert);

        // then
        assertBigDecimalValues(BigDecimal.ONE, actual);
    }

    @Test
    public void toBigDecimalGivenString() {
        // given
        String toConvert = "1";

        // when
        BigDecimal actual = ConversionUtils.toBigDecimal(toConvert);

        // then
        assertBigDecimalValues(BigDecimal.ONE, actual);
    }

    @Test(expected = NumberFormatException.class)
    public void toBigDecimalGivenNotNumberString() {
        // given
        String toConvert = "notNumber";

        // when
        ConversionUtils.toBigDecimal(toConvert);
    }

    @Test(expected = NullPointerException.class)
    public void toBigDecimalGivenNullThenNPE() {
        // when
        ConversionUtils.toBigDecimal(null);
    }

    @Test
    public void roundPercentageGivenRounded() {
        // given
        BigDecimal rounded = new BigDecimal("0.12");

        // when
        BigDecimal actual = ConversionUtils.round(rounded);

        // then
        assertEquals(rounded, actual);
    }

    @Test
    public void roundPercentageGivenNotRounded() {
        // given
        BigDecimal expected = new BigDecimal("0.12");
        BigDecimal toRound = new BigDecimal("0.12345");

        // when
        BigDecimal actual = ConversionUtils.round(toRound);

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void roundPercentageGivenRoundedMoreThenRoundingNotChanged() {
        // given
        BigDecimal toRound = BigDecimal.ONE;
        BigDecimal expected = new BigDecimal("1.00");

        // when
        BigDecimal actual = ConversionUtils.round(toRound);

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void percentageOfValueGivenOneHoundredPercentageThenReturnsValue() {
        // given
        BigDecimal value = new BigDecimal("200");
        BigDecimal percentage = new BigDecimal("100");

        // when
        BigDecimal actual = ConversionUtils.percentageOfValue(value, percentage);

        // then
        BigDecimal expected = new BigDecimal("200");
        assertTrue(expected.compareTo(actual) == 0);
    }

    @Test
    public void percentageOfValueGivenPercentageAndValueThenReturnsPercentageOfValue() {
        // given
        BigDecimal value = new BigDecimal("200");
        BigDecimal percentage = new BigDecimal("20");

        // when
        BigDecimal actual = ConversionUtils.percentageOfValue(value, percentage);

        // then
        BigDecimal expected = new BigDecimal("40");
        assertTrue(expected.compareTo(actual) == 0);
    }

    @Test(expected = NullPointerException.class)
    public void percentageOfValueGivenNullValueThenNPE() {
        // given
        BigDecimal value = null;
        BigDecimal percentage = new BigDecimal("20");

        // when
        ConversionUtils.percentageOfValue(value, percentage);
    }

    @Test(expected = NullPointerException.class)
    public void percentageOfValueGivenNullPercentageThenNPE() {
        // given
        BigDecimal value = new BigDecimal("20");
        BigDecimal percentage = null;

        // when
        ConversionUtils.percentageOfValue(value, percentage);
    }

    @Test(expected = NullPointerException.class)
    public void roundPercentageGivenNullThenNPE() {
        // when
        ConversionUtils.round(null);
    }
}