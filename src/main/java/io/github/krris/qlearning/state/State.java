package io.github.krris.qlearning.state;

import com.google.common.base.Objects;
import io.github.krris.qlearning.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by krris on 15.03.14.
 */
public final class State {
    private static final Logger LOG = LoggerFactory.getLogger(State.class);

    private final Range distanceToEnemy;
    private final Range distanceToWall;

    public static class Builder {

        private Range distanceToEnemy;
        private Range distanceToWall;

        public Builder distanceToEnemy(double distance) {
            this.distanceToEnemy = Range.getRange(distance, Constants.DISTANCES_TO_ENEMY);
            return this;
        }

        public Builder distanceToWall(double distance) {
            this.distanceToWall = Range.getRange(distance, Constants.DISTANCES_TO_WALL);
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
        this.distanceToEnemy = builder.distanceToEnemy;
        this.distanceToWall = builder.distanceToWall;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (other instanceof State) {
            return (((State)other).distanceToEnemy.equals(this.distanceToEnemy) &&
                    ((State)other).distanceToWall.equals(this.distanceToWall));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                this.distanceToEnemy,
                this.distanceToWall);
    }

    @Override
    public String toString() {
        String message = "\nToEnemy: " + distanceToEnemy +
                " ToWall: " + distanceToWall;
        return message;
    }

    public Range getDistanceToEnemy() {
        return distanceToEnemy;
    }

    public Range getDistanceToWall() {
        return distanceToWall;
    }
}
