package io.github.krris.qlearning.state;

/**
 * Created by krris on 21.06.14.
 */
public class BoolRange implements IRange<Boolean> {
    @Override
    public boolean fits(Boolean value) {
        return false;
    }

    @Override
    public RangeType getRangeType() {
        return null;
    }
}
