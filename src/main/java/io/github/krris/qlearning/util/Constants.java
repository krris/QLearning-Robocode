package io.github.krris.qlearning.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.github.krris.qlearning.state.Range;
import io.github.krris.qlearning.state.RangeType;

import java.io.File;

/**
 * Created by krris on 15.03.14.
 */
public class Constants {
    private static Config config = ConfigFactory.load();
    private static final String baseFolder = new File("").getAbsolutePath();

    // TODO wrong approach -> to change
    // Place where rewards are saved
    public static final String REWARDS_PATH = config.getString("rewardsPath");
    // Place where chart will be printed
    public static final String CHART_PATH = config.getString("chartPath");
    public static final String BATTLE_CONFIG_PATH = config.getString("battleConfigPath");

    public static String serializedQFilePath = baseFolder + config.getString("serializedQFile");
    public static String serializedWeightsFilePath = baseFolder + config.getString("serializedWeightsFile");

    private Constants() { } // Prevents initialization

    // Constants for qlearning algorithm
    public static final double EPSILON = config.getDouble("epsilon");
    public static final double GAMMA = config.getDouble("gamma");
    public static final double ALPHA = config.getDouble("alpha");
    public static final double WEIGHT_INIT_VALUE = 1;
    public static final double INITIAL_Q = 0;

    // Default distance for movement actions
    public static final int MOVE_DISTANCE = 50;
    // Default distance for turn actions
    public static final int TURN_ANGLE = 30;

    // Rewards:
    public static final int HIT_A_WALL = config.getInt("hitAWall");
    public static final int COLLISION_WITH_ENEMY = config.getInt("collisionWithEnemy");
    public static final int LIVING_REWARD = config.getInt("livingReward");

    public static final int LEARNING_ROUNDS = config.getInt("learningRounds");

    // Size of a chart with rewards per round
    public static final int CHART_WIDTH = 800;
    public static final int CHART_HEIGHT = 600;

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

    public static final Range[][] ALL_RANGES = {DISTANCES_TO_ENEMY, ANGLES_TO_ENEMY};
}
