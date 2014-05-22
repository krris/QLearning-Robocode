package io.github.krris.qlearning;

import io.github.krris.qlearning.action.Action;
import io.github.krris.qlearning.feature.Feature;
import io.github.krris.qlearning.feature.FeatureExtractor;
import io.github.krris.qlearning.state.State;
import io.github.krris.qlearning.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by krris on 22.05.14.
 */
public class ApproximateQLearning extends QLearning{
    private final Logger LOG = LoggerFactory.getLogger(ApproximateQLearning.class);

    private Map<Feature, Double> weights;

    ApproximateQLearning() {
        super();
        this.weights = new HashMap<>();
    }

    @Override
    public void init() {
        if (weights.isEmpty()) {
            initWeights();
        }
    }

    private void initWeights() {
        for (Feature feature : Feature.values()) {
            this.weights.put(feature, Constants.FEATURE_INIT_VALUE);
        }
    }

    /**
     * Q(s,a) = weight_1 * feature_1(s,a) + weight_2 * feature_2(s,a) + ... + weight_n * feature_n(s,a)
     *
     * @param state
     * @param action
     * @return Q(state,action) = weights * featureVector
     */
    @Override
    public double getQValue(State state, Action action) {
        double q = 0;
        Map<Feature, Double> features = FeatureExtractor.getFeatures(state, action);
        for (Map.Entry<Feature, Double> entry : features.entrySet()) {
            Feature feature = entry.getKey();
            double featureValue = entry.getValue();
            q += this.weights.get(feature) * featureValue;
        }
        return q;
    }

    /**
     * Overall algorithm for updataing Q:

     *  difference = [r + gamma * max Q(s',a')] - Q(s,a)
     *  Q(s,a) <- Q(s,a) + alpha * difference
     *
     * @param state
     * @param action
     * @param nextState
     */
    @Override
    public void updateQ(State state, Action action, State nextState) {
        double reward = rewards.getCycleReward();
        double difference = reward + Constants.GAMMA * this.maxQ(nextState) - this.getQValue(state, action);

        double newQ = this.Q.get(state, action) + Constants.ALPHA * difference;
        this.Q.put(state, action, newQ);

        Map<Feature, Double> features = FeatureExtractor.getFeatures(state, action);
        for (Map.Entry<Feature, Double> entry : features.entrySet()) {
            Feature feature = entry.getKey();
            double featureValue = entry.getValue();
            double newWeight = this.weights.get(feature) + Constants.ALPHA * difference * featureValue;
            this.weights.put(feature, newWeight);
        }
    }
}
