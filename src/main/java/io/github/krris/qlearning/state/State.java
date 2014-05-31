package io.github.krris.qlearning.state;

import com.google.common.base.Objects;
import io.github.krris.qlearning.GameStatus;
import io.github.krris.qlearning.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by krris on 15.03.14.
 */
public final class State implements Serializable {
    private transient static final Logger LOG = LoggerFactory.getLogger(State.class);

    private transient final GameStatus gameStatus;
    private final Range distanceToEnemy;
    private final Range angleToEnemy;

    public static class Builder {

        private GameStatus gameStatus;

        private Range distanceToEnemy;
        private Range angleToEnemy;
        public Builder distanceToEnemy(double distance) {
            this.distanceToEnemy = Range.getRange(distance, Constants.DISTANCES_TO_ENEMY);
            return this;
        }

        public Builder angleToEnemy(double angle) {
            this.angleToEnemy = Range.getRange(angle, Constants.ANGLES_TO_ENEMY);
            return this;
        }

        public Builder gameStatus(GameStatus gameStatus) {
            this.gameStatus = gameStatus;
            return this;
        }

        public Builder setProperty(Range range) {
            switch (range.getRangeType()) {
                case DISTANCES_TO_ENEMY:
                    this.distanceToEnemy = range;
                    return this;
                case ANGLES_TO_ENEMY:
                    this.angleToEnemy = range;
                    return this;
            }
            String message = "Range type not found";
            LOG.error(message);
            throw new IllegalStateException(message);
        }

        public State build() {
            return new State(this);
        }

    }
    private State(Builder builder) {
        this.gameStatus = builder.gameStatus;
        this.distanceToEnemy = builder.distanceToEnemy;
        this.angleToEnemy = builder.angleToEnemy;
    }

    public static State updateState(final GameStatus gameStatus) {
        State state = new Builder()
                .gameStatus(gameStatus)
                .distanceToEnemy(gameStatus.getDistanceToEnemy())
                .angleToEnemy(gameStatus.getAngleToEnemy())
                .build();
        return state;
    }

    public GameStatus getGameStatus() {
        // TODO return defensive copy
        return gameStatus;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (other instanceof State) {
            return (((State)other).distanceToEnemy.equals(this.distanceToEnemy) &&
                    ((State)other).angleToEnemy.equals(this.angleToEnemy));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                this.distanceToEnemy,
                this.angleToEnemy);
    }

    @Override
    public String toString() {
        return "ToEnemy: " + distanceToEnemy +
                " AngleToEnemy: " + angleToEnemy;
    }
}
