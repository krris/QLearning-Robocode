package io.github.krris.qlearning;

import com.google.common.base.Objects;

/**
 * Created by krris on 15.03.14.
 */
public final class State {
    private final Range distanceToEnemy;
    private final Range distanceToWall;

    public static class Builder {

        private Range distanceToEnemy;
        private Range distanceToWall;

        public Builder distanceToEnemy(int distance) {
            this.distanceToEnemy = Range.getRange(distance, Constants.DISTANCES_TO_ENEMY);
            return this;
        }

        public Builder distanceToWall(int distance) {
            this.distanceToWall = Range.getRange(distance, Constants.DISTANCES_TO_WALL);
            return this;
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
        String message = "ToEnemy: " + distanceToEnemy + "\n" +
                "ToWall: " + distanceToWall + "\n";
        return message;
    }

    public Range getDistanceToEnemy() {
        return distanceToEnemy;
    }

    public Range getDistanceToWall() {
        return distanceToWall;
    }
}
