package com.kbannach.funds.calculator.utils;

import com.kbannach.UnitTest;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

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

    @Test(expected = NullPointerException.class)
    public void roundPercentageGivenNullThenNPE() {
        // when
        ConversionUtils.round(null);
    }
}