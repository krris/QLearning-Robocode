package io.github.krris.qlearning.state;

import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by krris on 21.06.14.
 */
public class BoolRange implements IRange<Boolean> {
    private transient static Logger LOG = LoggerFactory.getLogger(BoolRange.class);

    private final RangeType rangeType;
    private final boolean value;

    public BoolRange(boolean value, RangeType rangeType) {
        this.value = value;
        this.rangeType = rangeType;
    }

    /**
     *
     * @param value
     * @param possibleRanges
     * @return The range which can contain a given value.
     */
    public static BoolRange getRange(boolean value, final IRange[] possibleRanges) {
        for (IRange range: possibleRanges) {
            if (range.fits(value)) {
                return (BoolRange)range;
            }
        }

        LOG.error("Value: [{}] does not fit to any range: {}", value, possibleRanges);
        throw new IllegalStateException("Value [" + value + "] does not fit to any range " +
                Arrays.toString(possibleRanges));
    }

    @Override
    public boolean fits(Boolean value) {
        if (value == this.value) {
            return true;
        }
        return false;
    }

    @Override
    public RangeType getRangeType() {
        return this.rangeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoolRange boolRange = (BoolRange) o;

        if (value != boolRange.value) return false;
        if (rangeType != boolRange.rangeType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.value, this.rangeType);
    }
}
