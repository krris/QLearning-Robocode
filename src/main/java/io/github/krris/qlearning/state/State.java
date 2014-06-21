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
    private final Range distanceToWall;
    private final BoolRange enemyShotABullet;
    private final Range enemyMovementDirection;

    public static class Builder {

        private GameStatus gameStatus;

        private Range distanceToEnemy;
        private Range angleToEnemy;
        private Range distanceToWall;
        private BoolRange enemyShotABullet;
        private Range enemyMovementDirection;

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

        public Builder enemyShotABullet(boolean value) {
            this.enemyShotABullet = BoolRange.getRange(value, Constants.ENEMY_SHOT_A_BULLET);
            return this;
        }

        public Builder enemyMovementDirection(double angle) {
            this.enemyMovementDirection = Range.getRange(angle, Constants.ENEMY_MOVEMENT_DIRECTION);
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
                case ENEMY_SHOT_A_BULLET:
                    this.enemyShotABullet = (BoolRange)range;
                    return this;
                case ENEMY_MOVEMENT_DIRECTION:
                    this.enemyMovementDirection = (Range)range;
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
        this.enemyShotABullet = builder.enemyShotABullet;
        this.enemyMovementDirection = builder.enemyMovementDirection;
    }

    public static State updateState(final GameStatus gameStatus) {
        State state = new Builder()
                .gameStatus(gameStatus)
                .distanceToEnemy(gameStatus.getDistanceToEnemy())
                .distanceToWall(gameStatus.getDistanceToNearestWall())
                .angleToEnemy(gameStatus.getAngleToEnemy())
                .enemyShotABullet(gameStatus.getEnemyShotABullet())
                .enemyMovementDirection(gameStatus.getEnemyMovementDirection())
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
                    ((State)other).enemyShotABullet.equals(this.enemyShotABullet) &&
                    ((State)other).enemyMovementDirection.equals(this.enemyMovementDirection) &&
                    ((State)other).angleToEnemy.equals(this.angleToEnemy));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                this.distanceToEnemy,
                this.distanceToWall,
                this.enemyShotABullet,
                this.enemyMovementDirection,
                this.angleToEnemy);
    }

    @Override
    public String toString() {
        return "ToEnemy: " + distanceToEnemy +
                " ToWall: " + distanceToWall +
                " AngleToEnemy: " + angleToEnemy +
                " EnemyMovementDirection: " + enemyMovementDirection +
                " EnemyShotABullet: " + enemyShotABullet;
    }
}
