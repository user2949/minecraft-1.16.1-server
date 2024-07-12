package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.Hash.Strategy;
import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public final class IntBigArrays {
	public static final int[][] EMPTY_BIG_ARRAY = new int[0][];
	public static final int[][] DEFAULT_EMPTY_BIG_ARRAY = new int[0][];
	public static final Strategy HASH_STRATEGY = new IntBigArrays.BigArrayHashStrategy();
	private static final int SMALL = 7;
	private static final int MEDIUM = 40;
	private static final int DIGIT_BITS = 8;
	private static final int DIGIT_MASK = 255;
	private static final int DIGITS_PER_ELEMENT = 4;

	private IntBigArrays() {
	}

	public static int get(int[][] array, long index) {
		return array[BigArrays.segment(index)][BigArrays.displacement(index)];
	}

	public static void set(int[][] array, long index, int value) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)] = value;
	}

	public static void swap(int[][] array, long first, long second) {
		int t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
		array[BigArrays.segment(first)][BigArrays.displacement(first)] = array[BigArrays.segment(second)][BigArrays.displacement(second)];
		array[BigArrays.segment(second)][BigArrays.displacement(second)] = t;
	}

	public static void add(int[][] array, long index, int incr) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)] += incr;
	}

	public static void mul(int[][] array, long index, int factor) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)] *= factor;
	}

	public static void incr(int[][] array, long index) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)]++;
	}

	public static void decr(int[][] array, long index) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)]--;
	}

	public static long length(int[][] array) {
		int length = array.length;
		return length == 0 ? 0L : BigArrays.start(length - 1) + (long)array[length - 1].length;
	}

	public static void copy(int[][] srcArray, long srcPos, int[][] destArray, long destPos, long length) {
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

	public static void copyFromBig(int[][] srcArray, long srcPos, int[] destArray, int destPos, int length) {
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

	public static void copyToBig(int[] srcArray, int srcPos, int[][] destArray, long destPos, long length) {
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

	public static int[][] newBigArray(long length) {
		if (length == 0L) {
			return EMPTY_BIG_ARRAY;
		} else {
			BigArrays.ensureLength(length);
			int baseLength = (int)(length + 134217727L >>> 27);
			int[][] base = new int[baseLength][];
			int residual = (int)(length & 134217727L);
			if (residual != 0) {
				for (int i = 0; i < baseLength - 1; i++) {
					base[i] = new int[134217728];
				}

				base[baseLength - 1] = new int[residual];
			} else {
				for (int i = 0; i < baseLength; i++) {
					base[i] = new int[134217728];
				}
			}

			return base;
		}
	}

	public static int[][] wrap(int[] array) {
		if (array.length == 0) {
			return EMPTY_BIG_ARRAY;
		} else if (array.length <= 134217728) {
			return new int[][]{array};
		} else {
			int[][] bigArray = newBigArray((long)array.length);

			for (int i = 0; i < bigArray.length; i++) {
				System.arraycopy(array, (int)BigArrays.start(i), bigArray[i], 0, bigArray[i].length);
			}

			return bigArray;
		}
	}

	public static int[][] ensureCapacity(int[][] array, long length) {
		return ensureCapacity(array, length, length(array));
	}

	public static int[][] forceCapacity(int[][] array, long length, long preserve) {
		BigArrays.ensureLength(length);
		int valid = array.length - (array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728) ? 1 : 0);
		int baseLength = (int)(length + 134217727L >>> 27);
		int[][] base = (int[][])Arrays.copyOf(array, baseLength);
		int residual = (int)(length & 134217727L);
		if (residual != 0) {
			for (int i = valid; i < baseLength - 1; i++) {
				base[i] = new int[134217728];
			}

			base[baseLength - 1] = new int[residual];
		} else {
			for (int i = valid; i < baseLength; i++) {
				base[i] = new int[134217728];
			}
		}

		if (preserve - (long)valid * 134217728L > 0L) {
			copy(array, (long)valid * 134217728L, base, (long)valid * 134217728L, preserve - (long)valid * 134217728L);
		}

		return base;
	}

	public static int[][] ensureCapacity(int[][] array, long length, long preserve) {
		return length > length(array) ? forceCapacity(array, length, preserve) : array;
	}

	public static int[][] grow(int[][] array, long length) {
		long oldLength = length(array);
		return length > oldLength ? grow(array, length, oldLength) : array;
	}

	public static int[][] grow(int[][] array, long length, long preserve) {
		long oldLength = length(array);
		return length > oldLength ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve) : array;
	}

	public static int[][] trim(int[][] array, long length) {
		BigArrays.ensureLength(length);
		long oldLength = length(array);
		if (length >= oldLength) {
			return array;
		} else {
			int baseLength = (int)(length + 134217727L >>> 27);
			int[][] base = (int[][])Arrays.copyOf(array, baseLength);
			int residual = (int)(length & 134217727L);
			if (residual != 0) {
				base[baseLength - 1] = IntArrays.trim(base[baseLength - 1], residual);
			}

			return base;
		}
	}

	public static int[][] setLength(int[][] array, long length) {
		long oldLength = length(array);
		if (length == oldLength) {
			return array;
		} else {
			return length < oldLength ? trim(array, length) : ensureCapacity(array, length);
		}
	}

	public static int[][] copy(int[][] array, long offset, long length) {
		ensureOffsetLength(array, offset, length);
		int[][] a = newBigArray(length);
		copy(array, offset, a, 0L, length);
		return a;
	}

	public static int[][] copy(int[][] array) {
		int[][] base = (int[][])array.clone();
		int i = base.length;

		while (i-- != 0) {
			base[i] = (int[])array[i].clone();
		}

		return base;
	}

	public static void fill(int[][] array, int value) {
		int i = array.length;

		while (i-- != 0) {
			Arrays.fill(array[i], value);
		}
	}

	public static void fill(int[][] array, long from, long to, int value) {
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

	public static boolean equals(int[][] a1, int[][] a2) {
		if (length(a1) != length(a2)) {
			return false;
		} else {
			int i = a1.length;

			while (i-- != 0) {
				int[] t = a1[i];
				int[] u = a2[i];
				int j = t.length;

				while (j-- != 0) {
					if (t[j] != u[j]) {
						return false;
					}
				}
			}

			return true;
		}
	}

	public static String toString(int[][] a) {
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

	public static void ensureFromTo(int[][] a, long from, long to) {
		BigArrays.ensureFromTo(length(a), from, to);
	}

	public static void ensureOffsetLength(int[][] a, long offset, long length) {
		BigArrays.ensureOffsetLength(length(a), offset, length);
	}

	private static void vecSwap(int[][] x, long a, long b, long n) {
		int i = 0;

		while ((long)i < n) {
			swap(x, a, b);
			i++;
			a++;
			b++;
		}
	}

	private static long med3(int[][] x, long a, long b, long c, IntComparator comp) {
		int ab = comp.compare(get(x, a), get(x, b));
		int ac = comp.compare(get(x, a), get(x, c));
		int bc = comp.compare(get(x, b), get(x, c));
		return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
	}

	private static void selectionSort(int[][] a, long from, long to, IntComparator comp) {
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

	public static void quickSort(int[][] x, long from, long to, IntComparator comp) {
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

			int v = get(x, m);
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
						long var26 = Math.min(d - c, to - d - 1L);
						vecSwap(x, b, to - var26, var26);
						long var27;
						if ((var27 = b - a) > 1L) {
							quickSort(x, from, from + var27, comp);
						}

						long var28;
						if ((var28 = d - c) > 1L) {
							quickSort(x, to - var28, to, comp);
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

	private static long med3(int[][] x, long a, long b, long c) {
		int ab = Integer.compare(get(x, a), get(x, b));
		int ac = Integer.compare(get(x, a), get(x, c));
		int bc = Integer.compare(get(x, b), get(x, c));
		return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
	}

	private static void selectionSort(int[][] a, long from, long to) {
		for (long i = from; i < to - 1L; i++) {
			long m = i;

			for (long j = i + 1L; j < to; j++) {
				if (get(a, j) < get(a, m)) {
					m = j;
				}
			}

			if (m != i) {
				swap(a, i, m);
			}
		}
	}

	public static void quickSort(int[][] x, IntComparator comp) {
		quickSort(x, 0L, length(x), comp);
	}

	public static void quickSort(int[][] x, long from, long to) {
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

			int v = get(x, m);
			long a = from;
			long b = from;
			long c = to - 1L;
			long d = c;

			while (true) {
				int comparison;
				while (b > c || (comparison = Integer.compare(get(x, b), v)) > 0) {
					for (; c >= b && (comparison = Integer.compare(get(x, c), v)) >= 0; c--) {
						if (comparison == 0) {
							swap(x, c, d--);
						}
					}

					if (b > c) {
						long s = Math.min(a - from, b - a);
						vecSwap(x, from, b - s, s);
						long var25 = Math.min(d - c, to - d - 1L);
						vecSwap(x, b, to - var25, var25);
						long var26;
						if ((var26 = b - a) > 1L) {
							quickSort(x, from, from + var26);
						}

						long var27;
						if ((var27 = d - c) > 1L) {
							quickSort(x, to - var27, to);
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

	public static void quickSort(int[][] x) {
		quickSort(x, 0L, length(x));
	}

	public static long binarySearch(int[][] a, long from, long to, int key) {
		to--;

		while (from <= to) {
			long mid = from + to >>> 1;
			int midVal = get(a, mid);
			if (midVal < key) {
				from = mid + 1L;
			} else {
				if (midVal <= key) {
					return mid;
				}

				to = mid - 1L;
			}
		}

		return -(from + 1L);
	}

	public static long binarySearch(int[][] a, int key) {
		return binarySearch(a, 0L, length(a), key);
	}

	public static long binarySearch(int[][] a, long from, long to, int key, IntComparator c) {
		to--;

		while (from <= to) {
			long mid = from + to >>> 1;
			int midVal = get(a, mid);
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

	public static long binarySearch(int[][] a, int key, IntComparator c) {
		return binarySearch(a, 0L, length(a), key, c);
	}

	public static void radixSort(int[][] a) {
		radixSort(a, 0L, length(a));
	}

	public static void radixSort(int[][] a, long from, long to) {
		int maxLevel = 3;
		int stackSize = 766;
		long[] offsetStack = new long[766];
		int offsetPos = 0;
		long[] lengthStack = new long[766];
		int lengthPos = 0;
		int[] levelStack = new int[766];
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
			int signMask = level % 4 == 0 ? 128 : 0;
			if (length < 40L) {
				selectionSort(a, first, first + length);
			} else {
				int shift = (3 - level % 4) * 8;
				long i = length;

				while (i-- != 0L) {
					ByteBigArrays.set(digit, i, (byte)(get(a, first + i) >>> shift & 0xFF ^ signMask));
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
						if (level < 3 && count[ix] > 1L) {
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
					int t = get(a, ix + first);
					c = ByteBigArrays.get(digit, ix) & 255;

					long d;
					while ((d = --pos[c]) > ix) {
						int z = t;
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

	private static void selectionSort(int[][] a, int[][] b, long from, long to) {
		for (long i = from; i < to - 1L; i++) {
			long m = i;

			for (long j = i + 1L; j < to; j++) {
				if (get(a, j) < get(a, m) || get(a, j) == get(a, m) && get(b, j) < get(b, m)) {
					m = j;
				}
			}

			if (m != i) {
				int t = get(a, i);
				set(a, i, get(a, m));
				set(a, m, t);
				t = get(b, i);
				set(b, i, get(b, m));
				set(b, m, t);
			}
		}
	}

	public static void radixSort(int[][] a, int[][] b) {
		radixSort(a, b, 0L, length(a));
	}

	public static void radixSort(int[][] a, int[][] b, long from, long to) {
		int layers = 2;
		if (length(a) != length(b)) {
			throw new IllegalArgumentException("Array size mismatch.");
		} else {
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
				int signMask = level % 4 == 0 ? 128 : 0;
				if (length < 40L) {
					selectionSort(a, b, first, first + length);
				} else {
					int[][] k = level < 4 ? a : b;
					int shift = (3 - level % 4) * 8;
					long i = length;

					while (i-- != 0L) {
						ByteBigArrays.set(digit, i, (byte)(get(k, first + i) >>> shift & 0xFF ^ signMask));
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
						int t = get(a, ix + first);
						int u = get(b, ix + first);
						c = ByteBigArrays.get(digit, ix) & 255;

						long d;
						while ((d = --pos[c]) > ix) {
							int z = t;
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

	public static int[][] shuffle(int[][] a, long from, long to, Random random) {
		long i = to - from;

		while (i-- != 0L) {
			long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
			int t = get(a, from + i);
			set(a, from + i, get(a, from + p));
			set(a, from + p, t);
		}

		return a;
	}

	public static int[][] shuffle(int[][] a, Random random) {
		long i = length(a);

		while (i-- != 0L) {
			long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
			int t = get(a, i);
			set(a, i, get(a, p));
			set(a, p, t);
		}

		return a;
	}

	private static final class BigArrayHashStrategy implements Strategy<int[][]>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;

		private BigArrayHashStrategy() {
		}

		public int hashCode(int[][] o) {
			return Arrays.deepHashCode(o);
		}

		public boolean equals(int[][] a, int[][] b) {
			return IntBigArrays.equals(a, b);
		}
	}
}
