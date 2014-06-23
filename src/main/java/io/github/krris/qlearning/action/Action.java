package io.github.krris.qlearning.action;

import io.github.krris.qlearning.util.Constants;

/**
 * Created by krris on 15.03.14.
 */
public enum Action {
    AHEAD(Constants.MOVE_DISTANCE),
    AHEAD_LEFT(Constants.MOVE_DISTANCE),
    AHEAD_RIGHT(Constants.MOVE_DISTANCE),
    BACK(Constants.MOVE_DISTANCE),
    TURN_LEFT(Constants.TURN_ANGLE),
    TURN_RIGHT(Constants.TURN_ANGLE);

    private transient Executable executableAction;
    private int value;

    Action(int value) {
        this.value = value;
    }

    public void execute() {
        this.executableAction.execute();
    }
    
    @Override
    public String toString() {
        return this.name();
    }

    public void setExecutableAction(Executable executableAction) {
        this.executableAction = executableAction;
    }

    public int value() {
        return this.value;
    }
}
