package io.github.krris.qlearning.reward;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krris on 23.03.14.
 */
public enum Rewards {
    INSTANCE;

    private List<Integer> rewardsPerRound;
    // A collected reward during one round
    private int roundReward;

    Rewards() {
        this.rewardsPerRound = new ArrayList<>();
        this.roundReward = 0;
    }

    public void addReward(RewardType rewardType) {
        this.roundReward += rewardType.getReward();
    }

    public int getRoundReward() {
        return roundReward;
    }

    public void endOfRound() {
        this.rewardsPerRound.add(this.roundReward);
        // Every round start calculating a reward from 0
        this.roundReward = 0;
    }

    public List<Integer> getRewardsPerRound() {
        return rewardsPerRound;
    }
}
