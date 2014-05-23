package io.github.krris.qlearning.feature;

import io.github.krris.qlearning.GameStatus;
import io.github.krris.qlearning.action.Action;
import io.github.krris.qlearning.state.State;
import io.github.krris.qlearning.util.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by krris on 22.05.14.
 */
public class FeatureExtractor {
    public static Map<Feature, Double> getFeatures(State state, Action action) {
        Map<Feature, Double> features = new HashMap<>();

        features.put(Feature.BIAS, 1.0);  // test feature
        features.put(Feature.ANGLE_TO_ENEMY, getAngleToEnemyFeature(state, action));
        features.put(Feature.DISTANCE_TO_ENEMY, getDistanceToEnemyFeature(state, action));
        features.put(Feature.DISTANCE_TO_WALL, getDistanceToWallFeature(state, action));
        features.put(Feature.ME_ENERGY, getMyEnergyFeature(state));
        return features;
    }

    private static double getAngleToEnemyFeature(State state, Action action) {
        GameStatus status = state.getGameStatus();
        double[] newCoords = Util.getCoordinatesAfterAction(state, action);
        double newX = newCoords[0];
        double newY = newCoords[1];

        return Util.angleToEnemy(newX, newY, status.getEnemyX(), status.getEnemyY());
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

        return Util.distanceBetween2Points(newX, newY, status.getEnemyX(), status.getEnemyY());
    }

    private static double getDistanceToWallFeature(State state, Action action) {
        GameStatus status = state.getGameStatus();
        double[] newCoords = Util.getCoordinatesAfterAction(state, action);
        double newX = newCoords[0];
        double newY = newCoords[1];

        return status.getDistanceToNearestWall(newX, newY);
    }

    private static double getMyEnergyFeature(State state) {
        GameStatus status = state.getGameStatus();
        return status.getMyEnergy();
    }
}
