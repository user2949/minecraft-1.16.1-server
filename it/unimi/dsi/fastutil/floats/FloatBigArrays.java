package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.Hash.Strategy;
import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public final class FloatBigArrays {
	public static final float[][] EMPTY_BIG_ARRAY = new float[0][];
	public static final float[][] DEFAULT_EMPTY_BIG_ARRAY = new float[0][];
	public static final Strategy HASH_STRATEGY = new FloatBigArrays.BigArrayHashStrategy();
	private static final int SMALL = 7;
	private static final int MEDIUM = 40;
	private static final int DIGIT_BITS = 8;
	private static final int DIGIT_MASK = 255;
	private static final int DIGITS_PER_ELEMENT = 4;

	private FloatBigArrays() {
	}

	public static float get(float[][] array, long index) {
		return array[BigArrays.segment(index)][BigArrays.displacement(index)];
	}

	public static void set(float[][] array, long index, float value) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)] = value;
	}

	public static void swap(float[][] array, long first, long second) {
		float t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
		array[BigArrays.segment(first)][BigArrays.displacement(first)] = array[BigArrays.segment(second)][BigArrays.displacement(second)];
		array[BigArrays.segment(second)][BigArrays.displacement(second)] = t;
	}

	public static void add(float[][] array, long index, float incr) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)] += incr;
	}

	public static void mul(float[][] array, long index, float factor) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)] *= factor;
	}

	public static void incr(float[][] array, long index) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)]++;
	}

	public static void decr(float[][] array, long index) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)]--;
	}

	public static long length(float[][] array) {
		int length = array.length;
		return length == 0 ? 0L : BigArrays.start(length - 1) + (long)array[length - 1].length;
	}

	public static void copy(float[][] srcArray, long srcPos, float[][] destArray, long destPos, long length) {
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

	public static void copyFromBig(float[][] srcArray, long srcPos, float[] destArray, int destPos, int length) {
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

	public static void copyToBig(float[] srcArray, int srcPos, float[][] destArray, long destPos, long length) {
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

	public static float[][] newBigArray(long length) {
		if (length == 0L) {
			return EMPTY_BIG_ARRAY;
		} else {
			BigArrays.ensureLength(length);
			int baseLength = (int)(length + 134217727L >>> 27);
			float[][] base = new float[baseLength][];
			int residual = (int)(length & 134217727L);
			if (residual != 0) {
				for (int i = 0; i < baseLength - 1; i++) {
					base[i] = new float[134217728];
				}

				base[baseLength - 1] = new float[residual];
			} else {
				for (int i = 0; i < baseLength; i++) {
					base[i] = new float[134217728];
				}
			}

			return base;
		}
	}

	public static float[][] wrap(float[] array) {
		if (array.length == 0) {
			return EMPTY_BIG_ARRAY;
		} else if (array.length <= 134217728) {
			return new float[][]{array};
		} else {
			float[][] bigArray = newBigArray((long)array.length);

			for (int i = 0; i < bigArray.length; i++) {
				System.arraycopy(array, (int)BigArrays.start(i), bigArray[i], 0, bigArray[i].length);
			}

			return bigArray;
		}
	}

	public static float[][] ensureCapacity(float[][] array, long length) {
		return ensureCapacity(array, length, length(array));
	}

	public static float[][] forceCapacity(float[][] array, long length, long preserve) {
		BigArrays.ensureLength(length);
		int valid = array.length - (array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728) ? 1 : 0);
		int baseLength = (int)(length + 134217727L >>> 27);
		float[][] base = (float[][])Arrays.copyOf(array, baseLength);
		int residual = (int)(length & 134217727L);
		if (residual != 0) {
			for (int i = valid; i < baseLength - 1; i++) {
				base[i] = new float[134217728];
			}

			base[baseLength - 1] = new float[residual];
		} else {
			for (int i = valid; i < baseLength; i++) {
				base[i] = new float[134217728];
			}
		}

		if (preserve - (long)valid * 134217728L > 0L) {
			copy(array, (long)valid * 134217728L, base, (long)valid * 134217728L, preserve - (long)valid * 134217728L);
		}

		return base;
	}

	public static float[][] ensureCapacity(float[][] array, long length, long preserve) {
		return length > length(array) ? forceCapacity(array, length, preserve) : array;
	}

	public static float[][] grow(float[][] array, long length) {
		long oldLength = length(array);
		return length > oldLength ? grow(array, length, oldLength) : array;
	}

	public static float[][] grow(float[][] array, long length, long preserve) {
		long oldLength = length(array);
		return length > oldLength ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve) : array;
	}

	public static float[][] trim(float[][] array, long length) {
		BigArrays.ensureLength(length);
		long oldLength = length(array);
		if (length >= oldLength) {
			return array;
		} else {
			int baseLength = (int)(length + 134217727L >>> 27);
			float[][] base = (float[][])Arrays.copyOf(array, baseLength);
			int residual = (int)(length & 134217727L);
			if (residual != 0) {
				base[baseLength - 1] = FloatArrays.trim(base[baseLength - 1], residual);
			}

			return base;
		}
	}

	public static float[][] setLength(float[][] array, long length) {
		long oldLength = length(array);
		if (length == oldLength) {
			return array;
		} else {
			return length < oldLength ? trim(array, length) : ensureCapacity(array, length);
		}
	}

	public static float[][] copy(float[][] array, long offset, long length) {
		ensureOffsetLength(array, offset, length);
		float[][] a = newBigArray(length);
		copy(array, offset, a, 0L, length);
		return a;
	}

	public static float[][] copy(float[][] array) {
		float[][] base = (float[][])array.clone();
		int i = base.length;

		while (i-- != 0) {
			base[i] = (float[])array[i].clone();
		}

		return base;
	}

	public static void fill(float[][] array, float value) {
		int i = array.length;

		while (i-- != 0) {
			Arrays.fill(array[i], value);
		}
	}

	public static void fill(float[][] array, long from, long to, float value) {
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

	public static boolean equals(float[][] a1, float[][] a2) {
		if (length(a1) != length(a2)) {
			return false;
		} else {
			int i = a1.length;

			while (i-- != 0) {
				float[] t = a1[i];
				float[] u = a2[i];
				int j = t.length;

				while (j-- != 0) {
					if (Float.floatToIntBits(t[j]) != Float.floatToIntBits(u[j])) {
						return false;
					}
				}
			}

			return true;
		}
	}

	public static String toString(float[][] a) {
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

	public static void ensureFromTo(float[][] a, long from, long to) {
		BigArrays.ensureFromTo(length(a), from, to);
	}

	public static void ensureOffsetLength(float[][] a, long offset, long length) {
		BigArrays.ensureOffsetLength(length(a), offset, length);
	}

	private static void vecSwap(float[][] x, long a, long b, long n) {
		int i = 0;

		while ((long)i < n) {
			swap(x, a, b);
			i++;
			a++;
			b++;
		}
	}

	private static long med3(float[][] x, long a, long b, long c, FloatComparator comp) {
		int ab = comp.compare(get(x, a), get(x, b));
		int ac = comp.compare(get(x, a), get(x, c));
		int bc = comp.compare(get(x, b), get(x, c));
		return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
	}

	private static void selectionSort(float[][] a, long from, long to, FloatComparator comp) {
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

	public static void quickSort(float[][] x, long from, long to, FloatComparator comp) {
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

			float v = get(x, m);
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

	private static long med3(float[][] x, long a, long b, long c) {
		int ab = Float.compare(get(x, a), get(x, b));
		int ac = Float.compare(get(x, a), get(x, c));
		int bc = Float.compare(get(x, b), get(x, c));
		return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
	}

	private static void selectionSort(float[][] a, long from, long to) {
		for (long i = from; i < to - 1L; i++) {
			long m = i;

			for (long j = i + 1L; j < to; j++) {
				if (Float.compare(get(a, j), get(a, m)) < 0) {
					m = j;
				}
			}

			if (m != i) {
				swap(a, i, m);
			}
		}
	}

	public static void quickSort(float[][] x, FloatComparator comp) {
		quickSort(x, 0L, length(x), comp);
	}

	public static void quickSort(float[][] x, long from, long to) {
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

			float v = get(x, m);
			long a = from;
			long b = from;
			long c = to - 1L;
			long d = c;

			while (true) {
				int comparison;
				while (b > c || (comparison = Float.compare(get(x, b), v)) > 0) {
					for (; c >= b && (comparison = Float.compare(get(x, c), v)) >= 0; c--) {
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

	public static void quickSort(float[][] x) {
		quickSort(x, 0L, length(x));
	}

	public static long binarySearch(float[][] a, long from, long to, float key) {
		to--;

		while (from <= to) {
			long mid = from + to >>> 1;
			float midVal = get(a, mid);
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

	public static long binarySearch(float[][] a, float key) {
		return binarySearch(a, 0L, length(a), key);
	}

	public static long binarySearch(float[][] a, long from, long to, float key, FloatComparator c) {
		to--;

		while (from <= to) {
			long mid = from + to >>> 1;
			float midVal = get(a, mid);
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

	public static long binarySearch(float[][] a, float key, FloatComparator c) {
		return binarySearch(a, 0L, length(a), key, c);
	}

	private static final long fixFloat(float f) {
		long i = (long)Float.floatToRawIntBits(f);
		return i >= 0L ? i : i ^ 2147483647L;
	}

	public static void radixSort(float[][] a) {
		radixSort(a, 0L, length(a));
	}

	public static void radixSort(float[][] a, long from, long to) {
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
					ByteBigArrays.set(digit, i, (byte)((int)(fixFloat(get(a, first + i)) >>> shift & 255L ^ (long)signMask)));
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
					float t = get(a, ix + first);
					c = ByteBigArrays.get(digit, ix) & 255;

					long d;
					while ((d = --pos[c]) > ix) {
						float z = t;
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

	private static void selectionSort(float[][] a, float[][] b, long from, long to) {
		for (long i = from; i < to - 1L; i++) {
			long m = i;

			for (long j = i + 1L; j < to; j++) {
				if (Float.compare(get(a, j), get(a, m)) < 0 || Float.compare(get(a, j), get(a, m)) == 0 && Float.compare(get(b, j), get(b, m)) < 0) {
					m = j;
				}
			}

			if (m != i) {
				float t = get(a, i);
				set(a, i, get(a, m));
				set(a, m, t);
				t = get(b, i);
				set(b, i, get(b, m));
				set(b, m, t);
			}
		}
	}

	public static void radixSort(float[][] a, float[][] b) {
		radixSort(a, b, 0L, length(a));
	}

	public static void radixSort(float[][] a, float[][] b, long from, long to) {
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
					float[][] k = level < 4 ? a : b;
					int shift = (3 - level % 4) * 8;
					long i = length;

					while (i-- != 0L) {
						ByteBigArrays.set(digit, i, (byte)((int)(fixFloat(get(k, first + i)) >>> shift & 255L ^ (long)signMask)));
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
						float t = get(a, ix + first);
						float u = get(b, ix + first);
						c = ByteBigArrays.get(digit, ix) & 255;

						long d;
						while ((d = --pos[c]) > ix) {
							float z = t;
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

	public static float[][] shuffle(float[][] a, long from, long to, Random random) {
		long i = to - from;

		while (i-- != 0L) {
			long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
			float t = get(a, from + i);
			set(a, from + i, get(a, from + p));
			set(a, from + p, t);
		}

		return a;
	}

	public static float[][] shuffle(float[][] a, Random random) {
		long i = length(a);

		while (i-- != 0L) {
			long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
			float t = get(a, i);
			set(a, i, get(a, p));
			set(a, p, t);
		}

		return a;
	}

	private static final class BigArrayHashStrategy implements Strategy<float[][]>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;

		private BigArrayHashStrategy() {
		}

		public int hashCode(float[][] o) {
			return Arrays.deepHashCode(o);
		}

		public boolean equals(float[][] a, float[][] b) {
			return FloatBigArrays.equals(a, b);
		}
	}
}
