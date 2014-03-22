package io.github.krris.qlearning;

/**
 * Created by krris on 15.03.14.
 */
public class Constants {
    private Constants() { } // Prevents initialization

    // Default distance for movement actions
    public static final int MOVE_DISTANCE = 50;
    // Default distance for turn actions
    public static final int TURN_ANGLE = 45;

    // Possible distances to enemy
    public static final Range[] DISTANCES_TO_ENEMY = {
            new Range(0, 50, RangeType.DISTANCES_TO_ENEMY),
            new Range(50,100, RangeType.DISTANCES_TO_ENEMY),
            new Range(100, Integer.MAX_VALUE, RangeType.DISTANCES_TO_ENEMY)
    };

    // Possible distances to the wall
    public static final Range[] DISTANCES_TO_WALL = {
            new Range(0, 10, RangeType.DISTANCES_TO_WALL),
            new Range(10, 30, RangeType.DISTANCES_TO_WALL),
            new Range(30, Integer.MAX_VALUE, RangeType.DISTANCES_TO_WALL)
    };

    public static final Range[][] ALL_RANGES = {DISTANCES_TO_ENEMY, DISTANCES_TO_WALL};

}
