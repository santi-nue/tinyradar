package dev.mf1.tinyradar.core.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathUtilsTest {

    @Test
    void shouldCheckCoordinatesInRange() {
        assertFalse(MathUtils.inRange(40.6397, -73.7788, 42.6397, -72.7788, 5));
    }

}