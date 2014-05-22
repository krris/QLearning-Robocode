package io.github.krris.qlearning;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import io.github.krris.qlearning.action.Action;
import io.github.krris.qlearning.action.Executable;
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
    private Rewards rewards;

    QLearning() {
        this.Q = HashBasedTable.create();
        this.actionFunctions = new HashMap<>();
    }

    public void init() {
        if (Q.isEmpty()) {
            areAllActionsSet();
            initQ();
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

    public void setActionFunction(Action action, Executable function) {
        this.actionFunctions.put(action, function);
    }

    public Action eGreedyAction(State state) {
        double x = Math.random();

        if (x < Constants.EPSILON) {
            Action randomAction = randomAction();
            LOG.debug("Random action: " + randomAction.toString());
            return randomAction;
        } else {
            Action bestAction = bestAction(state);
            LOG.debug("Best action: " + bestAction.toString());
            return bestAction;
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

        return randomAction;
    }

    public Action bestAction(State state) {
        Action bestAction = this.randomAction();
        double bestQ = getQValue(state, bestAction);

        for (Action action : Action.values()) {
            double actionQ = getQValue(state, action);

            if (actionQ > bestQ) {
                bestQ = actionQ;
                bestAction = action;
            }
        }

        return bestAction;
    }

    public double getQValue(State state, Action action) {
        return Q.get(state, action);
    }

    /**
     * Overall algorithm for updataing Q:
     *      Q(s,a) <- (1 - alpha) Q(s,a) + (alpha) [r + gamma max_action' Q(s',a')]
     *
     * @param state
     * @param action
     * @param nextState
     */
    public void updateQ(State state, Action action, State nextState) {

        LOG.debug("UpdateQ() for action: {}", action);
        double reward = rewards.getCycleReward();
        LOG.info("CycleReward: {}", reward);
        double newQValue = (1 - Constants.ALPHA) * this.getQValue(state, action) +
                Constants.ALPHA * (reward + Constants.GAMMA * this.maxQ(nextState));

        LOG.debug("Old Q: [{}], new Q: [{}]", Q.get(state, action), newQValue);
        Q.put(state, action, newQValue);
    }

    /**
     *
     * @param state
     * @return max_action Q(state, action)
     */
    private double maxQ(State state) {
        double max = - Double.MAX_VALUE;
        for (Action action : Q.columnKeySet()) {
            if (getQValue(state, action) > max) {
                max = getQValue(state, action);
            }
        }
        return max;
    }

    public Action nextAction(State state, int roundNo) {
        if (roundNo >= Constants.BEST_ACTION_TRESHOLD){
            LOG.debug("IF Best action treshold");
            return  bestAction(state);
        }
        return eGreedyAction(state);
    }

    public Table<State, Action, Double> getQTable() {
        return Q;
    }

    public void setRewards(Rewards rewards) {
        this.rewards = rewards;
    }

}
