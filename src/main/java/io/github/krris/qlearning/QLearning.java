package io.github.krris.qlearning;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import io.github.krris.qlearning.action.Action;
import io.github.krris.qlearning.action.Executable;
import io.github.krris.qlearning.feature.Feature;
import io.github.krris.qlearning.reward.Rewards;
import io.github.krris.qlearning.state.State;
import io.github.krris.qlearning.util.Constants;
import io.github.krris.qlearning.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by krris on 16.03.14.
 */
public class QLearning {
    private final Logger LOG = LoggerFactory.getLogger(QLearning.class);

    private Table<State, Action, Double> Q;
    private Map<Action, Executable> actionFunctions;
    private Map<Feature, Double> weights;
    private Rewards rewards;

    QLearning() {
        this.Q = HashBasedTable.create();
        this.actionFunctions = new HashMap<>();
        this.weights = new HashMap<>();
    }

    public void init() {
        if (Q.isEmpty()) {
            areAllActionsSet();
            initQ();
            initWeights();
        }
    }

    private void areAllActionsSet() {
        for (Action action : Action.values()) {
            if (!actionFunctions.containsKey(action)) {
                String message = "Function for action: " + action.name() + " is not defined.";
                LOG.error(message);
                throw new IllegalStateException(message);
            }
        }
    }

    private void initQ() {
        Set<State> availableStates = Util.allAvailableStates();
        for (State state : availableStates) {
            for (Action action : Action.values()) {
                Executable actionFunc = actionFunctions.get(action);
                action.setExecutableAction(actionFunc);
                Q.put(state, action, Constants.INITIAL_Q);
            }
        }
    }

    private void initWeights() {
        for (Feature feature : Feature.values()) {
            this.weights.put(feature, Constants.FEATURE_INIT_VALUE);
        }
    }

    public void setActionFunction(Action action, Executable function) {
        this.actionFunctions.put(action, function);
    }

    public Action eGreedyAction(State state) {
        double x = Math.random();

        if (x < Constants.EPSILON) {
            return randomAction();
        } else {
            return bestAction(state);
        }
    }

    public Action randomAction() {
        Random random = new Random();
        Set<Action> availableActions = Q.columnKeySet();

        int randomInt = random.nextInt(availableActions.size() + 1);
        Action randomAction = availableActions.iterator().next();
        Iterator<Action> iterator = availableActions.iterator();
        for (int i = 0; i < randomInt; i++) {
            randomAction = iterator.next();
        }

        LOG.debug("Random action: " + randomAction.toString());
        return randomAction;
    }

    public Action bestAction(State state) {
        Action bestAction = this.randomAction();
        double bestQ = Q.get(state, bestAction);

        for (Action action : Action.values()) {
            double actionQ = Q.get(state, action);

            if (actionQ > bestQ) {
                bestQ = actionQ;
                bestAction = action;
            }
        }

        LOG.debug("Best action: " + bestAction.toString());
        return bestAction;
    }

    public State randomState() {
        Random random = new Random();
        Set<State> availableStates = Util.allAvailableStates();

        int randomInt = random.nextInt(availableStates.size() + 1);
        State randomState = availableStates.iterator().next();
        Iterator<State> iterator = availableStates.iterator();
        for (int i = 0; i < randomInt; i++) {
            randomState = iterator.next();
        }
        return randomState;
    }

    /**
     * Overall algorithm for updataing Q:
     *      Q(s,a) = weight_1 * feature_1(s,a) + weight_2 * feature_2(s,a) + ... + weight_n * feature_n(s,a)
     *      difference = [r + gamma * max Q(s',a')] - Q(s,a)
     *      Q(s,a) <- Q(s,a) + alpha * difference
     *
     * @param currentState
     * @param executedAction
     */
    public void updateQ(State currentState, Action executedAction) {
        LOG.debug("UpdateQ()");
        // Q(s,a) = weight_1 * feature_1(s,a) + weight_2 * feature_2(s,a) + ... + weight_n * feature_n(s,a)
        double q = 0;
        for (Feature feature : Feature.values()) {
            q += this.weights.get(feature) * feature.getValue(currentState, executedAction);
        }

        // difference = [r + gamma * max Q(s',a')] - Q(s,a)
        // new hypothetical state after executing action
        State newState = currentState.nextHypotheticalState(executedAction);
        double maxQPrim = maxQ(newState);
        double difference = rewards.getCycleReward() + Constants.GAMMA * maxQPrim - q;

        // Q(s,a) <- Q(s,a) + alpha * difference
        double newQValue = Q.get(currentState, executedAction) + Constants.ALPHA * difference;
        Q.put(currentState, executedAction, newQValue);

        updateWeights(currentState, executedAction, difference);
    }

    /**
     * Algorithm for updating weights:
     *      w_1 <- w_1 + alpha * difference * feature_1(s,a)
     *      w_2 <- w_2 + alpha * difference * feature_2(s,a)
     *      (...)
     *
     * @param currentState
     * @param executedAction
     */
    private void updateWeights(State currentState, Action executedAction, double difference) {
        for (Feature feature : weights.keySet()) {
            double currentWeight = weights.get(feature);
            double newWeigth = currentWeight + Constants.ALPHA * feature.getValue(currentState, executedAction);
            weights.put(feature, newWeigth);
        }
    }

    private double maxQ(State state) {
        double max = - Double.MAX_VALUE;
        for (Action action : Q.columnKeySet()) {
            if (Q.get(state, action) > max) {
                max = Q.get(state, action);
            }
        }
        return max;
    }

    public Action nextAction(State state) {
        return eGreedyAction(state);
    }

    public Table<State, Action, Double> getQ() {
        return Q;
    }

    public void setRewards(Rewards rewards) {
        this.rewards = rewards;
    }

    public Map<Feature, Double> getWeights() {
        return weights;
    }

}
