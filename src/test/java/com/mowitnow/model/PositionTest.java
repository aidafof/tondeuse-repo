package com.mowitnow.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PositionTest {
    private Boundary bounds;

    @BeforeEach
    public void setup() {
        bounds = new Boundary(0, 5, 0, 5);
    }

    @Test
    void instanciation_raise_exception_when_invalid_absissa() {
        // when and then
        assertThrows(IllegalArgumentException.class,
                () -> new Position(-1, 1));
    }

    @Test
    void instanciation_raise_exception_when_invalid_ordonat() {
        // when and then
        assertThrows(IllegalArgumentException.class,
                () -> new Position(5, -1));
    }

    @Test
    void moveToNorth_insideBounds_should_succeed() {
        //GIVEN
        Position initial_position = new Position(4, 3);
        Position expectedPosition = new Position(4, 4);

        //WHEN
        Position newPosition = initial_position.moveToNorth(bounds);
        assertEquals(expectedPosition, newPosition);

    }

    @Test
    void moveToNorth_outside_Bounds_should_return_initial_position() {
        //GIVEN
        Position initial_position = new Position(3, 5);

        //WHEN
        Position newPosition = initial_position.moveToNorth(bounds);
        assertEquals(initial_position, newPosition);
    }

    @Test
    void moveToEst_insideBounds_should_succeed() {
        //GIVEN
        Position initial_position = new Position(3, 3);
        Position expectedPosition = new Position(4, 3);

        //WHEN
        Position newPosition = initial_position.moveToEast(bounds);
        assertEquals(expectedPosition, newPosition);

    }

    @Test
    void moveToEast_outside_Bounds_should_return_initial_position() {
        //GIVEN
        Position initial_position = new Position(5, 3);

        //WHEN
        Position newPosition = initial_position.moveToEast(bounds);
        assertEquals(initial_position, newPosition);
    }

    @Test
    void moveToSouth_insideBounds_should_succeed() {
        //GIVEN
        Position initial_position = new Position(3, 3);
        Position expectedPosition = new Position(3, 2);

        //WHEN
        Position newPosition = initial_position.moveToSouth(bounds);
        assertEquals(expectedPosition, newPosition);
    }

    @Test
    void moveToSouth_outside_Bounds_should_return_initial_position() {
        //GIVEN
        Position initial_position = new Position(3, 0);

        //WHEN
        Position newPosition = initial_position.moveToSouth(bounds);
        assertEquals(initial_position, newPosition);
    }

    @Test
    void moveToWest_insideBounds_should_succeed() {
        //GIVEN
        Position initial_position = new Position(3, 3);
        Position expectedPosition = new Position(2, 3);

        //WHEN
        Position newPosition = initial_position.moveToWest(bounds);
        assertEquals(expectedPosition, newPosition);

    }

    @Test
    void moveToWest_outside_Bounds_should_return_initial_position() {
        //GIVEN
        Position initial_position = new Position(0, 4);

        //WHEN
        Position newPosition = initial_position.moveToWest(bounds);
        assertEquals(initial_position, newPosition);
    }
}
