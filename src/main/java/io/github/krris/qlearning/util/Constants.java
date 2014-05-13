package io.github.krris.qlearning.util;

import io.github.krris.qlearning.state.Range;
import io.github.krris.qlearning.state.RangeType;

/**
 * Created by krris on 15.03.14.
 */
public class Constants {
    // Place where chart will be printed
    public static final String CHART_PATH = System.getProperty("user.home") + "/log/chart.png";
    public static final double TEMP = 0.3;

    private Constants() { } // Prevents initialization

    // Constants for qlearning algorithm
    public static final double EPSILON = 0.1;
    public static final double FEATURE_INIT_VALUE = 1;
    public static final double INITIAL_Q = 0;
    public static final double GAMMA = 0.9;
    public static final double ALPHA = 0.5;

    // Default distance for movement actions
    public static final int MOVE_DISTANCE = 15;
    // Default distance for turn actions
    public static final int TURN_ANGLE = 90;

    // Rewards:
    public static final int HIT_A_WALL = -10000;
//    public static final int COLLISION_WITH_ENEMY = -1;

    // TODO to delete
    public static final int BEST_ACTION_TRESHOLD = 80;

    // Possible distances to enemy
    public static final Range[] DISTANCES_TO_ENEMY = {
            new Range(0, 20, RangeType.DISTANCES_TO_ENEMY),
            new Range(20, Integer.MAX_VALUE, RangeType.DISTANCES_TO_ENEMY)
    };

    // Possible distances to the wall
    public static final Range[] DISTANCES_TO_WALL = {
            new Range(0, 20, RangeType.DISTANCES_TO_WALL),
            new Range(20, Integer.MAX_VALUE, RangeType.DISTANCES_TO_WALL)
    };

    public static final Range[][] ALL_RANGES = {DISTANCES_TO_ENEMY, DISTANCES_TO_WALL};

    public static final int CHART_WIDTH = 800;
    public static final int CHART_HEIGHT = 600;
}
