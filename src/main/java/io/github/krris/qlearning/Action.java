package io.github.krris.qlearning;

/**
 * Created by krris on 15.03.14.
 */
public enum Action {
    AHEAD,
    BACK,
    TURN_LEFT,
    TURN_RIGHT;

    private Executable executableAction;

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
}
