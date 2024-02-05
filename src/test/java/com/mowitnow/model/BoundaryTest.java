package com.mowitnow.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoundaryTest {

    @ParameterizedTest(name = "isOutOfBound true for coordinates({0}, {1})")
    @CsvSource({"-1, 5", "11, 5", "5, -1", "5, 11"})
    void isOutOfBounds_shouldReturnTrue_When_coordinates_outside(String x_st, String y_st) {

        // GIVEN
        int x = Integer.parseInt(x_st.trim());
        int y = Integer.parseInt(y_st.trim());
        Boundary boundary = new Boundary(0, 10, 0, 10);

        //ASSERT
        assertTrue(boundary.isOutOfBounds(x, y));
    }

    @ParameterizedTest(name = "isOutOfBound False for coordinates ({0}, {1})")
    @CsvSource({"0, 5", "10, 5", "5, 0", "4, 8"})
    void isOutOfBounds_shouldReturnFalse_When_coordinates_inside(Integer x_st, Integer y_st) {

        // GIVEN
        /*int x = x_st;
        int y = y_st;*/
        Boundary boundary = new Boundary(0, 10, 0, 10);

        //ASSERT
        assertFalse(boundary.isOutOfBounds(x_st, y_st));
    }
}