import io.github.krris.qlearning.Action;
import io.github.krris.qlearning.Constants;
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

    @Test
    public void turnValue() {
        Action turnLeft = Action.TURN_LEFT;
        assertEquals(turnLeft.value(), Constants.TURN_ANGLE);
    }

    @Test
    public void movementValue() {
        Action ahead = Action.AHEAD;
        assertEquals(ahead.value(), Constants.MOVE_DISTANCE);
    }
}
