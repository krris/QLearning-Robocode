package krris.learning.robot;

import krris.learning.robot.Constants;

/**
 * Created by krris on 15.03.14.
 */
public enum Action {
    AHEAD(Constants.MOVE_DISTANCE),
    BACK(Constants.MOVE_DISTANCE),
    TURN_LEFT(Constants.TURN_ANGLE),
    TURN_RIGHT(Constants.TURN_ANGLE);

    private final int value;

    Action(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
    
    @Override
    public String toString() {
        return this.name() + "(" + this.value() + ")";
    }
}
