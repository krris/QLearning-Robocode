package io.github.krris.qlearning;

import io.github.krris.qlearning.action.Action;
import io.github.krris.qlearning.feature.Feature;
import io.github.krris.qlearning.feature.FeatureExtractor;
import io.github.krris.qlearning.state.State;
import io.github.krris.qlearning.util.Constants;
import io.github.krris.qlearning.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import robocode.RobocodeFileOutputStream;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
        super.init();
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
            initWeight(feature);
            q += this.weights.get(feature) * featureValue;
        }
        return q;
    }

    private void initWeight(Feature feature) {
        if (this.weights.get(feature) == null) {
            this.weights.put(feature, Constants.WEIGHT_INIT_VALUE);
        }
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
        LOG.debug("UpdateQ() for action: {}", action);
        double reward = rewards.getCycleReward();
        LOG.info("CycleReward: {}", reward);
        double difference = reward + Constants.GAMMA * this.maxQ(nextState) - this.getQValue(state, action);
        LOG.info("Difference: {}", difference);

        double newQ = this.Q.get(state, action) + Constants.ALPHA * difference;
        LOG.debug("Old Q: [{}], new Q: [{}]", Q.get(state, action), newQ);
        this.Q.put(state, action, newQ);

        Map<Feature, Double> features = FeatureExtractor.getFeatures(state, action);
        for (Map.Entry<Feature, Double> entry : features.entrySet()) {
            Feature feature = entry.getKey();
            double featureValue = entry.getValue();
            double newWeight = this.weights.get(feature) + Constants.ALPHA * difference * featureValue;
            LOG.debug("Old weight for feature [{}]: [{}], new w.: [{}]",feature , this.weights.get(feature), newWeight);
            this.weights.put(feature, newWeight);
        }
    }

    public void serializeWeights(File file)
    {
        LOG.info("Serialization of weights");
        try {
            int i = Util.sizeof(this.weights);
            LOG.info("Size of object to serialize: {}", i);

            RobocodeFileOutputStream fileOut = new RobocodeFileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.Q);
            out.close();
            fileOut.close();
            LOG.info("Serialization: weights serialized succesfully");
        } catch (IOException e) {
            LOG.info("IOException trying to write!");
            e.printStackTrace();
        }
    }
}
