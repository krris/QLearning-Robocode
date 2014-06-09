package io.github.krris.qlearning.feature;

import io.github.krris.qlearning.action.Action;
import io.github.krris.qlearning.state.State;

/**
 * Created by krris on 31.05.14.
 */
public class IdentityFeatureImpl implements Feature {
    private State state;
    private Action action;

    public IdentityFeatureImpl(State state, Action action) {
        this.action = action;
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdentityFeatureImpl that = (IdentityFeatureImpl) o;

        if (action != that.action) return false;
        if (!state.equals(that.state)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = state.hashCode();
        result = 31 * result + action.hashCode();
        return result;
    }
}
