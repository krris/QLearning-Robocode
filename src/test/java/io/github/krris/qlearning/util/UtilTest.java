package io.github.krris.qlearning.util;

import io.github.krris.qlearning.GameStatus;
import io.github.krris.qlearning.action.Action;
import io.github.krris.qlearning.state.Range;
import io.github.krris.qlearning.state.State;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import robocode.RobotStatus;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by krris on 22.03.14.
 */
//@Ignore
@RunWith(PowerMockRunner.class)
@PrepareForTest(RobotStatus.class)
public class UtilTest {
    private final Logger LOG = LoggerFactory.getLogger(UtilTest.class);

//    @Test
//    public void generateStates() {
//        Range[] distancesToEnemy = {
//                new Range(0, 50, RangeType.DISTANCES_TO_ENEMY),
//                new Range(50,100, RangeType.DISTANCES_TO_ENEMY),
//        };
//
//        Range[] distancesToWall = {
//                new Range(0, 10, RangeType.DISTANCES_TO_WALL),
//                new Range(10, 30, RangeType.DISTANCES_TO_WALL),
//        };
//
//        Set<State> expectedStates = initExpectedStates(distancesToEnemy, distancesToWall);
//
//        Range[][] ranges = {distancesToEnemy, distancesToWall};
//        assertEquals(Util.generateStates(ranges), expectedStates);
//        LOG.info(Util.generateStates(ranges).toString());
//    }

    private Set<State> initExpectedStates(Range[] distancesToEnemy, Range[] distancesToWall) {
        Set<State> expectedStates = new HashSet<>();
        State state1 = new State.Builder()
                .setProperty(distancesToEnemy[0])
                .setProperty(distancesToWall[0])
                .build();
        State state2 = new State.Builder()
                .setProperty(distancesToEnemy[0])
                .setProperty(distancesToWall[1])
                .build();
        State state3 = new State.Builder()
                .setProperty(distancesToEnemy[1])
                .setProperty(distancesToWall[0])
                .build();
        State state4 = new State.Builder()
                .setProperty(distancesToEnemy[1])
                .setProperty(distancesToWall[1])
                .build();

        expectedStates.add(state1);
        expectedStates.add(state2);
        expectedStates.add(state3);
        expectedStates.add(state4);

        return expectedStates;
    }

    @Test
    public void dostanceBetween2PointsTest() {
        int x1 = 1;
        int y1 = 5;
        int x2 = 5;
        int y2 = 7;

        double distance = Util.distanceBetween2Points(x1, y1, x2, y2);
        double expectedDistance = 2 * Math.sqrt(5);
        double error = 0;
        assertEquals(expectedDistance, distance, error);
    }

    @Test
    public void distanceBetween2PointsTest2() {
        int x1 = 25;
        int y1 = 25;
        int x2 = 125;
        int y2 = 125;

        double distance = Util.distanceBetween2Points(x1, y1, x2, y2);
        double expectedDistance = 100 * Math.sqrt(2);
        double error = 0;
        assertEquals(expectedDistance, distance, error);
    }

    @Test
    public void angleToEnemyTest() {
        double x1 = -5;
        double y1 = -5;
        double x2 = 5;
        double y2 = 5;
        double expectedAngle = 45;
        double error = 0;

        double angle = Util.angleToEnemy(x1, y1, x2, y2);
        assertEquals(expectedAngle, angle, error);
    }

    @Test
    public void getCoordinatesAfterActionTest() {
        double x1 = 15;
        double y1 = 15;
        double expectedX = 15;
        double expectedY = x1 - Constants.MOVE_DISTANCE;
        double heading = 180;
        Action action = Action.AHEAD;

        RobotStatus robotStatus = mock(RobotStatus.class);
        when(robotStatus.getX()).thenReturn(x1);
        when(robotStatus.getY()).thenReturn(y1);
        when(robotStatus.getHeading()).thenReturn(heading);

        GameStatus gameStatus = mock(GameStatus.class);
        State state = new State.Builder().gameStatus(gameStatus).build();
        when(gameStatus.getRobotStatus()).thenReturn(robotStatus);

        double[] coords = Util.getCoordinatesAfterAction(state, action);
        assertTrue(coords[0] == expectedX);
        assertTrue(coords[1] == expectedY);
    }
}
