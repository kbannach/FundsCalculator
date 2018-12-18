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
        assertTrue(BigDecimal.ONE.compareTo(actual) == 0);
    }

    @Test
    public void toBigDecimalGivenString() {
        // given
        String toConvert = "1";

        // when
        BigDecimal actual = ConversionUtils.toBigDecimal(toConvert);

        // then
        assertTrue(BigDecimal.ONE.compareTo(actual) == 0);
    }

    @Test
    public void toBigDecimalGivenComplexString() {
        // given
        String toConvert = "-1.23E-12";
        BigDecimal expected = new BigDecimal("-1.23E-12");

        // when
        BigDecimal actual = ConversionUtils.toBigDecimal(toConvert);

        // then
        assertTrue(expected.compareTo(actual) == 0);
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
        BigDecimal actual = ConversionUtils.roundPercentage(rounded);

        // then
        assertEquals(rounded, actual);
    }

    @Test
    public void roundPercentageGivenNotRounded() {
        // given
        BigDecimal expected = new BigDecimal("0.12");
        BigDecimal toRound = new BigDecimal("0.12345");

        // when
        BigDecimal actual = ConversionUtils.roundPercentage(toRound);

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void roundPercentageGivenRoundedMoreThenRoundingNotChanged() {
        // given
        BigDecimal toRound = BigDecimal.ONE;
        BigDecimal expected = new BigDecimal("1.00");

        // when
        BigDecimal actual = ConversionUtils.roundPercentage(toRound);

        // then
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void roundPercentageGivenNullThenNPE() {
        // when
        ConversionUtils.roundPercentage(null);
    }
}