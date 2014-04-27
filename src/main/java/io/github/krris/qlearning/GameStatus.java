package io.github.krris.qlearning;

import io.github.krris.qlearning.action.Action;
import io.github.krris.qlearning.util.Util;
import math.geom2d.Vector2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import robocode.RobotStatus;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by krris on 18.03.14.
 */
public class GameStatus {
    private Logger LOG = LoggerFactory.getLogger(GameStatus.class);

    private RobotStatus robotStatus;

    // The coordinates of the last scanned enemy robot
    private double enemyX;
    private double enemyY;

    private double enemyEnergy;
    private double angleToEnemy;

    private double battleFieldWith;
    private double battleFieldHeight;

    private double distanceToWall;
    private double distanceToEnemy;

    // If my robot is alive
    private boolean amIAlive;

    /* Optional parameters. Used to predict game status after executing an action.
    If they are not set, return a given parameter from RobotStatus. */
    private Optional<Double> myX;
    private Optional<Double> myY;

    private GameStatus(Builder builder) {
        this.robotStatus = builder.robotStatus;
        this.myX = builder.myX;
        this.myY = builder.myY;
        this.enemyX = builder.enemyX;
        this.enemyY = builder.enemyY;
        this.enemyEnergy = builder.enemyEnergy;
        this.amIAlive = builder.amIAlive;
        this.battleFieldHeight = builder.battleFieldHeight;
        this.battleFieldWith = builder.battleFieldWidth;
    }

    public static class Builder {
        private RobotStatus robotStatus;
        private Optional<Double> myX = Optional.empty();
        private Optional<Double> myY = Optional.empty();
        private double enemyX;
        private double enemyY;
        private double enemyEnergy;
        private boolean amIAlive;
        private double battleFieldWidth;
        private double battleFieldHeight;

        public Builder() { }

        public Builder robotStatus(RobotStatus status) {
            this.robotStatus = status;
            return this;
        }

        public Builder myX(Optional<Double> x) {
            this.myX = x;
            return this;
        }

        public Builder myY(Optional<Double> y) {
            this.myY = y;
            return this;
        }

        public Builder enemyX(double x) {
            this.enemyX = x;
            return this;
        }

        public Builder enemyY(double y) {
            this.enemyY = y;
            return this;
        }

        public Builder enemyEnergy(double energy) {
            this.enemyEnergy = energy;
            return this;
        }

        public Builder amIAlive(boolean alive) {
            this.amIAlive = alive;
            return this;
        }

        public Builder battleFieldWidth(double width) {
            this.battleFieldWidth = width;
            return this;
        }

        public Builder battleFieldHeight(double height) {
            this.battleFieldHeight = height;
            return this;
        }

        public GameStatus build() {
            return new GameStatus(this);
        }
    }

    public double getX() {
        if (this.myX.isPresent()) {
            return this.myX.get();
        }
        return this.robotStatus.getX();
    }

    public double getY() {
        if (this.myY.isPresent()) {
            return this.myY.get();
        }
        return this.robotStatus.getY();
    }

    public void setMyX(double myX) {
        this.myX = Optional.of(myX);
    }

    public void setMyY(double myY) {
        this.myY = Optional.of(myY);
    }


    public double getHeading() {
        return this.robotStatus.getHeading();
    }

    /**
     * Return game status after executing action. It is assumed that enemy robot
     * stays in place and only our robot is moving.
     * @param action
     * @return
     */
    public GameStatus getStatusAfterExecutingAction(Action action) {
        GameStatus gameStatus = this.copy();;
        switch (action) {
            case AHEAD:
            case BACK:
                Vector2D myPosition = moveAheadOrBack(action);
                gameStatus.setMyX(myPosition.x());
                gameStatus.setMyY(myPosition.y());
                break;
            case TURN_RIGHT:
            case TURN_LEFT:
                break;
            default:
                String message = "Action [" + action.name() + "] is not supported in getStatusAfterExecutingAction().";
                LOG.error(message);
                throw new IllegalArgumentException(message);
        }
        return gameStatus;
    }

    private Vector2D moveAheadOrBack(Action action) {
        Vector2D currentPosition = new Vector2D(this.getX(), this.getY());
        double myAngle = robotStatus.getHeadingRadians();
        Vector2D moveVector = Vector2D.createPolar(action.value(), myAngle);

        Vector2D sum = currentPosition.plus(moveVector);
        return sum;
    }

    public RobotStatus getRobotStatus() {
        return robotStatus;
    }

    public void setRobotStatus(RobotStatus robotStatus) {
        this.robotStatus = robotStatus;
    }

    public double getAngleToEnemy() {
        return angleToEnemy;
    }

    public void setAngleToEnemy(double angleToEnemy) {
        this.angleToEnemy = angleToEnemy;
    }

    public double getEnemyX() {
        return enemyX;
    }

    public void setEnemyX(double enemyX) {
        this.enemyX = enemyX;
    }

    public double getEnemyY() {
        return enemyY;
    }

    public void setEnemyY(double enemyY) {
        this.enemyY = enemyY;
    }

    public double getEnemyEnergy() {
        return enemyEnergy;
    }

    public void setEnemyEnergy(double enemyEnergy) {
        this.enemyEnergy = enemyEnergy;
    }

    public double getMyEnergy() {
        return this.robotStatus.getEnergy();
    }

    public boolean isAmIAlive() {
        return amIAlive;
    }

    public void setAmIAlive(boolean amIAlive) {
        this.amIAlive = amIAlive;
    }

    public double getDistanceToNearestWall() {
        double xLeftOffset = this.robotStatus.getX();
        double xRightOffset = this.battleFieldWith - this.robotStatus.getX();

        double yTopOffset = this.battleFieldHeight - this.robotStatus.getY();
        double yBottomOffset = this.robotStatus.getY();

        double[] offsets = {xLeftOffset, xRightOffset, yBottomOffset, yTopOffset};
        Arrays.sort(offsets);

        // return smallest number
        return offsets[0];
    }

    public double getDistanceToEnemy() {
        return Util.distanceBetween2Points(this.getX(), this.getY(), this.getEnemyX(), this.getEnemyY());
    }

    public void setBattlefieldHeight(double battleFieldHeight) {
        this.battleFieldHeight = battleFieldHeight;
    }

    public void setBattlefieldWidth(double battleFieldWidth) {
        this.battleFieldWith = battleFieldWidth;
    }

    public GameStatus copy() {
        GameStatus copy = new Builder()
                .amIAlive(this.amIAlive)
                .enemyEnergy(this.enemyEnergy)
                .enemyX(this.enemyX)
                .enemyY(this.enemyY)
                .myX(this.myX)
                .myY(this.myY)
                .robotStatus(this.robotStatus)
                .battleFieldHeight(this.battleFieldHeight)
                .battleFieldWidth(this.battleFieldWith)
                .build();
        return copy;
    }
}
