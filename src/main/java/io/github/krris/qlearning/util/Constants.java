package io.github.krris.qlearning.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.github.krris.qlearning.state.IRange;
import io.github.krris.qlearning.state.Range;
import io.github.krris.qlearning.state.RangeType;

import java.io.File;

/**
 * Created by krris on 15.03.14.
 */
public class Constants {
    private static Config config = ConfigFactory.parseFile(new File("/home/krris/programowanie/idea-robot/QLearning-Robocode/application.conf"));
    // TODO wrong approach -> to change
    // Place where rewards are saved
    public static final String REWARDS_PATH = config.getString("rewardsPath");
    // Place where chart will be printed
    public static final String CHART_PATH = config.getString("chartPath");
    public static final String TICKS_CHART_PATH = config.getString("ticksChartPath");

    public static final String TICKS_PATH = config.getString("ticksPath");
    public static final String BATTLE_CONFIG_PATH = config.getString("battleConfigPath");
    public static final String serializedQFilePath = config.getString("serializedQFile");

    public static final String serializedWeightsFilePath = config.getString("serializedWeightsFile");
    private Constants() { } // Prevents initialization

    // Constants for qlearning algorithm
    public static final double EPSILON = config.getDouble("epsilon");

    public static final double GAMMA = config.getDouble("gamma");
    public static final double ALPHA = config.getDouble("alpha");
    public static final double WEIGHT_INIT_VALUE = 1;
    public static final double INITIAL_Q = 0;
    // Default distance for movement actions
    public static final int MOVE_DISTANCE = 25;

    // Default distance for turn actions
    public static final int TURN_ANGLE = 30;
    // Rewards:
    public static final int HIT_A_WALL = config.getInt("hitAWall");

    public static final double COLLISION_WITH_ENEMY = config.getDouble("collisionWithEnemy");
    public static final double LIVING_REWARD = config.getDouble("livingReward");
    public static final double HIT_BY_BULLET = config.getDouble("hitByBullet");
    public static final double COLLISION_AND_KILL_ENEMY = config.getDouble("collisionAndKillEnemy");
    public static final double DISTANCE_TO_ENEMY_LESS_THAN_200 = config.getDouble("distanceToEnemyLessThan200");
    public static final double DISTANCE_TO_ENEMY_LESS_THAN_50 = config.getDouble("distanceToEnemyLessThan50");

    public static final int LEARNING_ROUNDS = config.getInt("learningRounds");

    // Size of a chart with rewards per round
    public static final int CHART_WIDTH = 800;
    public static final int CHART_HEIGHT = 600;

    public static final IRange[] MY_ENERGIES = {
            new Range(0, 10, RangeType.MY_ENERGY),
            new Range(10, Integer.MAX_VALUE, RangeType.MY_ENERGY)
    };

    public static final IRange[] OPONNENT_ENERGIES = {
            new Range(0, 10, RangeType.OPPONENT_ENERGY),
            new Range(10, Integer.MAX_VALUE, RangeType.OPPONENT_ENERGY)
    };

    // Possible distances to enemy
    public static final IRange[] DISTANCES_TO_ENEMY = {
            new Range(0, 100, RangeType.DISTANCES_TO_ENEMY),
            new Range(100, 500, RangeType.DISTANCES_TO_ENEMY),
            new Range(500, Integer.MAX_VALUE, RangeType.DISTANCES_TO_ENEMY)
    };

    // Possible angles to enemy
    public static final IRange[] ANGLES_TO_ENEMY = {
            new Range(-180, -90, RangeType.ANGLES_TO_ENEMY),
            new Range(-90, 0, RangeType.ANGLES_TO_ENEMY),
            new Range(0, 90, RangeType.ANGLES_TO_ENEMY),
            new Range(90, 181, RangeType.ANGLES_TO_ENEMY),
    };

    // Possible distances to wall
    public static final IRange[] DISTANCES_TO_WALL = {
            new Range(0, 50, RangeType.DISTANCES_TO_WALL),
            new Range(50, Integer.MAX_VALUE, RangeType.DISTANCES_TO_WALL)
    };

    public static final IRange[][] ALL_RANGES = {
            MY_ENERGIES,
            OPONNENT_ENERGIES,
            DISTANCES_TO_ENEMY,
            ANGLES_TO_ENEMY,
            DISTANCES_TO_WALL,
    };
}
