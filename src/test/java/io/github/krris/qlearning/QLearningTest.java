package io.github.krris.qlearning;

import io.github.krris.qlearning.action.Action;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by krris on 22.05.14.
 */
@RunWith(JUnit4.class)
public class QLearningTest {
    @Test
    public void testRandomAction() {
        QLearning ql = new QLearning();
        for (Action action : Action.values()) {
            ql.setActionFunction(action, ()->{});
        }
        ql.init();
        for (int i = 0; i < 10; i++)
            System.out.println(ql.randomAction());
    }

}
