package io.github.krris.qlearning;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import io.github.krris.qlearning.action.Action;
import io.github.krris.qlearning.action.Executable;
import io.github.krris.qlearning.state.State;
import io.github.krris.qlearning.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by krris on 16.03.14.
 */
public enum QLearning {
    INSTANCE;

    private final Logger LOG = LoggerFactory.getLogger(QLearning.class);

    private Table<State, Action, Double> Q;
    private Map<Action, Executable> actionFunctions = new HashMap<>();

    private static double INITIAL_Q = 1;

    QLearning() {
        this.Q = HashBasedTable.create();
    }

    public void init() {
        areAllActionsSet();
        initQ();
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
                Q.put(state, action, INITIAL_Q);
            }
        }
    }

    public void setActionFunction(Action action, Executable function) {
        this.actionFunctions.put(action, function);
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

    // FIXME
    public void updateQ() {}
    public Action nextAction() {
        return randomAction();
    }
}
