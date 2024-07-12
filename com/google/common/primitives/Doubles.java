package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import java.util.regex.Pattern;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

@GwtCompatible(
	emulated = true
)
public final class Doubles {
	public static final int BYTES = 8;
	@GwtIncompatible
	static final Pattern FLOATING_POINT_PATTERN = fpPattern();

	private Doubles() {
	}

	public static int hashCode(double value) {
		return Double.valueOf(value).hashCode();
	}

	public static int compare(double a, double b) {
		return Double.compare(a, b);
	}

	public static boolean isFinite(double value) {
		return Double.NEGATIVE_INFINITY < value & value < Double.POSITIVE_INFINITY;
	}

	public static boolean contains(double[] array, double target) {
		for (double value : array) {
			if (value == target) {
				return true;
			}
		}

		return false;
	}

	public static int indexOf(double[] array, double target) {
		return indexOf(array, target, 0, array.length);
	}

	private static int indexOf(double[] array, double target, int start, int end) {
		for (int i = start; i < end; i++) {
			if (array[i] == target) {
				return i;
			}
		}

		return -1;
	}

	public static int indexOf(double[] array, double[] target) {
		Preconditions.checkNotNull(array, "array");
		Preconditions.checkNotNull(target, "target");
		if (target.length == 0) {
			return 0;
		} else {
			label28:
			for (int i = 0; i < array.length - target.length + 1; i++) {
				for (int j = 0; j < target.length; j++) {
					if (array[i + j] != target[j]) {
						continue label28;
					}
				}

				return i;
			}

			return -1;
		}
	}

	public static int lastIndexOf(double[] array, double target) {
		return lastIndexOf(array, target, 0, array.length);
	}

	private static int lastIndexOf(double[] array, double target, int start, int end) {
		for (int i = end - 1; i >= start; i--) {
			if (array[i] == target) {
				return i;
			}
		}

		return -1;
	}

	public static double min(double... array) {
		Preconditions.checkArgument(array.length > 0);
		double min = array[0];

		for (int i = 1; i < array.length; i++) {
			min = Math.min(min, array[i]);
		}

		return min;
	}

	public static double max(double... array) {
		Preconditions.checkArgument(array.length > 0);
		double max = array[0];

		for (int i = 1; i < array.length; i++) {
			max = Math.max(max, array[i]);
		}

		return max;
	}

	@Beta
	public static double constrainToRange(double value, double min, double max) {
		Preconditions.checkArgument(min <= max, "min (%s) must be less than or equal to max (%s)", min, max);
		return Math.min(Math.max(value, min), max);
	}

	public static double[] concat(double[]... arrays) {
		int length = 0;

		for (double[] array : arrays) {
			length += array.length;
		}

		double[] result = new double[length];
		int pos = 0;

		for (double[] array : arrays) {
			System.arraycopy(array, 0, result, pos, array.length);
			pos += array.length;
		}

		return result;
	}

	@Beta
	public static Converter<String, Double> stringConverter() {
		return Doubles.DoubleConverter.INSTANCE;
	}

	public static double[] ensureCapacity(double[] array, int minLength, int padding) {
		Preconditions.checkArgument(minLength >= 0, "Invalid minLength: %s", minLength);
		Preconditions.checkArgument(padding >= 0, "Invalid padding: %s", padding);
		return array.length < minLength ? Arrays.copyOf(array, minLength + padding) : array;
	}

	public static String join(String separator, double... array) {
		Preconditions.checkNotNull(separator);
		if (array.length == 0) {
			return "";
		} else {
			StringBuilder builder = new StringBuilder(array.length * 12);
			builder.append(array[0]);

			for (int i = 1; i < array.length; i++) {
				builder.append(separator).append(array[i]);
			}

			return builder.toString();
		}
	}

	public static Comparator<double[]> lexicographicalComparator() {
		return Doubles.LexicographicalComparator.INSTANCE;
	}

	public static double[] toArray(Collection<? extends Number> collection) {
		if (collection instanceof Doubles.DoubleArrayAsList) {
			return ((Doubles.DoubleArrayAsList)collection).toDoubleArray();
		} else {
			Object[] boxedArray = collection.toArray();
			int len = boxedArray.length;
			double[] array = new double[len];

			for (int i = 0; i < len; i++) {
				array[i] = ((Number)Preconditions.checkNotNull(boxedArray[i])).doubleValue();
			}

			return array;
		}
	}

	public static List<Double> asList(double... backingArray) {
		return (List<Double>)(backingArray.length == 0 ? Collections.emptyList() : new Doubles.DoubleArrayAsList(backingArray));
	}

	@GwtIncompatible
	private static Pattern fpPattern() {
		String decimal = "(?:\\d++(?:\\.\\d*+)?|\\.\\d++)";
		String completeDec = decimal + "(?:[eE][+-]?\\d++)?[fFdD]?";
		String hex = "(?:\\p{XDigit}++(?:\\.\\p{XDigit}*+)?|\\.\\p{XDigit}++)";
		String completeHex = "0[xX]" + hex + "[pP][+-]?\\d++[fFdD]?";
		String fpPattern = "[+-]?(?:NaN|Infinity|" + completeDec + "|" + completeHex + ")";
		return Pattern.compile(fpPattern);
	}

	@Nullable
	@CheckForNull
	@Beta
	@GwtIncompatible
	public static Double tryParse(String string) {
		if (FLOATING_POINT_PATTERN.matcher(string).matches()) {
			try {
				return Double.parseDouble(string);
			} catch (NumberFormatException var2) {
			}
		}

		return null;
	}

	@GwtCompatible
	private static class DoubleArrayAsList extends AbstractList<Double> implements RandomAccess, Serializable {
		final double[] array;
		final int start;
		final int end;
		private static final long serialVersionUID = 0L;

		DoubleArrayAsList(double[] array) {
			this(array, 0, array.length);
		}

		DoubleArrayAsList(double[] array, int start, int end) {
			this.array = array;
			this.start = start;
			this.end = end;
		}

		public int size() {
			return this.end - this.start;
		}

		public boolean isEmpty() {
			return false;
		}

		public Double get(int index) {
			Preconditions.checkElementIndex(index, this.size());
			return this.array[this.start + index];
		}

		public boolean contains(Object target) {
			return target instanceof Double && Doubles.indexOf(this.array, (Double)target, this.start, this.end) != -1;
		}

		public int indexOf(Object target) {
			if (target instanceof Double) {
				int i = Doubles.indexOf(this.array, (Double)target, this.start, this.end);
				if (i >= 0) {
					return i - this.start;
				}
			}

			return -1;
		}

		public int lastIndexOf(Object target) {
			if (target instanceof Double) {
				int i = Doubles.lastIndexOf(this.array, (Double)target, this.start, this.end);
				if (i >= 0) {
					return i - this.start;
				}
			}

			return -1;
		}

		public Double set(int index, Double element) {
			Preconditions.checkElementIndex(index, this.size());
			double oldValue = this.array[this.start + index];
			this.array[this.start + index] = Preconditions.checkNotNull(element);
			return oldValue;
		}

		public List<Double> subList(int fromIndex, int toIndex) {
			int size = this.size();
			Preconditions.checkPositionIndexes(fromIndex, toIndex, size);
			return (List<Double>)(fromIndex == toIndex
				? Collections.emptyList()
				: new Doubles.DoubleArrayAsList(this.array, this.start + fromIndex, this.start + toIndex));
		}

		public boolean equals(@Nullable Object object) {
			if (object == this) {
				return true;
			} else if (object instanceof Doubles.DoubleArrayAsList) {
				Doubles.DoubleArrayAsList that = (Doubles.DoubleArrayAsList)object;
				int size = this.size();
				if (that.size() != size) {
					return false;
				} else {
					for (int i = 0; i < size; i++) {
						if (this.array[this.start + i] != that.array[that.start + i]) {
							return false;
						}
					}

					return true;
				}
			} else {
				return super.equals(object);
			}
		}

		public int hashCode() {
			int result = 1;

			for (int i = this.start; i < this.end; i++) {
				result = 31 * result + Doubles.hashCode(this.array[i]);
			}

			return result;
		}

		public String toString() {
			StringBuilder builder = new StringBuilder(this.size() * 12);
			builder.append('[').append(this.array[this.start]);

			for (int i = this.start + 1; i < this.end; i++) {
				builder.append(", ").append(this.array[i]);
			}

			return builder.append(']').toString();
		}

		double[] toDoubleArray() {
			return Arrays.copyOfRange(this.array, this.start, this.end);
		}
	}

	private static final class DoubleConverter extends Converter<String, Double> implements Serializable {
		static final Doubles.DoubleConverter INSTANCE = new Doubles.DoubleConverter();
		private static final long serialVersionUID = 1L;

		protected Double doForward(String value) {
			return Double.valueOf(value);
		}

		protected String doBackward(Double value) {
			return value.toString();
		}

		public String toString() {
			return "Doubles.stringConverter()";
		}

		private Object readResolve() {
			return INSTANCE;
		}
	}

	private static enum LexicographicalComparator implements Comparator<double[]> {
		INSTANCE;

		public int compare(double[] left, double[] right) {
			int minLength = Math.min(left.length, right.length);

			for (int i = 0; i < minLength; i++) {
				int result = Double.compare(left[i], right[i]);
				if (result != 0) {
					return result;
				}
			}

			return left.length - right.length;
		}

		public String toString() {
			return "Doubles.lexicographicalComparator()";
		}
	}
}
