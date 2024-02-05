package com.mowitnow.model;

import java.util.function.BiFunction;

/**
 * Record class defines the area boundaries
 * @param minWidth
 * @param maxWidth
 * @param minLength
 * @param maxLength
 */
public record Boundary(int minWidth, int maxWidth, int minLength, int maxLength) {

    public static final int MIN_WIDTH = 0;
    public static final int MIN_LENGTH = 0;
    /**
     * check if a position defines by its coordinates (x, y) is inside the area boundaries
     * @param absX :intial absissa value
     * @param ordY: initial ordinate
     * @return <b>true</b> if position coordinates are outside the area
     *
     */
    public  Boolean isOutOfBounds(int absX, int ordY) {
        BiFunction<Integer, Integer, Boolean> isOutOfBounds = (x, y) -> x < minWidth || x > maxWidth || y < minLength || y > maxLength;
        return isOutOfBounds.apply(absX, ordY);
    }
}
