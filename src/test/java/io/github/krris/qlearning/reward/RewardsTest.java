package io.github.krris.qlearning.reward;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by krris on 23.03.14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/test-rewards-beans.xml"})
public class RewardsTest {
    private ApplicationContext context;

    @Autowired
    private Rewards rewards;

    @Test
    public void testReward() {
        double expectedSum = RewardType.COLLISION_WITH_ENEMY.getReward() + RewardType.HIT_A_WALL.getReward();

        assertEquals(rewards.getRoundReward(), 0, 0);
        assertTrue(rewards.getRewardsPerRound().isEmpty());

        rewards.addReward(RewardType.COLLISION_WITH_ENEMY);
        rewards.addReward(RewardType.HIT_A_WALL);

        assertEquals(rewards.getRoundReward(), expectedSum, 0);

        rewards.endOfRound();

        assertEquals(rewards.getRoundReward(), 0, 0);
        assertTrue(!rewards.getRewardsPerRound().isEmpty());
    }
}
