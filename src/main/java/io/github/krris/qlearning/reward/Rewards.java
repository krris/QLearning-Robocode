package io.github.krris.qlearning.reward;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krris on 23.03.14.
 */
public class Rewards {
    private final Logger LOG = LoggerFactory.getLogger(Rewards.class);

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
        LOG.info(String.valueOf(roundReward));
        this.rewardsPerRound.add(this.roundReward);
        // Every round start calculating a reward from 0
        this.roundReward = 0;
        this.cycleReward = 0;
    }

    public List<Integer> getRewardsPerRound() {
        return rewardsPerRound;
    }
}
