package io.github.krris.qlearning.reward;

import io.github.krris.qlearning.reward.RewardType;
import io.github.krris.qlearning.reward.Rewards;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by krris on 23.03.14.
 */
@RunWith(JUnit4.class)
public class RewardsTest {
    @Test
    public void testReward() {
        Rewards rewards = Rewards.INSTANCE;
        int expectedSum = RewardType.COLLISION_WITH_ENEMY.getReward() + RewardType.HIT_A_WALL.getReward();

        assertEquals(rewards.getRoundReward(), 0);
        assertTrue(rewards.getRewardsPerRound().isEmpty());

        rewards.addReward(RewardType.COLLISION_WITH_ENEMY);
        rewards.addReward(RewardType.HIT_A_WALL);

        assertEquals(rewards.getRoundReward(), expectedSum);

        rewards.endOfRound();

        assertEquals(rewards.getRoundReward(), 0);
        assertTrue(!rewards.getRewardsPerRound().isEmpty());
    }
}
