package io.github.krris.qlearning;

import com.google.common.base.Objects;

public class Range {
	public final int min;
	public final int max;
	
	private double value;
	
	public Range(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	public Range(int min, int max, double value) {
		this.min = min;
		this.max = max;
		this.value = value;
	}
	
	public Range(Range other) {
		this.min = other.min;
		this.max = other.max;
	}
	
	public Range(Range other, double value) {
		this.min = other.min;
		this.max = other.max;
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
        // FIXME
        throw new IllegalStateException("");
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
		return "Range: [" + min + ", " + max + ")";
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
