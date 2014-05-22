package io.github.krris.qlearning.feature;

import io.github.krris.qlearning.action.Action;
import io.github.krris.qlearning.state.State;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by krris on 22.05.14.
 */
public class FeatureExtractor {
    public static Map<Feature, Double> getFeatures(State state, Action action) {
        Map<Feature, Double> features = new HashMap<>();
        // TODO
        double angleToEnemy = 0;
        double distanceToWall = 0;
        double distanceToEnemy = 0;
        features.put(Feature.ANGLE_TO_ENEMY, angleToEnemy);
        features.put(Feature.DISTANCE_TO_ENEMY, distanceToEnemy);
        features.put(Feature.DISTANCE_TO_WALL, distanceToWall);
        return features;
    }
}
