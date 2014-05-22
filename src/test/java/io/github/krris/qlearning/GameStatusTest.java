package io.github.krris.qlearning;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import robocode.RobotStatus;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by krris on 30.03.14.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RobotStatus.class)
public class GameStatusTest {
    private GameStatus gameStatus;
    private RobotStatus robotStatus;

    private static double MY_X = 0;
    private static double MY_Y = 5;
    private static double MY_ANGLE_RADIANS = Math.toRadians(90);


    @Before
    public void setUp() {
        robotStatus = mock(RobotStatus.class);
        when(robotStatus.getHeadingRadians()).thenReturn(MY_ANGLE_RADIANS);

        gameStatus = new GameStatus.Builder()
                .myX(Optional.of(MY_X))
                .myY(Optional.of(MY_Y))
                .robotStatus(robotStatus)
                .build();
    }

    @Test
    public void myCoordinatesTest() {
        double delta = 0;
        assertEquals(MY_X, gameStatus.getX(), delta);
        assertEquals(MY_Y, gameStatus.getY(), delta);
    }

//    @Test
//    public void getStatusAfterExecutingActionTest() {
//        GameStatus testStatus = gameStatus.getStatusAfterExecutingAction(Action.AHEAD);
//        double expectedX = MY_X;
//        double expectedY = MY_Y + Constants.MOVE_DISTANCE;
//        double error = 0.01;
//        assertEquals(expectedX, testStatus.getX(), error);
//        assertEquals(expectedY, testStatus.getY(), error);
//    }

    @Test
    public void builderTest() {
        final boolean amIalive = false;
        final double enemyEnergy = 99;
        final double enemyX = 1.2;
        final double enemyY = 3.4;
        final double myX = 4.4;
        final double myY = 5.5;
        GameStatus gameStatus = new GameStatus.Builder()
                .robotStatus(this.robotStatus)
                .amIAlive(amIalive)
                .enemyEnergy(enemyEnergy)
                .enemyX(enemyX)
                .enemyY(enemyY)
                .myX(Optional.of(myX))
                .myY(Optional.of(myY))
                .build();

        double error = 0;
        assertEquals(robotStatus, gameStatus.getRobotStatus());
        assertEquals(amIalive, gameStatus.isAmIAlive());
        assertEquals(enemyEnergy, gameStatus.getEnemyEnergy(), error);
        assertEquals(enemyX, gameStatus.getEnemyX(), error);
        assertEquals(enemyY, gameStatus.getEnemyY(), error);
        assertEquals(myX, gameStatus.getX(), error);
        assertEquals(myY, gameStatus.getY(), error);
    }

    @Test
    public void copyTest() {
        final boolean amIalive = false;
        final double enemyEnergy = 99;
        final double enemyX = 1.2;
        final double enemyY = 3.4;
        final double myX = 4.4;
        final double myY = 5.5;
        GameStatus gameStatus = new GameStatus.Builder()
                .robotStatus(this.robotStatus)
                .amIAlive(amIalive)
                .enemyEnergy(enemyEnergy)
                .enemyX(enemyX)
                .enemyY(enemyY)
                .myX(Optional.of(myX))
                .myY(Optional.of(myY))
                .build();

        double error = 0;
        GameStatus copy = gameStatus.copy();
        assertEquals(gameStatus.getRobotStatus(), copy.getRobotStatus());
        assertEquals(gameStatus.isAmIAlive(), copy.isAmIAlive());
        assertEquals(gameStatus.getEnemyEnergy(), copy.getEnemyEnergy(), error);
        assertEquals(gameStatus.getEnemyX(), copy.getEnemyX(), error);
        assertEquals(gameStatus.getEnemyY(), copy.getEnemyY(), error);
        assertEquals(gameStatus.getX(), copy.getX(), error);
        assertEquals(gameStatus.getY(), copy.getY(), error);
    }

}
