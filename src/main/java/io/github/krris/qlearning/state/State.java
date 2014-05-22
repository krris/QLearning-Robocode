package io.github.krris.qlearning.state;

import com.google.common.base.Objects;
import io.github.krris.qlearning.GameStatus;
import io.github.krris.qlearning.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by krris on 15.03.14.
 */
public final class State {
    private static final Logger LOG = LoggerFactory.getLogger(State.class);

    private final GameStatus gameStatus;
    private final Range distanceToEnemy;
    private final Range distanceToWall;
    private final Range angleToEnemy;

    public static class Builder {

        private GameStatus gameStatus;

        private Range distanceToEnemy;
        private Range distanceToWall;
        private Range angleToEnemy;
        public Builder distanceToEnemy(double distance) {
            this.distanceToEnemy = Range.getRange(distance, Constants.DISTANCES_TO_ENEMY);
            return this;
        }

        public Builder distanceToWall(double distance) {
            this.distanceToWall = Range.getRange(distance, Constants.DISTANCES_TO_WALL);
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
                case DISTANCES_TO_WALL:
                    this.distanceToWall = range;
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
        this.distanceToWall = builder.distanceToWall;
        this.angleToEnemy = builder.angleToEnemy;
    }

    public static State updateState(final GameStatus gameStatus) {
        State state = new Builder()
                .gameStatus(gameStatus)
                .distanceToEnemy(gameStatus.getDistanceToEnemy())
                .distanceToWall(gameStatus.getDistanceToNearestWall())
                .angleToEnemy(gameStatus.getAngleToEnemy())
                .build();
        return state;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (other instanceof State) {
            return (((State)other).distanceToEnemy.equals(this.distanceToEnemy) &&
                    ((State)other).distanceToWall.equals(this.distanceToWall) &&
                    ((State)other).angleToEnemy.equals(this.angleToEnemy));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                this.distanceToEnemy,
                this.distanceToWall,
                this.angleToEnemy);
    }

    @Override
    public String toString() {
        return "ToEnemy: " + distanceToEnemy +
                " ToWall: " + distanceToWall +
                " AngleToEnemy: " + angleToEnemy;

    }
}
