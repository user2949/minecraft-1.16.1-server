package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.Hash.Strategy;
import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public final class DoubleBigArrays {
	public static final double[][] EMPTY_BIG_ARRAY = new double[0][];
	public static final double[][] DEFAULT_EMPTY_BIG_ARRAY = new double[0][];
	public static final Strategy HASH_STRATEGY = new DoubleBigArrays.BigArrayHashStrategy();
	private static final int SMALL = 7;
	private static final int MEDIUM = 40;
	private static final int DIGIT_BITS = 8;
	private static final int DIGIT_MASK = 255;
	private static final int DIGITS_PER_ELEMENT = 8;

	private DoubleBigArrays() {
	}

	public static double get(double[][] array, long index) {
		return array[BigArrays.segment(index)][BigArrays.displacement(index)];
	}

	public static void set(double[][] array, long index, double value) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)] = value;
	}

	public static void swap(double[][] array, long first, long second) {
		double t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
		array[BigArrays.segment(first)][BigArrays.displacement(first)] = array[BigArrays.segment(second)][BigArrays.displacement(second)];
		array[BigArrays.segment(second)][BigArrays.displacement(second)] = t;
	}

	public static void add(double[][] array, long index, double incr) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)] += incr;
	}

	public static void mul(double[][] array, long index, double factor) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)] *= factor;
	}

	public static void incr(double[][] array, long index) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)]++;
	}

	public static void decr(double[][] array, long index) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)]--;
	}

	public static long length(double[][] array) {
		int length = array.length;
		return length == 0 ? 0L : BigArrays.start(length - 1) + (long)array[length - 1].length;
	}

	public static void copy(double[][] srcArray, long srcPos, double[][] destArray, long destPos, long length) {
		if (destPos <= srcPos) {
			int srcSegment = BigArrays.segment(srcPos);
			int destSegment = BigArrays.segment(destPos);
			int srcDispl = BigArrays.displacement(srcPos);
			int destDispl = BigArrays.displacement(destPos);

			while (length > 0L) {
				int l = (int)Math.min(length, (long)Math.min(srcArray[srcSegment].length - srcDispl, destArray[destSegment].length - destDispl));
				System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
				if ((srcDispl += l) == 134217728) {
					srcDispl = 0;
					srcSegment++;
				}

				if ((destDispl += l) == 134217728) {
					destDispl = 0;
					destSegment++;
				}

				length -= (long)l;
			}
		} else {
			int srcSegment = BigArrays.segment(srcPos + length);
			int destSegment = BigArrays.segment(destPos + length);
			int srcDispl = BigArrays.displacement(srcPos + length);
			int destDispl = BigArrays.displacement(destPos + length);

			while (length > 0L) {
				if (srcDispl == 0) {
					srcDispl = 134217728;
					srcSegment--;
				}

				if (destDispl == 0) {
					destDispl = 134217728;
					destSegment--;
				}

				int lx = (int)Math.min(length, (long)Math.min(srcDispl, destDispl));
				System.arraycopy(srcArray[srcSegment], srcDispl - lx, destArray[destSegment], destDispl - lx, lx);
				srcDispl -= lx;
				destDispl -= lx;
				length -= (long)lx;
			}
		}
	}

	public static void copyFromBig(double[][] srcArray, long srcPos, double[] destArray, int destPos, int length) {
		int srcSegment = BigArrays.segment(srcPos);
		int srcDispl = BigArrays.displacement(srcPos);

		while (length > 0) {
			int l = Math.min(srcArray[srcSegment].length - srcDispl, length);
			System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
			if ((srcDispl += l) == 134217728) {
				srcDispl = 0;
				srcSegment++;
			}

			destPos += l;
			length -= l;
		}
	}

	public static void copyToBig(double[] srcArray, int srcPos, double[][] destArray, long destPos, long length) {
		int destSegment = BigArrays.segment(destPos);
		int destDispl = BigArrays.displacement(destPos);

		while (length > 0L) {
			int l = (int)Math.min((long)(destArray[destSegment].length - destDispl), length);
			System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
			if ((destDispl += l) == 134217728) {
				destDispl = 0;
				destSegment++;
			}

			srcPos += l;
			length -= (long)l;
		}
	}

	public static double[][] newBigArray(long length) {
		if (length == 0L) {
			return EMPTY_BIG_ARRAY;
		} else {
			BigArrays.ensureLength(length);
			int baseLength = (int)(length + 134217727L >>> 27);
			double[][] base = new double[baseLength][];
			int residual = (int)(length & 134217727L);
			if (residual != 0) {
				for (int i = 0; i < baseLength - 1; i++) {
					base[i] = new double[134217728];
				}

				base[baseLength - 1] = new double[residual];
			} else {
				for (int i = 0; i < baseLength; i++) {
					base[i] = new double[134217728];
				}
			}

			return base;
		}
	}

	public static double[][] wrap(double[] array) {
		if (array.length == 0) {
			return EMPTY_BIG_ARRAY;
		} else if (array.length <= 134217728) {
			return new double[][]{array};
		} else {
			double[][] bigArray = newBigArray((long)array.length);

			for (int i = 0; i < bigArray.length; i++) {
				System.arraycopy(array, (int)BigArrays.start(i), bigArray[i], 0, bigArray[i].length);
			}

			return bigArray;
		}
	}

	public static double[][] ensureCapacity(double[][] array, long length) {
		return ensureCapacity(array, length, length(array));
	}

	public static double[][] forceCapacity(double[][] array, long length, long preserve) {
		BigArrays.ensureLength(length);
		int valid = array.length - (array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728) ? 1 : 0);
		int baseLength = (int)(length + 134217727L >>> 27);
		double[][] base = (double[][])Arrays.copyOf(array, baseLength);
		int residual = (int)(length & 134217727L);
		if (residual != 0) {
			for (int i = valid; i < baseLength - 1; i++) {
				base[i] = new double[134217728];
			}

			base[baseLength - 1] = new double[residual];
		} else {
			for (int i = valid; i < baseLength; i++) {
				base[i] = new double[134217728];
			}
		}

		if (preserve - (long)valid * 134217728L > 0L) {
			copy(array, (long)valid * 134217728L, base, (long)valid * 134217728L, preserve - (long)valid * 134217728L);
		}

		return base;
	}

	public static double[][] ensureCapacity(double[][] array, long length, long preserve) {
		return length > length(array) ? forceCapacity(array, length, preserve) : array;
	}

	public static double[][] grow(double[][] array, long length) {
		long oldLength = length(array);
		return length > oldLength ? grow(array, length, oldLength) : array;
	}

	public static double[][] grow(double[][] array, long length, long preserve) {
		long oldLength = length(array);
		return length > oldLength ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve) : array;
	}

	public static double[][] trim(double[][] array, long length) {
		BigArrays.ensureLength(length);
		long oldLength = length(array);
		if (length >= oldLength) {
			return array;
		} else {
			int baseLength = (int)(length + 134217727L >>> 27);
			double[][] base = (double[][])Arrays.copyOf(array, baseLength);
			int residual = (int)(length & 134217727L);
			if (residual != 0) {
				base[baseLength - 1] = DoubleArrays.trim(base[baseLength - 1], residual);
			}

			return base;
		}
	}

	public static double[][] setLength(double[][] array, long length) {
		long oldLength = length(array);
		if (length == oldLength) {
			return array;
		} else {
			return length < oldLength ? trim(array, length) : ensureCapacity(array, length);
		}
	}

	public static double[][] copy(double[][] array, long offset, long length) {
		ensureOffsetLength(array, offset, length);
		double[][] a = newBigArray(length);
		copy(array, offset, a, 0L, length);
		return a;
	}

	public static double[][] copy(double[][] array) {
		double[][] base = (double[][])array.clone();
		int i = base.length;

		while (i-- != 0) {
			base[i] = (double[])array[i].clone();
		}

		return base;
	}

	public static void fill(double[][] array, double value) {
		int i = array.length;

		while (i-- != 0) {
			Arrays.fill(array[i], value);
		}
	}

	public static void fill(double[][] array, long from, long to, double value) {
		long length = length(array);
		BigArrays.ensureFromTo(length, from, to);
		if (length != 0L) {
			int fromSegment = BigArrays.segment(from);
			int toSegment = BigArrays.segment(to);
			int fromDispl = BigArrays.displacement(from);
			int toDispl = BigArrays.displacement(to);
			if (fromSegment == toSegment) {
				Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
			} else {
				if (toDispl != 0) {
					Arrays.fill(array[toSegment], 0, toDispl, value);
				}

				while (--toSegment > fromSegment) {
					Arrays.fill(array[toSegment], value);
				}

				Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
			}
		}
	}

	public static boolean equals(double[][] a1, double[][] a2) {
		if (length(a1) != length(a2)) {
			return false;
		} else {
			int i = a1.length;

			while (i-- != 0) {
				double[] t = a1[i];
				double[] u = a2[i];
				int j = t.length;

				while (j-- != 0) {
					if (Double.doubleToLongBits(t[j]) != Double.doubleToLongBits(u[j])) {
						return false;
					}
				}
			}

			return true;
		}
	}

	public static String toString(double[][] a) {
		if (a == null) {
			return "null";
		} else {
			long last = length(a) - 1L;
			if (last == -1L) {
				return "[]";
			} else {
				StringBuilder b = new StringBuilder();
				b.append('[');
				long i = 0L;

				while (true) {
					b.append(String.valueOf(get(a, i)));
					if (i == last) {
						return b.append(']').toString();
					}

					b.append(", ");
					i++;
				}
			}
		}
	}

	public static void ensureFromTo(double[][] a, long from, long to) {
		BigArrays.ensureFromTo(length(a), from, to);
	}

	public static void ensureOffsetLength(double[][] a, long offset, long length) {
		BigArrays.ensureOffsetLength(length(a), offset, length);
	}

	private static void vecSwap(double[][] x, long a, long b, long n) {
		int i = 0;

		while ((long)i < n) {
			swap(x, a, b);
			i++;
			a++;
			b++;
		}
	}

	private static long med3(double[][] x, long a, long b, long c, DoubleComparator comp) {
		int ab = comp.compare(get(x, a), get(x, b));
		int ac = comp.compare(get(x, a), get(x, c));
		int bc = comp.compare(get(x, b), get(x, c));
		return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
	}

	private static void selectionSort(double[][] a, long from, long to, DoubleComparator comp) {
		for (long i = from; i < to - 1L; i++) {
			long m = i;

			for (long j = i + 1L; j < to; j++) {
				if (comp.compare(get(a, j), get(a, m)) < 0) {
					m = j;
				}
			}

			if (m != i) {
				swap(a, i, m);
			}
		}
	}

	public static void quickSort(double[][] x, long from, long to, DoubleComparator comp) {
		long len = to - from;
		if (len < 7L) {
			selectionSort(x, from, to, comp);
		} else {
			long m = from + len / 2L;
			if (len > 7L) {
				long l = from;
				long n = to - 1L;
				if (len > 40L) {
					long s = len / 8L;
					l = med3(x, from, from + s, from + 2L * s, comp);
					m = med3(x, m - s, m, m + s, comp);
					n = med3(x, n - 2L * s, n - s, n, comp);
				}

				m = med3(x, l, m, n, comp);
			}

			double v = get(x, m);
			long a = from;
			long b = from;
			long c = to - 1L;
			long d = c;

			while (true) {
				int comparison;
				while (b > c || (comparison = comp.compare(get(x, b), v)) > 0) {
					for (; c >= b && (comparison = comp.compare(get(x, c), v)) >= 0; c--) {
						if (comparison == 0) {
							swap(x, c, d--);
						}
					}

					if (b > c) {
						long s = Math.min(a - from, b - a);
						vecSwap(x, from, b - s, s);
						long var29 = Math.min(d - c, to - d - 1L);
						vecSwap(x, b, to - var29, var29);
						long var30;
						if ((var30 = b - a) > 1L) {
							quickSort(x, from, from + var30, comp);
						}

						long var31;
						if ((var31 = d - c) > 1L) {
							quickSort(x, to - var31, to, comp);
						}

						return;
					}

					swap(x, b++, c--);
				}

				if (comparison == 0) {
					swap(x, a++, b);
				}

				b++;
			}
		}
	}

	private static long med3(double[][] x, long a, long b, long c) {
		int ab = Double.compare(get(x, a), get(x, b));
		int ac = Double.compare(get(x, a), get(x, c));
		int bc = Double.compare(get(x, b), get(x, c));
		return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
	}

	private static void selectionSort(double[][] a, long from, long to) {
		for (long i = from; i < to - 1L; i++) {
			long m = i;

			for (long j = i + 1L; j < to; j++) {
				if (Double.compare(get(a, j), get(a, m)) < 0) {
					m = j;
				}
			}

			if (m != i) {
				swap(a, i, m);
			}
		}
	}

	public static void quickSort(double[][] x, DoubleComparator comp) {
		quickSort(x, 0L, length(x), comp);
	}

	public static void quickSort(double[][] x, long from, long to) {
		long len = to - from;
		if (len < 7L) {
			selectionSort(x, from, to);
		} else {
			long m = from + len / 2L;
			if (len > 7L) {
				long l = from;
				long n = to - 1L;
				if (len > 40L) {
					long s = len / 8L;
					l = med3(x, from, from + s, from + 2L * s);
					m = med3(x, m - s, m, m + s);
					n = med3(x, n - 2L * s, n - s, n);
				}

				m = med3(x, l, m, n);
			}

			double v = get(x, m);
			long a = from;
			long b = from;
			long c = to - 1L;
			long d = c;

			while (true) {
				int comparison;
				while (b > c || (comparison = Double.compare(get(x, b), v)) > 0) {
					for (; c >= b && (comparison = Double.compare(get(x, c), v)) >= 0; c--) {
						if (comparison == 0) {
							swap(x, c, d--);
						}
					}

					if (b > c) {
						long s = Math.min(a - from, b - a);
						vecSwap(x, from, b - s, s);
						long var28 = Math.min(d - c, to - d - 1L);
						vecSwap(x, b, to - var28, var28);
						long var29;
						if ((var29 = b - a) > 1L) {
							quickSort(x, from, from + var29);
						}

						long var30;
						if ((var30 = d - c) > 1L) {
							quickSort(x, to - var30, to);
						}

						return;
					}

					swap(x, b++, c--);
				}

				if (comparison == 0) {
					swap(x, a++, b);
				}

				b++;
			}
		}
	}

	public static void quickSort(double[][] x) {
		quickSort(x, 0L, length(x));
	}

	public static long binarySearch(double[][] a, long from, long to, double key) {
		to--;

		while (from <= to) {
			long mid = from + to >>> 1;
			double midVal = get(a, mid);
			if (midVal < key) {
				from = mid + 1L;
			} else {
				if (!(midVal > key)) {
					return mid;
				}

				to = mid - 1L;
			}
		}

		return -(from + 1L);
	}

	public static long binarySearch(double[][] a, double key) {
		return binarySearch(a, 0L, length(a), key);
	}

	public static long binarySearch(double[][] a, long from, long to, double key, DoubleComparator c) {
		to--;

		while (from <= to) {
			long mid = from + to >>> 1;
			double midVal = get(a, mid);
			int cmp = c.compare(midVal, key);
			if (cmp < 0) {
				from = mid + 1L;
			} else {
				if (cmp <= 0) {
					return mid;
				}

				to = mid - 1L;
			}
		}

		return -(from + 1L);
	}

	public static long binarySearch(double[][] a, double key, DoubleComparator c) {
		return binarySearch(a, 0L, length(a), key, c);
	}

	private static final long fixDouble(double d) {
		long l = Double.doubleToRawLongBits(d);
		return l >= 0L ? l : l ^ Long.MAX_VALUE;
	}

	public static void radixSort(double[][] a) {
		radixSort(a, 0L, length(a));
	}

	public static void radixSort(double[][] a, long from, long to) {
		int maxLevel = 7;
		int stackSize = 1786;
		long[] offsetStack = new long[1786];
		int offsetPos = 0;
		long[] lengthStack = new long[1786];
		int lengthPos = 0;
		int[] levelStack = new int[1786];
		int levelPos = 0;
		offsetStack[offsetPos++] = from;
		lengthStack[lengthPos++] = to - from;
		levelStack[levelPos++] = 0;
		long[] count = new long[256];
		long[] pos = new long[256];
		byte[][] digit = ByteBigArrays.newBigArray(to - from);

		while (offsetPos > 0) {
			long first = offsetStack[--offsetPos];
			long length = lengthStack[--lengthPos];
			int level = levelStack[--levelPos];
			int signMask = level % 8 == 0 ? 128 : 0;
			if (length < 40L) {
				selectionSort(a, first, first + length);
			} else {
				int shift = (7 - level % 8) * 8;
				long i = length;

				while (i-- != 0L) {
					ByteBigArrays.set(digit, i, (byte)((int)(fixDouble(get(a, first + i)) >>> shift & 255L ^ (long)signMask)));
				}

				i = length;

				while (i-- != 0L) {
					count[ByteBigArrays.get(digit, i) & 255]++;
				}

				int lastUsed = -1;
				long p = 0L;

				for (int ix = 0; ix < 256; ix++) {
					if (count[ix] != 0L) {
						lastUsed = ix;
						if (level < 7 && count[ix] > 1L) {
							offsetStack[offsetPos++] = p + first;
							lengthStack[lengthPos++] = count[ix];
							levelStack[levelPos++] = level + 1;
						}
					}

					pos[ix] = p += count[ix];
				}

				long end = length - count[lastUsed];
				count[lastUsed] = 0L;
				int c = -1;
				long ix = 0L;

				while (ix < end) {
					double t = get(a, ix + first);
					c = ByteBigArrays.get(digit, ix) & 255;

					long d;
					while ((d = --pos[c]) > ix) {
						double z = t;
						int zz = c;
						t = get(a, d + first);
						c = ByteBigArrays.get(digit, d) & 255;
						set(a, d + first, z);
						ByteBigArrays.set(digit, d, (byte)zz);
					}

					set(a, ix + first, t);
					ix += count[c];
					count[c] = 0L;
				}
			}
		}
	}

	private static void selectionSort(double[][] a, double[][] b, long from, long to) {
		for (long i = from; i < to - 1L; i++) {
			long m = i;

			for (long j = i + 1L; j < to; j++) {
				if (Double.compare(get(a, j), get(a, m)) < 0 || Double.compare(get(a, j), get(a, m)) == 0 && Double.compare(get(b, j), get(b, m)) < 0) {
					m = j;
				}
			}

			if (m != i) {
				double t = get(a, i);
				set(a, i, get(a, m));
				set(a, m, t);
				t = get(b, i);
				set(b, i, get(b, m));
				set(b, m, t);
			}
		}
	}

	public static void radixSort(double[][] a, double[][] b) {
		radixSort(a, b, 0L, length(a));
	}

	public static void radixSort(double[][] a, double[][] b, long from, long to) {
		int layers = 2;
		if (length(a) != length(b)) {
			throw new IllegalArgumentException("Array size mismatch.");
		} else {
			int maxLevel = 15;
			int stackSize = 3826;
			long[] offsetStack = new long[3826];
			int offsetPos = 0;
			long[] lengthStack = new long[3826];
			int lengthPos = 0;
			int[] levelStack = new int[3826];
			int levelPos = 0;
			offsetStack[offsetPos++] = from;
			lengthStack[lengthPos++] = to - from;
			levelStack[levelPos++] = 0;
			long[] count = new long[256];
			long[] pos = new long[256];
			byte[][] digit = ByteBigArrays.newBigArray(to - from);

			while (offsetPos > 0) {
				long first = offsetStack[--offsetPos];
				long length = lengthStack[--lengthPos];
				int level = levelStack[--levelPos];
				int signMask = level % 8 == 0 ? 128 : 0;
				if (length < 40L) {
					selectionSort(a, b, first, first + length);
				} else {
					double[][] k = level < 8 ? a : b;
					int shift = (7 - level % 8) * 8;
					long i = length;

					while (i-- != 0L) {
						ByteBigArrays.set(digit, i, (byte)((int)(fixDouble(get(k, first + i)) >>> shift & 255L ^ (long)signMask)));
					}

					i = length;

					while (i-- != 0L) {
						count[ByteBigArrays.get(digit, i) & 255]++;
					}

					int lastUsed = -1;
					long p = 0L;

					for (int ix = 0; ix < 256; ix++) {
						if (count[ix] != 0L) {
							lastUsed = ix;
							if (level < 15 && count[ix] > 1L) {
								offsetStack[offsetPos++] = p + first;
								lengthStack[lengthPos++] = count[ix];
								levelStack[levelPos++] = level + 1;
							}
						}

						pos[ix] = p += count[ix];
					}

					long end = length - count[lastUsed];
					count[lastUsed] = 0L;
					int c = -1;
					long ix = 0L;

					while (ix < end) {
						double t = get(a, ix + first);
						double u = get(b, ix + first);
						c = ByteBigArrays.get(digit, ix) & 255;

						long d;
						while ((d = --pos[c]) > ix) {
							double z = t;
							int zz = c;
							t = get(a, d + first);
							set(a, d + first, z);
							z = u;
							u = get(b, d + first);
							set(b, d + first, z);
							c = ByteBigArrays.get(digit, d) & 255;
							ByteBigArrays.set(digit, d, (byte)zz);
						}

						set(a, ix + first, t);
						set(b, ix + first, u);
						ix += count[c];
						count[c] = 0L;
					}
				}
			}
		}
	}

	public static double[][] shuffle(double[][] a, long from, long to, Random random) {
		long i = to - from;

		while (i-- != 0L) {
			long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
			double t = get(a, from + i);
			set(a, from + i, get(a, from + p));
			set(a, from + p, t);
		}

		return a;
	}

	public static double[][] shuffle(double[][] a, Random random) {
		long i = length(a);

		while (i-- != 0L) {
			long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
			double t = get(a, i);
			set(a, i, get(a, p));
			set(a, p, t);
		}

		return a;
	}

	private static final class BigArrayHashStrategy implements Strategy<double[][]>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;

		private BigArrayHashStrategy() {
		}

		public int hashCode(double[][] o) {
			return Arrays.deepHashCode(o);
		}

		public boolean equals(double[][] a, double[][] b) {
			return DoubleBigArrays.equals(a, b);
		}
	}
}
