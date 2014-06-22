package io.github.krris.qlearning.reward;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
//        double expectedSum = RewardType.HIT_A_WALL.getReward(0) + RewardType.HIT_A_WALL.getReward(0);
//
//        assertEquals(rewards.getRoundReward(), 0, 0);
//        assertTrue(rewards.getRewardsPerRound().isEmpty());
//
//        rewards.addReward(0,RewardType.HIT_A_WALL);
//        rewards.addReward(0,RewardType.HIT_A_WALL);
//
//        assertEquals(rewards.getRoundReward(), expectedSum, 0);
//
//        rewards.endOfRound();
//
//        assertEquals(rewards.getRoundReward(), 0);
//        assertTrue(!rewards.getRewardsPerRound().isEmpty());
    }
}
