package com.kbannach;

import org.junit.Rule;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class UnitTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    protected void assertBigDecimalValues(BigDecimal expected, BigDecimal actual) {

        String message = String.format("Expected: %s, actual: %s", expected, actual);
        assertTrue(message, expected.compareTo(actual) == 0);
    }
}
