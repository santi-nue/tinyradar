package dev.mf1.tinyradar.core.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IcaoRangeTest {

    @Test
    void shouldMatchIcaoCodeWithCountryCode() {
        assertEquals("de", IcaoRange.getCountryCode("3c5424"));
        assertEquals("fi", IcaoRange.getCountryCode("461E16"));
        assertEquals("gr", IcaoRange.getCountryCode("46B82D"));
        assertEquals("tr", IcaoRange.getCountryCode("4BCDE1"));
    }

}