package io.github.krris.qlearning.feature;

import io.github.krris.qlearning.GameStatus;
import io.github.krris.qlearning.action.Action;
import io.github.krris.qlearning.state.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by krris on 25.03.14.
 */
public enum Feature {
//    DISTANCE_TO_ENEMY,
    DISTANCE_TO_WALL;

    private Logger LOG = LoggerFactory.getLogger(Feature.class);

    /**
     * Get value of the feature after executing action in a given state.
     * @param state
     * @param executedAction
     * @return
     */
    public double getValue(State state, Action executedAction) {
        GameStatus gameStatus = state.getGameStatus().getStatusAfterExecutingAction(executedAction);
        switch (this) {
//            case DISTANCE_TO_ENEMY:
//                return gameStatus.getDistanceToEnemy();
            case DISTANCE_TO_WALL:
                return gameStatus.getDistanceToNearestWall();
            default:
                String message = "Feature [" + this.name() + "] is not supported.";
                LOG.error(message);
                throw new IllegalArgumentException(message);
        }
    }
}
