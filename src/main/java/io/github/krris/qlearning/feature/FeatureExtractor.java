package io.github.krris.qlearning.feature;

import io.github.krris.qlearning.GameStatus;
import io.github.krris.qlearning.action.Action;
import io.github.krris.qlearning.state.State;
import io.github.krris.qlearning.util.Util;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.max;

/**
 * Created by krris on 22.05.14.
 */
public class FeatureExtractor {
    public static Map<Feature, Double> getFeatures(State state, Action action) {
        Map<Feature, Double> features = getSimpleFeatures(state, action);
//        double x = 1000;
//        for (Map.Entry<Feature, Double> entry : features.entrySet()) {
//            features.put(entry.getKey(), entry.getValue() / x);
//        }
        return features;
    }

    public static Map<Feature, Double> getIdentityFeatures(State state, Action action) {
        Map<Feature, Double> features = new HashMap<>();
        features.put(new IdentityFeatureImpl(state, action), 1.0);
        return features;
    }

    public static Map<Feature, Double> getSimpleFeatures(State state, Action action) {
        Map<Feature, Double> features = new HashMap<>();

//        features.put(FeatureImpl.BIAS, 1.0);  // test feature
        features.put(FeatureImpl.ANGLE_TO_ENEMY, getAngleToEnemyFeature(state, action));
        features.put(FeatureImpl.DISTANCE_TO_ENEMY, getDistanceToEnemyFeature(state, action));
        features.put(FeatureImpl.DISTANCE_TO_WALL, getDistanceToWallFeature(state, action));
        features.put(FeatureImpl.ME_ENERGY, getMyEnergyFeature(state));
        return features;
    }

    private static double getAngleToEnemyFeature(State state, Action action) {
        GameStatus status = state.getGameStatus();
        double[] newCoords = Util.getCoordinatesAfterAction(state, action);
        double newX = newCoords[0];
        double newY = newCoords[1];

        double angle = Util.angleToEnemy(newX, newY, status.getEnemyX(), status.getEnemyY());
        double minAngle = 0;
        double maxAngle = 360;
        return Util.normalize(angle, minAngle,maxAngle);
//        return angle;
    }

    /**
     * Compute distance to enemy after he takes an action
     * @return
     */
    private static double getDistanceToEnemyFeature(State state, Action action) {
        GameStatus status = state.getGameStatus();
        double[] newCoords = Util.getCoordinatesAfterAction(state, action);
        double newX = newCoords[0];
        double newY = newCoords[1];

        double distance = Util.distanceBetween2Points(newX, newY, status.getEnemyX(), status.getEnemyY());
        double minDistance = 0;
        double maxDistance = Util.distanceBetween2Points(0, 0, status.getBattleFieldWith(),
                status.getBattleFieldHeight());
        return Util.normalize(distance, minDistance, maxDistance);
//        return distance;
    }

    private static double getDistanceToWallFeature(State state, Action action) {
        GameStatus status = state.getGameStatus();
        double[] newCoords = Util.getCoordinatesAfterAction(state, action);
        double newX = newCoords[0];
        double newY = newCoords[1];

        double battlefieldWidth = status.getBattleFieldWith();
        double battlefieldHeight = status.getBattleFieldHeight();

        double distanceToWall = status.getDistanceToNearestWall(newX, newY);
        double minDistance = 0;
        return Util.normalize(distanceToWall, minDistance, max(battlefieldHeight, battlefieldWidth));

    }

    private static double getMyEnergyFeature(State state) {
        GameStatus status = state.getGameStatus();
        double energy = status.getMyEnergy();
        return Util.normalize(energy, 0, 100);
    }
}
