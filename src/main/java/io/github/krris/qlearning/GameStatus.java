package io.github.krris.qlearning;

import robocode.RobotStatus;

/**
 * Created by krris on 18.03.14.
 */
public class GameStatus {
    private RobotStatus robotStatus;

    // The coordinates of the last scanned enemy robot
    private double enemyX;
    private double enemyY;

    private double enemyEnergy;
    private double angleToEnemy;

    private double distanceToWall;
    private double distanceToEnemy;

    // If my robot is alive
    private boolean amIAlive;

    private GameStatus(Builder builder) {
        this.enemyX = builder.enemyX;
        this.enemyY = builder.enemyY;
        this.enemyEnergy = builder.enemyEnergy;
        this.amIAlive = builder.amIAlive;
    }

    public static class Builder {
        private RobotStatus robotStatus;
        private double enemyX;
        private double enemyY;
        private double enemyEnergy;
        private boolean amIAlive;

        public Builder() { }

        public Builder robotStatus(RobotStatus status) {
            this.robotStatus = status;
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

        public GameStatus build() {
            return new GameStatus(this);
        }
    }

    public double getX() {
        return this.robotStatus.getX();
    }

    public double getY() {
        return this.robotStatus.getY();
    }

    public double getHeading() {
        return this.robotStatus.getHeading();
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

    public double getDistanceToWall() {
        return distanceToWall;
    }

    public void setDistanceToWall(double distanceToWall) {
        this.distanceToWall = distanceToWall;
    }

    public double getDistanceToEnemy() {
        return distanceToEnemy;
    }

    public void setDistanceToEnemy(double distanceToEnemy) {
        this.distanceToEnemy = distanceToEnemy;
    }


}
