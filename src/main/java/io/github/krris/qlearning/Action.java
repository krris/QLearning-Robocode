package io.github.krris.qlearning;

/**
 * Created by krris on 15.03.14.
 */
public enum Action {
    AHEAD(Constants.MOVE_DISTANCE),
    BACK(Constants.MOVE_DISTANCE),
    TURN_LEFT(Constants.TURN_ANGLE),
    TURN_RIGHT(Constants.TURN_ANGLE);

    private Executable executableAction;
    private final int value;

    Action(int value) {
        this.value = value;
    }

    public void execute() {
        this.executableAction.execute();
    }

    public int value() {
        return value;
    }
    
    @Override
    public String toString() {
        return this.name() + "(" + this.value() + ")";
    }

    public void setExecutableAction(Executable executableAction) {
        this.executableAction = executableAction;
    }
}
