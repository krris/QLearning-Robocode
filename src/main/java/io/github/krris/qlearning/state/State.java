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
    private final Range myEnergy;
    private final Range opponentEnergy;
    private final Range distanceToEnemy;
    private final Range angleToEnemy;
    private final Range distanceToWall;

    public static class Builder {

        private GameStatus gameStatus;

        private Range myEnergy;
        private Range opponentEnergy;
        private Range distanceToEnemy;
        private Range angleToEnemy;
        private Range distanceToWall;

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

        public Builder myEnergy(double energy) {
            this.myEnergy = Range.getRange(energy, Constants.MY_ENERGIES);
            return this;
        }

        public Builder opponentEnergy(double energy) {
            this.opponentEnergy = Range.getRange(energy, Constants.OPONNENT_ENERGIES);
            return this;
        }

        public Builder gameStatus(GameStatus gameStatus) {
            this.gameStatus = gameStatus;
            return this;
        }

        public Builder setProperty(IRange range) {
            switch (range.getRangeType()) {
                case DISTANCES_TO_ENEMY:
                    this.distanceToEnemy = (Range)range;
                    return this;
                case DISTANCES_TO_WALL:
                    this.distanceToWall = (Range)range;
                    return this;
                case ANGLES_TO_ENEMY:
                    this.angleToEnemy = (Range)range;
                    return this;
                case MY_ENERGY:
                    this.myEnergy = (Range)range;
                    return this;
                case OPPONENT_ENERGY:
                    this.opponentEnergy = (Range)range;
                    return this;
                default:
                    String message = "Range type not found";
                    LOG.error(message);
                    throw new IllegalStateException(message);
            }
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
        this.myEnergy = builder.myEnergy;
        this.opponentEnergy = builder.opponentEnergy;
    }

    public static State updateState(final GameStatus gameStatus) {
        State state = new Builder()
                .gameStatus(gameStatus)
                .distanceToEnemy(gameStatus.getDistanceToEnemy())
                .distanceToWall(gameStatus.getDistanceToNearestWall())
                .angleToEnemy(gameStatus.getAngleToEnemy())
                .opponentEnergy(gameStatus.getEnemyEnergy())
                .myEnergy(gameStatus.getMyEnergy())
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
                    ((State)other).distanceToWall.equals(this.distanceToWall) &&
                    ((State)other).opponentEnergy.equals(this.opponentEnergy) &&
                    ((State)other).myEnergy.equals(this.myEnergy) &&
                    ((State)other).angleToEnemy.equals(this.angleToEnemy));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                this.distanceToEnemy,
                this.distanceToWall,
                this.myEnergy,
                this.opponentEnergy,
                this.angleToEnemy);
    }

    @Override
    public String toString() {
        return "ToEnemy: " + distanceToEnemy +
                " ToWall: " + distanceToWall +
                " AngleToEnemy: " + angleToEnemy +
                " OpponentEnergy: " + opponentEnergy +
                " MyEnergy: " + myEnergy;
    }
}
