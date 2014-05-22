package io.github.krris.qlearning.util;

import io.github.krris.qlearning.state.Range;
import io.github.krris.qlearning.state.RangeType;

/**
 * Created by krris on 15.03.14.
 */
public class Constants {
    // Place where chart will be printed
    public static final String CHART_PATH = System.getProperty("user.home") + "/log/chart.png";
    public static final double TEMP = 1000;

    private Constants() { } // Prevents initialization

    // Constants for qlearning algorithm
    public static final double EPSILON = 0.05;
    public static final double FEATURE_INIT_VALUE = 1;
    public static final double INITIAL_Q = 0;
    public static final double GAMMA = 0.8;
    public static final double ALPHA = 0.2;

    // Default distance for movement actions
    public static final int MOVE_DISTANCE = 50;
    // Default distance for turn actions
    public static final int TURN_ANGLE = 30;

    // Rewards:
    public static final int HIT_A_WALL = -10;
    public static final int COLLISION_WITH_ENEMY = 5;
    public static final int LIVING_REWARD = -1;

    // TODO to delete
    public static final int BEST_ACTION_TRESHOLD = 3000;

    // Possible distances to enemy
    public static final Range[] DISTANCES_TO_ENEMY = {
            new Range(0, 50, RangeType.DISTANCES_TO_ENEMY),
            new Range(50, 100, RangeType.DISTANCES_TO_ENEMY),
            new Range(100, 200, RangeType.DISTANCES_TO_ENEMY),
            new Range(200, 300, RangeType.DISTANCES_TO_ENEMY),
            new Range(300, 400, RangeType.DISTANCES_TO_ENEMY),
            new Range(400, 500, RangeType.DISTANCES_TO_ENEMY),
            new Range(500, Integer.MAX_VALUE, RangeType.DISTANCES_TO_ENEMY)
    };

    // Possible distances to the wall
    public static final Range[] DISTANCES_TO_WALL = {
            new Range(0, 20, RangeType.DISTANCES_TO_WALL),
            new Range(20, Integer.MAX_VALUE, RangeType.DISTANCES_TO_WALL)
    };

    // Possible angles to enemy
    public static final Range[] ANGLES_TO_ENEMY = {
            new Range(-180, -135, RangeType.ANGLES_TO_ENEMY),
            new Range(-135, -90, RangeType.ANGLES_TO_ENEMY),
            new Range(-90, -45, RangeType.ANGLES_TO_ENEMY),
            new Range(-45, 0, RangeType.ANGLES_TO_ENEMY),
            new Range(0, 45, RangeType.ANGLES_TO_ENEMY),
            new Range(45, 90, RangeType.ANGLES_TO_ENEMY),
            new Range(90, 135, RangeType.ANGLES_TO_ENEMY),
            new Range(135, 180, RangeType.ANGLES_TO_ENEMY),
    };

    public static final Range[][] ALL_RANGES = {DISTANCES_TO_ENEMY, DISTANCES_TO_WALL, ANGLES_TO_ENEMY};

    public static final int CHART_WIDTH = 800;
    public static final int CHART_HEIGHT = 600;
}
