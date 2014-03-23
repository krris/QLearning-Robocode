package io.github.krris.qlearning.state;

import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class Range {
    private static Logger LOG = LoggerFactory.getLogger(Range.class);

	public final int min;
	public final int max;
	
	private double value;
    private RangeType rangeType;

	public Range(int min, int max, RangeType rangeType) {
		this.min = min;
		this.max = max;
        this.rangeType = rangeType;
	}
	
	public Range(Range other, double value) {
		this.min = other.min;
		this.max = other.max;
        this.rangeType = other.getRangeType();
		this.value = value;
	}
	
	public boolean fits(double value) {
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
    static public Range getRange(double value, final Range[] possibleRanges) {
        for (Range range: possibleRanges) {
            if (range.fits(value)) {
                return range;
            }
        }

        LOG.error("Value: [{}] does not fit to any range: {}", value, possibleRanges);
        throw new IllegalStateException("Value [" + value + "] does not fit to any range " +
                Arrays.toString(possibleRanges));
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
        return Objects.hashCode(this.min, this.max);
    }
	
	@Override
	public String toString() {
        if (max == Integer.MAX_VALUE) {
            return "[" + min + ", MAX)";

        } else {
		    return "[" + min + ", " + max + ")";
        }
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

    public RangeType getRangeType() {
        return rangeType;
    }
}
