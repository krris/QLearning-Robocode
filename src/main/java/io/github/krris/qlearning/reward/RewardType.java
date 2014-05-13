package io.github.krris.qlearning.reward;

import io.github.krris.qlearning.util.Constants;

/**
 * Created by krris on 23.03.14.
 */
public enum RewardType {
    HIT_A_WALL(Constants.HIT_A_WALL);
//    COLLISION_WITH_ENEMY(Constants.COLLISION_WITH_ENEMY);

    private int reward;

    RewardType(int reward) {
        this.reward = reward;
    }

    public int getReward() {
        return this.reward;
    }
}
