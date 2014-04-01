package io.github.krris.qlearning.action;

import io.github.krris.qlearning.action.Action;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

/**
 * Created by krris on 15.03.14.
 */
@RunWith(JUnit4.class)
public class ActionTest {

    @Test
    public void equal(){
        Action aheadActionVer1 = Action.AHEAD;
        Action aheadActionVer2 = Action.AHEAD;
        assertEquals(aheadActionVer1, aheadActionVer2);
    }
}
