package io.github.krris.qlearning;

import io.github.krris.qlearning.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import robocode.RobotStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private double previousEnemyX;
    private double previousEnemyY;

    private double enemyEnergy;
    private double previousEnemyEnergy;
    private List<Boolean> enemyShoots = new ArrayList<>();

    private double angleToEnemy;

    private double battleFieldWith;
    private double battleFieldHeight;

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

    public void resetDataAtTheEndOfCycle() {
        this.enemyShoots = new ArrayList<>();
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

    public double getHeading() {
        return this.robotStatus.getHeading();
    }

    public int getRoundNum() {
        return robotStatus.getRoundNum();
    }

    public RobotStatus getRobotStatus() {
        return robotStatus;
    }

    public void setRobotStatus(RobotStatus robotStatus) {
        this.robotStatus = robotStatus;
    }

    public double getAngleToEnemy() {
        return this.angleToEnemy;
    }

    public void setAngleToEnemy(double angle) {
        this.angleToEnemy = angle;
    }

    public double getEnemyX() {
        return enemyX;
    }

    public void setEnemyX(double enemyX) {
        this.previousEnemyX = this.enemyX;
        this.enemyX = enemyX;
    }

    public double getEnemyY() {
        return enemyY;
    }

    public void setEnemyY(double enemyY) {
        this.previousEnemyY = this.enemyY;
        this.enemyY = enemyY;
    }

    public double getEnemyEnergy() {
        return enemyEnergy;
    }

    public void setEnemyEnergy(double enemyEnergy) {
        this.previousEnemyEnergy = this.enemyEnergy;
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

    public double getDistanceToNearestWall(double myX, double myY) {
        double xLeftOffset = myX;
        double xRightOffset = this.battleFieldWith - myX;

        double yTopOffset = this.battleFieldHeight - myY;
        double yBottomOffset = myY;

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

    public double getBattleFieldWith() {
        return battleFieldWith;
    }

    public double getBattleFieldHeight() {
        return battleFieldHeight;
    }

    public boolean getEnemyShotABullet() {
        double minDifference = 0.1;
        double maxDifference = 3.0;

        double difference = this.previousEnemyEnergy - this.enemyEnergy;
        if (difference >= minDifference && difference <= maxDifference) {
            this.enemyShoots.add(true);
        }
        this.enemyShoots.add(false);

        if (this.enemyShoots.contains(true)) {
            return true;
        }
        return false;
    }

    public double getEnemyMovementDirection() {
        return Util.angleBetween2Points(this.enemyX, this.enemyY, this.previousEnemyX, this.previousEnemyX);
    }

}
