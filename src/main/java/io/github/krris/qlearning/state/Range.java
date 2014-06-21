package io.github.krris.qlearning.state;

import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Arrays;

public class Range implements IRange<Double>, Serializable {
    private transient static Logger LOG = LoggerFactory.getLogger(Range.class);

	public final double min;
	public final double max;
	
    private final RangeType rangeType;

	public Range(double min, double max, RangeType rangeType) {
		this.min = min;
		this.max = max;
        this.rangeType = rangeType;
	}

    @Override
    public boolean fits(Double value) {
		if (value >= min && value < max)
			return true;
		return false;
	}

    /**
     *
     * @param value
     * @param possibleRanges
     * @return The range which can contain a given value.
     */
    public static Range getRange(double value, final IRange[] possibleRanges) {
        for (IRange range: possibleRanges) {
            if (range.fits(value)) {
                return (Range)range;
            }
        }

        LOG.error("Value: [{}] does not fit to any range: {}", value, possibleRanges);
        throw new IllegalStateException("Value [" + value + "] does not fit to any range " +
                Arrays.toString(possibleRanges));
    }

    public RangeType getRangeType() {
        return rangeType;
    }

    /**
     * Range is equal when min and max value of another Range object are the same.
     * Value is not considered.
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (other instanceof Range) {
            return (((Range)other).min == min &&
            		((Range)other).max == max);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.min, this.max, this.rangeType);
    }
	
	@Override
	public String toString() {
        if (max == Integer.MAX_VALUE) {
            return "[" + min + ", MAX)";

        } else {
		    return "[" + min + ", " + max + ")";
        }
	}
}
