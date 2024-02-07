package com.mowitnow.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Class to manage position (x, y) of the mower in the given orientations :North, Est,West and South
 * within the lawun area boundaries
 */
@Getter
@EqualsAndHashCode
public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("invalid position :negatives values are not allowed ");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Defines the new position reach after moving in the NORTH if the new postion
     * is outside boundaries vthe current postion is returned
     * @param lawnBoundary: Lawn boundaries
     * @return position : final position
     */
    public Position moveToNorth(Boundary lawnBoundary) {
        return lawnBoundary.isOutOfBounds(x, y + 1) ? this : new Position(x, y + 1);
    }

    /**
     * Compute the new position reach after moving in the EAST if the new postion
     * is outside boundaries vthe current postion is returned
     * @param lawnBoundary : input boundary instance
     * @return position : final position
     */
    public Position moveToEast( Boundary lawnBoundary) {
        return lawnBoundary.isOutOfBounds(x + 1, y) ? this : new Position(x + 1, y);
    }

    /**
     * Compute the new position reach after moving in the WEST if the new postion
     * is outside boundaries the initial current postion is returned
     * @param lawnBoundary: input boundary instance
     * @return position : final position
     */
    public Position moveToWest( Boundary lawnBoundary) {
        return lawnBoundary.isOutOfBounds(x - 1, y) ? this : new Position(x - 1, y);
    }

    /**
     * Compute the new position reach after moving in the SOUTH if the new postion
     * is outside boundaries the initial current postion is returned
     * @param lawnBoundary : input boundary instance
     * @return position : final position
     */
    public Position moveToSouth( Boundary lawnBoundary) {
        return lawnBoundary.isOutOfBounds(x, y - 1) ? this : new Position(x, y - 1);
    }

}
