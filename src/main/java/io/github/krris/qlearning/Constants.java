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
            new Range(0, 50),
            new Range(50,100),
            new Range(100, Integer.MAX_VALUE)
    };

    // Possible distances to the wall
    public static final Range[] DISTANCES_TO_WALL = {
            new Range(0, 10),
            new Range(10, 30),
            new Range(30, Integer.MAX_VALUE)
    };
}
