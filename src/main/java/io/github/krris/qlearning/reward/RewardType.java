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
    HIT_BY_BULLET(Constants.HIT_BY_BULLET),
    DISTANCE_TO_ENEMY_LESS_THAN_50(Constants.DISTANCE_TO_ENEMY_LESS_THAN_50),
    DISTANCE_TO_ENEMY_LESS_THAN_200(Constants.DISTANCE_TO_ENEMY_LESS_THAN_200);

    private double reward;

    RewardType(double reward) {
        this.reward = reward;
    }

    public double getReward() {
        return this.reward;
    }
}
