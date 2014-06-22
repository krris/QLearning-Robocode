package io.github.krris.qlearning.reward;

import io.github.krris.qlearning.util.Constants;

/**
 * Created by krris on 23.03.14.
 */
public enum RewardType {
    HIT_A_WALL(Constants.HIT_A_WALL),
    COLLISION_WITH_ENEMY(Constants.COLLISION_WITH_ENEMY),
    COLLISION_AND_KILL_ENEMY(Constants.COLLISION_AND_KILL_ENEMY),
    LIVING_REWARD(Constants.LIVING_REWARD),
    HIT_BY_BULLET(Constants.HIT_BY_BULLET);

    private int reward;

    RewardType(int reward) {
        this.reward = reward;
    }

    public double getReward(double value) {
        switch (this) {
            case HIT_A_WALL:
            case LIVING_REWARD:
            case HIT_BY_BULLET:
                return this.reward;
            case COLLISION_WITH_ENEMY:
            case COLLISION_AND_KILL_ENEMY:
                return value * this.reward;

        }
        return this.reward;
    }
}
