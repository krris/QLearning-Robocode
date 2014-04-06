package io.github.krris.qlearning.reward;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krris on 23.03.14.
 */
public class Rewards {
    private List<Integer> rewardsPerRound;

    // Collected reward during one round
    private int roundReward;

    // Collected reward during one cycle
    private int cycleReward;

    Rewards() {
        this.rewardsPerRound = new ArrayList<>();
        this.roundReward = 0;
        this.cycleReward = 0;
    }

    public void addReward(RewardType rewardType) {
        this.roundReward += rewardType.getReward();
        this.cycleReward += rewardType.getReward();
    }

    public int getRoundReward() {
        return roundReward;
    }

    public int getCycleReward() {
        return this.cycleReward;
    }

    public void endOfCycle() {
        this.cycleReward = 0;
    }

    public void endOfRound() {
        this.rewardsPerRound.add(this.roundReward);
        // Every round start calculating a reward from 0
        this.roundReward = 0;
        this.cycleReward = 0;
    }

    public List<Integer> getRewardsPerRound() {
        return rewardsPerRound;
    }
}
