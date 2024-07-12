package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.Hash.Strategy;
import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public final class ShortBigArrays {
	public static final short[][] EMPTY_BIG_ARRAY = new short[0][];
	public static final short[][] DEFAULT_EMPTY_BIG_ARRAY = new short[0][];
	public static final Strategy HASH_STRATEGY = new ShortBigArrays.BigArrayHashStrategy();
	private static final int SMALL = 7;
	private static final int MEDIUM = 40;
	private static final int DIGIT_BITS = 8;
	private static final int DIGIT_MASK = 255;
	private static final int DIGITS_PER_ELEMENT = 2;

	private ShortBigArrays() {
	}

	public static short get(short[][] array, long index) {
		return array[BigArrays.segment(index)][BigArrays.displacement(index)];
	}

	public static void set(short[][] array, long index, short value) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)] = value;
	}

	public static void swap(short[][] array, long first, long second) {
		short t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
		array[BigArrays.segment(first)][BigArrays.displacement(first)] = array[BigArrays.segment(second)][BigArrays.displacement(second)];
		array[BigArrays.segment(second)][BigArrays.displacement(second)] = t;
	}

	public static void add(short[][] array, long index, short incr) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)] += incr;
	}

	public static void mul(short[][] array, long index, short factor) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)] *= factor;
	}

	public static void incr(short[][] array, long index) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)]++;
	}

	public static void decr(short[][] array, long index) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)]--;
	}

	public static long length(short[][] array) {
		int length = array.length;
		return length == 0 ? 0L : BigArrays.start(length - 1) + (long)array[length - 1].length;
	}

	public static void copy(short[][] srcArray, long srcPos, short[][] destArray, long destPos, long length) {
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

	public static void copyFromBig(short[][] srcArray, long srcPos, short[] destArray, int destPos, int length) {
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

	public static void copyToBig(short[] srcArray, int srcPos, short[][] destArray, long destPos, long length) {
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

	public static short[][] newBigArray(long length) {
		if (length == 0L) {
			return EMPTY_BIG_ARRAY;
		} else {
			BigArrays.ensureLength(length);
			int baseLength = (int)(length + 134217727L >>> 27);
			short[][] base = new short[baseLength][];
			int residual = (int)(length & 134217727L);
			if (residual != 0) {
				for (int i = 0; i < baseLength - 1; i++) {
					base[i] = new short[134217728];
				}

				base[baseLength - 1] = new short[residual];
			} else {
				for (int i = 0; i < baseLength; i++) {
					base[i] = new short[134217728];
				}
			}

			return base;
		}
	}

	public static short[][] wrap(short[] array) {
		if (array.length == 0) {
			return EMPTY_BIG_ARRAY;
		} else if (array.length <= 134217728) {
			return new short[][]{array};
		} else {
			short[][] bigArray = newBigArray((long)array.length);

			for (int i = 0; i < bigArray.length; i++) {
				System.arraycopy(array, (int)BigArrays.start(i), bigArray[i], 0, bigArray[i].length);
			}

			return bigArray;
		}
	}

	public static short[][] ensureCapacity(short[][] array, long length) {
		return ensureCapacity(array, length, length(array));
	}

	public static short[][] forceCapacity(short[][] array, long length, long preserve) {
		BigArrays.ensureLength(length);
		int valid = array.length - (array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728) ? 1 : 0);
		int baseLength = (int)(length + 134217727L >>> 27);
		short[][] base = (short[][])Arrays.copyOf(array, baseLength);
		int residual = (int)(length & 134217727L);
		if (residual != 0) {
			for (int i = valid; i < baseLength - 1; i++) {
				base[i] = new short[134217728];
			}

			base[baseLength - 1] = new short[residual];
		} else {
			for (int i = valid; i < baseLength; i++) {
				base[i] = new short[134217728];
			}
		}

		if (preserve - (long)valid * 134217728L > 0L) {
			copy(array, (long)valid * 134217728L, base, (long)valid * 134217728L, preserve - (long)valid * 134217728L);
		}

		return base;
	}

	public static short[][] ensureCapacity(short[][] array, long length, long preserve) {
		return length > length(array) ? forceCapacity(array, length, preserve) : array;
	}

	public static short[][] grow(short[][] array, long length) {
		long oldLength = length(array);
		return length > oldLength ? grow(array, length, oldLength) : array;
	}

	public static short[][] grow(short[][] array, long length, long preserve) {
		long oldLength = length(array);
		return length > oldLength ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve) : array;
	}

	public static short[][] trim(short[][] array, long length) {
		BigArrays.ensureLength(length);
		long oldLength = length(array);
		if (length >= oldLength) {
			return array;
		} else {
			int baseLength = (int)(length + 134217727L >>> 27);
			short[][] base = (short[][])Arrays.copyOf(array, baseLength);
			int residual = (int)(length & 134217727L);
			if (residual != 0) {
				base[baseLength - 1] = ShortArrays.trim(base[baseLength - 1], residual);
			}

			return base;
		}
	}

	public static short[][] setLength(short[][] array, long length) {
		long oldLength = length(array);
		if (length == oldLength) {
			return array;
		} else {
			return length < oldLength ? trim(array, length) : ensureCapacity(array, length);
		}
	}

	public static short[][] copy(short[][] array, long offset, long length) {
		ensureOffsetLength(array, offset, length);
		short[][] a = newBigArray(length);
		copy(array, offset, a, 0L, length);
		return a;
	}

	public static short[][] copy(short[][] array) {
		short[][] base = (short[][])array.clone();
		int i = base.length;

		while (i-- != 0) {
			base[i] = (short[])array[i].clone();
		}

		return base;
	}

	public static void fill(short[][] array, short value) {
		int i = array.length;

		while (i-- != 0) {
			Arrays.fill(array[i], value);
		}
	}

	public static void fill(short[][] array, long from, long to, short value) {
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

	public static boolean equals(short[][] a1, short[][] a2) {
		if (length(a1) != length(a2)) {
			return false;
		} else {
			int i = a1.length;

			while (i-- != 0) {
				short[] t = a1[i];
				short[] u = a2[i];
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

	public static String toString(short[][] a) {
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

	public static void ensureFromTo(short[][] a, long from, long to) {
		BigArrays.ensureFromTo(length(a), from, to);
	}

	public static void ensureOffsetLength(short[][] a, long offset, long length) {
		BigArrays.ensureOffsetLength(length(a), offset, length);
	}

	private static void vecSwap(short[][] x, long a, long b, long n) {
		int i = 0;

		while ((long)i < n) {
			swap(x, a, b);
			i++;
			a++;
			b++;
		}
	}

	private static long med3(short[][] x, long a, long b, long c, ShortComparator comp) {
		int ab = comp.compare(get(x, a), get(x, b));
		int ac = comp.compare(get(x, a), get(x, c));
		int bc = comp.compare(get(x, b), get(x, c));
		return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
	}

	private static void selectionSort(short[][] a, long from, long to, ShortComparator comp) {
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

	public static void quickSort(short[][] x, long from, long to, ShortComparator comp) {
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

			short v = get(x, m);
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

	private static long med3(short[][] x, long a, long b, long c) {
		int ab = Short.compare(get(x, a), get(x, b));
		int ac = Short.compare(get(x, a), get(x, c));
		int bc = Short.compare(get(x, b), get(x, c));
		return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
	}

	private static void selectionSort(short[][] a, long from, long to) {
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

	public static void quickSort(short[][] x, ShortComparator comp) {
		quickSort(x, 0L, length(x), comp);
	}

	public static void quickSort(short[][] x, long from, long to) {
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

			short v = get(x, m);
			long a = from;
			long b = from;
			long c = to - 1L;
			long d = c;

			while (true) {
				int comparison;
				while (b > c || (comparison = Short.compare(get(x, b), v)) > 0) {
					for (; c >= b && (comparison = Short.compare(get(x, c), v)) >= 0; c--) {
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

	public static void quickSort(short[][] x) {
		quickSort(x, 0L, length(x));
	}

	public static long binarySearch(short[][] a, long from, long to, short key) {
		to--;

		while (from <= to) {
			long mid = from + to >>> 1;
			short midVal = get(a, mid);
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

	public static long binarySearch(short[][] a, short key) {
		return binarySearch(a, 0L, length(a), key);
	}

	public static long binarySearch(short[][] a, long from, long to, short key, ShortComparator c) {
		to--;

		while (from <= to) {
			long mid = from + to >>> 1;
			short midVal = get(a, mid);
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

	public static long binarySearch(short[][] a, short key, ShortComparator c) {
		return binarySearch(a, 0L, length(a), key, c);
	}

	public static void radixSort(short[][] a) {
		radixSort(a, 0L, length(a));
	}

	public static void radixSort(short[][] a, long from, long to) {
		int maxLevel = 1;
		int stackSize = 256;
		long[] offsetStack = new long[256];
		int offsetPos = 0;
		long[] lengthStack = new long[256];
		int lengthPos = 0;
		int[] levelStack = new int[256];
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
			int signMask = level % 2 == 0 ? 128 : 0;
			if (length < 40L) {
				selectionSort(a, first, first + length);
			} else {
				int shift = (1 - level % 2) * 8;
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
						if (level < 1 && count[ix] > 1L) {
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
					short t = get(a, ix + first);
					c = ByteBigArrays.get(digit, ix) & 255;

					long d;
					while ((d = --pos[c]) > ix) {
						short z = t;
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

	private static void selectionSort(short[][] a, short[][] b, long from, long to) {
		for (long i = from; i < to - 1L; i++) {
			long m = i;

			for (long j = i + 1L; j < to; j++) {
				if (get(a, j) < get(a, m) || get(a, j) == get(a, m) && get(b, j) < get(b, m)) {
					m = j;
				}
			}

			if (m != i) {
				short t = get(a, i);
				set(a, i, get(a, m));
				set(a, m, t);
				t = get(b, i);
				set(b, i, get(b, m));
				set(b, m, t);
			}
		}
	}

	public static void radixSort(short[][] a, short[][] b) {
		radixSort(a, b, 0L, length(a));
	}

	public static void radixSort(short[][] a, short[][] b, long from, long to) {
		int layers = 2;
		if (length(a) != length(b)) {
			throw new IllegalArgumentException("Array size mismatch.");
		} else {
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
				int signMask = level % 2 == 0 ? 128 : 0;
				if (length < 40L) {
					selectionSort(a, b, first, first + length);
				} else {
					short[][] k = level < 2 ? a : b;
					int shift = (1 - level % 2) * 8;
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
						short t = get(a, ix + first);
						short u = get(b, ix + first);
						c = ByteBigArrays.get(digit, ix) & 255;

						long d;
						while ((d = --pos[c]) > ix) {
							short z = t;
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

	public static short[][] shuffle(short[][] a, long from, long to, Random random) {
		long i = to - from;

		while (i-- != 0L) {
			long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
			short t = get(a, from + i);
			set(a, from + i, get(a, from + p));
			set(a, from + p, t);
		}

		return a;
	}

	public static short[][] shuffle(short[][] a, Random random) {
		long i = length(a);

		while (i-- != 0L) {
			long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
			short t = get(a, i);
			set(a, i, get(a, p));
			set(a, p, t);
		}

		return a;
	}

	private static final class BigArrayHashStrategy implements Strategy<short[][]>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;

		private BigArrayHashStrategy() {
		}

		public int hashCode(short[][] o) {
			return Arrays.deepHashCode(o);
		}

		public boolean equals(short[][] a, short[][] b) {
			return ShortBigArrays.equals(a, b);
		}
	}
}
