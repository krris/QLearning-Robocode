package io.github.krris.qlearning.state;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by krris on 15.03.14.
 */
@RunWith(JUnit4.class)
public class StateTest {

    @Test
    public void equals() {
        int distanceToEnemy = 213;
        int angleToEnemy = 90;
        int distanceToWall = 5;
        boolean enemyShot = true;
        State state = new State.Builder()
                .distanceToEnemy(distanceToEnemy)
                .distanceToWall(distanceToWall)
                .angleToEnemy(angleToEnemy)
                .enemyShotABullet(enemyShot)
                .build();

        // For any non-null reference value x, x.equals(null) must return false.
        State other = null;
        assertFalse(state.equals(other));

        // Reflexive: For any non-null reference value x, x.equals(x) must return true
        assertTrue(state.equals(state));

        // Symmetric: For any non-null reference values x and y, x.equals(y) must
        // return true if and only if y.equals(x) returns true.
        other = new State.Builder()
                .distanceToEnemy(distanceToEnemy)
                .distanceToWall(distanceToWall)
                .angleToEnemy(angleToEnemy)
                .enemyShotABullet(enemyShot)
                .build();
        assertTrue(state.equals(other) == other.equals(state));

        // Transitive: For any non-null reference values x, y, z, if x.equals(y) returns
        // true and y.equals(z) returns true, then x.equals(z) must return true.
        State other2 = new State.Builder()
                .distanceToEnemy(distanceToEnemy)
                .distanceToWall(distanceToWall)
                .angleToEnemy(angleToEnemy)
                .enemyShotABullet(enemyShot)
                .build();
        assertTrue(state.equals(other));
        assertTrue(other.equals(other2));
        assertTrue(state.equals(other2));

        // Consistent: For any non-null reference values x and y, multiple invocations
        // of x.equals(y) consistently return true or consistently return false, provided
        // no information used in equals comparisons on the objects is modified.
        int limit = 1000;
        State different = new State.Builder()
                .distanceToEnemy(distanceToEnemy + 123)
                .distanceToWall(distanceToWall + 123)
                .angleToEnemy(angleToEnemy - 123)
                .enemyShotABullet(!enemyShot)
                .build();

        for (int i = 0; i < limit ; i++) {
            assertTrue(state.equals(other));
            assertFalse(state.equals(different));
        }
    }
}
