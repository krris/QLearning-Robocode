package io.github.krris.qlearning.state;

import io.github.krris.qlearning.util.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by krris on 15.03.14.
 */
@RunWith(JUnit4.class)
public class RangeTest {

    @Test
    public void equals() {
        int min = 100;
        int max = 200;
        Range range = new Range(min, max, RangeType.DISTANCES_TO_ENEMY);

        // For any non-null reference value x, x.equals(null) must return false.
        Range other = null;
        assertFalse(range.equals(other));

        // Reflexive: For any non-null reference value x, x.equals(x) must return true
        assertTrue(range.equals(range));

        // Symmetric: For any non-null reference values x and y, x.equals(y) must
        // return true if and only if y.equals(x) returns true.
        other = new Range(min, max, RangeType.DISTANCES_TO_ENEMY);
        assertTrue(range.equals(other) == other.equals(range));

        // Transitive: For any non-null reference values x, y, z, if x.equals(y) returns
        // true and y.equals(z) returns true, then x.equals(z) must return true.
        Range other2 = new Range(min, max, RangeType.DISTANCES_TO_ENEMY);
        assertTrue(range.equals(other));
        assertTrue(other.equals(other2));
        assertTrue(range.equals(other2));

        // Consistent: For any non-null reference values x and y, multiple invocations
        // of x.equals(y) consistently return true or consistently return false, provided
        // no information used in equals comparisons on the objects is modified.
        int limit = 1000;
        Range different = new Range(max, min, RangeType.DISTANCES_TO_WALL);
        for (int i = 0; i < limit ; i++) {
            assertTrue(range.equals(other));
            assertFalse(range.equals(different));
        }
    }

    @Test(expected = IllegalStateException.class)
    public void getRangeTest() {
        double wrongValue = -9999;
        Range.getRange(wrongValue, Constants.DISTANCES_TO_ENEMY);
    }
}
