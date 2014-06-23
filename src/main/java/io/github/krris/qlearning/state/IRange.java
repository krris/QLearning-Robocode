package io.github.krris.qlearning.state;

/**
 * Created by krris on 20.06.14.
 */
public interface IRange<T> {
    public boolean fits(T value);
    public RangeType getRangeType();
}
