package org.apache.commons.lang3;

import java.io.Serializable;
import java.util.Comparator;

public final class Range<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private final Comparator<T> comparator;
	private final T minimum;
	private final T maximum;
	private transient int hashCode;
	private transient String toString;

	public static <T extends Comparable<T>> Range<T> is(T element) {
		return between(element, element, null);
	}

	public static <T> Range<T> is(T element, Comparator<T> comparator) {
		return between(element, element, comparator);
	}

	public static <T extends Comparable<T>> Range<T> between(T fromInclusive, T toInclusive) {
		return between(fromInclusive, toInclusive, null);
	}

	public static <T> Range<T> between(T fromInclusive, T toInclusive, Comparator<T> comparator) {
		return new Range<>(fromInclusive, toInclusive, comparator);
	}

	private Range(T element1, T element2, Comparator<T> comp) {
		if (element1 != null && element2 != null) {
			if (comp == null) {
				this.comparator = Range.ComparableComparator.INSTANCE;
			} else {
				this.comparator = comp;
			}

			if (this.comparator.compare(element1, element2) < 1) {
				this.minimum = element1;
				this.maximum = element2;
			} else {
				this.minimum = element2;
				this.maximum = element1;
			}
		} else {
			throw new IllegalArgumentException("Elements in a range must not be null: element1=" + element1 + ", element2=" + element2);
		}
	}

	public T getMinimum() {
		return this.minimum;
	}

	public T getMaximum() {
		return this.maximum;
	}

	public Comparator<T> getComparator() {
		return this.comparator;
	}

	public boolean isNaturalOrdering() {
		return this.comparator == Range.ComparableComparator.INSTANCE;
	}

	public boolean contains(T element) {
		return element == null ? false : this.comparator.compare(element, this.minimum) > -1 && this.comparator.compare(element, this.maximum) < 1;
	}

	public boolean isAfter(T element) {
		return element == null ? false : this.comparator.compare(element, this.minimum) < 0;
	}

	public boolean isStartedBy(T element) {
		return element == null ? false : this.comparator.compare(element, this.minimum) == 0;
	}

	public boolean isEndedBy(T element) {
		return element == null ? false : this.comparator.compare(element, this.maximum) == 0;
	}

	public boolean isBefore(T element) {
		return element == null ? false : this.comparator.compare(element, this.maximum) > 0;
	}

	public int elementCompareTo(T element) {
		if (element == null) {
			throw new NullPointerException("Element is null");
		} else if (this.isAfter(element)) {
			return -1;
		} else {
			return this.isBefore(element) ? 1 : 0;
		}
	}

	public boolean containsRange(Range<T> otherRange) {
		return otherRange == null ? false : this.contains(otherRange.minimum) && this.contains(otherRange.maximum);
	}

	public boolean isAfterRange(Range<T> otherRange) {
		return otherRange == null ? false : this.isAfter(otherRange.maximum);
	}

	public boolean isOverlappedBy(Range<T> otherRange) {
		return otherRange == null ? false : otherRange.contains(this.minimum) || otherRange.contains(this.maximum) || this.contains(otherRange.minimum);
	}

	public boolean isBeforeRange(Range<T> otherRange) {
		return otherRange == null ? false : this.isBefore(otherRange.minimum);
	}

	public Range<T> intersectionWith(Range<T> other) {
		if (!this.isOverlappedBy(other)) {
			throw new IllegalArgumentException(String.format("Cannot calculate intersection with non-overlapping range %s", other));
		} else if (this.equals(other)) {
			return this;
		} else {
			T min = this.getComparator().compare(this.minimum, other.minimum) < 0 ? other.minimum : this.minimum;
			T max = this.getComparator().compare(this.maximum, other.maximum) < 0 ? this.maximum : other.maximum;
			return between(min, max, this.getComparator());
		}
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (obj != null && obj.getClass() == this.getClass()) {
			Range<T> range = (Range<T>)obj;
			return this.minimum.equals(range.minimum) && this.maximum.equals(range.maximum);
		} else {
			return false;
		}
	}

	public int hashCode() {
		int result = this.hashCode;
		if (this.hashCode == 0) {
			int var2 = 17;
			int var3 = 37 * var2 + this.getClass().hashCode();
			int var4 = 37 * var3 + this.minimum.hashCode();
			result = 37 * var4 + this.maximum.hashCode();
			this.hashCode = result;
		}

		return result;
	}

	public String toString() {
		if (this.toString == null) {
			this.toString = "[" + this.minimum + ".." + this.maximum + "]";
		}

		return this.toString;
	}

	public String toString(String format) {
		return String.format(format, this.minimum, this.maximum, this.comparator);
	}

	private static enum ComparableComparator implements Comparator {
		INSTANCE;

		public int compare(Object obj1, Object obj2) {
			return ((Comparable)obj1).compareTo(obj2);
		}
	}
}
