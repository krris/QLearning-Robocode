package io.github.krris.qlearning.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.github.krris.qlearning.state.BoolRange;
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
    public static final int MOVE_DISTANCE = 50;
    // Default distance for turn actions
    public static final int TURN_ANGLE = 30;

    // Rewards:
    public static final int HIT_A_WALL = config.getInt("hitAWall");
    public static final int COLLISION_WITH_ENEMY = config.getInt("collisionWithEnemy");
    public static final int LIVING_REWARD = config.getInt("livingReward");
    public static final int HIT_BY_BULLET = config.getInt("hitByBullet");
    public static final int MY_BULLET_HITS_ROBOT = config.getInt("myBulletHitsRobot");

    public static final int LEARNING_ROUNDS = config.getInt("learningRounds");

    // Size of a chart with rewards per round
    public static final int CHART_WIDTH = 800;
    public static final int CHART_HEIGHT = 600;

    // Possible distances to enemy
    public static final IRange[] DISTANCES_TO_ENEMY = {
            new Range(0, 50, RangeType.DISTANCES_TO_ENEMY),
            new Range(50, 100, RangeType.DISTANCES_TO_ENEMY),
            new Range(100, 200, RangeType.DISTANCES_TO_ENEMY),
            new Range(200, 300, RangeType.DISTANCES_TO_ENEMY),
            new Range(300, 400, RangeType.DISTANCES_TO_ENEMY),
            new Range(400, 500, RangeType.DISTANCES_TO_ENEMY),
            new Range(500, Integer.MAX_VALUE, RangeType.DISTANCES_TO_ENEMY)
    };

    // Possible distances to wall
    public static final IRange[] DISTANCES_TO_WALL = {
            new Range(0, 19, RangeType.DISTANCES_TO_WALL),
//            new Range(19, 37, RangeType.DISTANCES_TO_WALL),
            new Range(19, Integer.MAX_VALUE, RangeType.DISTANCES_TO_WALL)
    };

    // Possible angles to enemy
    public static final IRange[] ANGLES_TO_ENEMY = {
            new Range(-180, -135, RangeType.ANGLES_TO_ENEMY),
            new Range(-135, -90, RangeType.ANGLES_TO_ENEMY),
            new Range(-90, -45, RangeType.ANGLES_TO_ENEMY),
            new Range(-45, 0, RangeType.ANGLES_TO_ENEMY),
            new Range(0, 45, RangeType.ANGLES_TO_ENEMY),
            new Range(45, 90, RangeType.ANGLES_TO_ENEMY),
            new Range(90, 135, RangeType.ANGLES_TO_ENEMY),
            new Range(135, 181, RangeType.ANGLES_TO_ENEMY),
    };

    // Enemy shot a bullet
    public static final IRange[] ENEMY_SHOT_A_BULLET = {
            new BoolRange(true, RangeType.ENEMY_SHOT_A_BULLET),
            new BoolRange(false, RangeType.ENEMY_SHOT_A_BULLET)
    };

    // Enemy's movement direction
    public static final IRange[] ENEMY_MOVEMENT_DIRECTION = {
            new Range(-180, -90, RangeType.ENEMY_MOVEMENT_DIRECTION),
//            new Range(-135, -90, RangeType.ENEMY_MOVEMENT_DIRECTION),
            new Range(-90, -1, RangeType.ENEMY_MOVEMENT_DIRECTION),
//            new Range(-45, -1, RangeType.ENEMY_MOVEMENT_DIRECTION),
            new Range(-1, 1, RangeType.ENEMY_MOVEMENT_DIRECTION), // stays in place
//            new Range(1, 45, RangeType.ENEMY_MOVEMENT_DIRECTION),
            new Range(1, 90, RangeType.ENEMY_MOVEMENT_DIRECTION),
//            new Range(90, 135, RangeType.ENEMY_MOVEMENT_DIRECTION),
            new Range(90, 181, RangeType.ENEMY_MOVEMENT_DIRECTION),
    };

    public static final IRange[][] ALL_RANGES = {
            DISTANCES_TO_ENEMY,
            DISTANCES_TO_WALL,
            ANGLES_TO_ENEMY,
            ENEMY_SHOT_A_BULLET,
            ENEMY_MOVEMENT_DIRECTION
    };
}
