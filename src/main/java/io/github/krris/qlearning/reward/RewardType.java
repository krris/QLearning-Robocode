package io.github.krris.qlearning.reward;

import io.github.krris.qlearning.util.Constants;

/**
 * Created by krris on 23.03.14.
 */
public enum RewardType {
    HIT_A_WALL(Constants.HIT_A_WALL),
    COLLISION_WITH_ENEMY(Constants.COLLISION_WITH_ENEMY),
    LIVING_REWARD(Constants.LIVING_REWARD),
    HIT_BY_BULLET(Constants.HIT_BY_BULLET),
    MY_BULLET_HITS_ROBOT(Constants.MY_BULLET_HITS_ROBOT);

    private int reward;

    RewardType(int reward) {
        this.reward = reward;
    }

    public int getReward() {
        return this.reward;
    }
}
