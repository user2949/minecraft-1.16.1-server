package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.Hash.Strategy;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public final class BooleanBigArrays {
	public static final boolean[][] EMPTY_BIG_ARRAY = new boolean[0][];
	public static final boolean[][] DEFAULT_EMPTY_BIG_ARRAY = new boolean[0][];
	public static final Strategy HASH_STRATEGY = new BooleanBigArrays.BigArrayHashStrategy();
	private static final int SMALL = 7;
	private static final int MEDIUM = 40;

	private BooleanBigArrays() {
	}

	public static boolean get(boolean[][] array, long index) {
		return array[BigArrays.segment(index)][BigArrays.displacement(index)];
	}

	public static void set(boolean[][] array, long index, boolean value) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)] = value;
	}

	public static void swap(boolean[][] array, long first, long second) {
		boolean t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
		array[BigArrays.segment(first)][BigArrays.displacement(first)] = array[BigArrays.segment(second)][BigArrays.displacement(second)];
		array[BigArrays.segment(second)][BigArrays.displacement(second)] = t;
	}

	public static long length(boolean[][] array) {
		int length = array.length;
		return length == 0 ? 0L : BigArrays.start(length - 1) + (long)array[length - 1].length;
	}

	public static void copy(boolean[][] srcArray, long srcPos, boolean[][] destArray, long destPos, long length) {
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

	public static void copyFromBig(boolean[][] srcArray, long srcPos, boolean[] destArray, int destPos, int length) {
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

	public static void copyToBig(boolean[] srcArray, int srcPos, boolean[][] destArray, long destPos, long length) {
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

	public static boolean[][] newBigArray(long length) {
		if (length == 0L) {
			return EMPTY_BIG_ARRAY;
		} else {
			BigArrays.ensureLength(length);
			int baseLength = (int)(length + 134217727L >>> 27);
			boolean[][] base = new boolean[baseLength][];
			int residual = (int)(length & 134217727L);
			if (residual != 0) {
				for (int i = 0; i < baseLength - 1; i++) {
					base[i] = new boolean[134217728];
				}

				base[baseLength - 1] = new boolean[residual];
			} else {
				for (int i = 0; i < baseLength; i++) {
					base[i] = new boolean[134217728];
				}
			}

			return base;
		}
	}

	public static boolean[][] wrap(boolean[] array) {
		if (array.length == 0) {
			return EMPTY_BIG_ARRAY;
		} else if (array.length <= 134217728) {
			return new boolean[][]{array};
		} else {
			boolean[][] bigArray = newBigArray((long)array.length);

			for (int i = 0; i < bigArray.length; i++) {
				System.arraycopy(array, (int)BigArrays.start(i), bigArray[i], 0, bigArray[i].length);
			}

			return bigArray;
		}
	}

	public static boolean[][] ensureCapacity(boolean[][] array, long length) {
		return ensureCapacity(array, length, length(array));
	}

	public static boolean[][] forceCapacity(boolean[][] array, long length, long preserve) {
		BigArrays.ensureLength(length);
		int valid = array.length - (array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728) ? 1 : 0);
		int baseLength = (int)(length + 134217727L >>> 27);
		boolean[][] base = (boolean[][])Arrays.copyOf(array, baseLength);
		int residual = (int)(length & 134217727L);
		if (residual != 0) {
			for (int i = valid; i < baseLength - 1; i++) {
				base[i] = new boolean[134217728];
			}

			base[baseLength - 1] = new boolean[residual];
		} else {
			for (int i = valid; i < baseLength; i++) {
				base[i] = new boolean[134217728];
			}
		}

		if (preserve - (long)valid * 134217728L > 0L) {
			copy(array, (long)valid * 134217728L, base, (long)valid * 134217728L, preserve - (long)valid * 134217728L);
		}

		return base;
	}

	public static boolean[][] ensureCapacity(boolean[][] array, long length, long preserve) {
		return length > length(array) ? forceCapacity(array, length, preserve) : array;
	}

	public static boolean[][] grow(boolean[][] array, long length) {
		long oldLength = length(array);
		return length > oldLength ? grow(array, length, oldLength) : array;
	}

	public static boolean[][] grow(boolean[][] array, long length, long preserve) {
		long oldLength = length(array);
		return length > oldLength ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve) : array;
	}

	public static boolean[][] trim(boolean[][] array, long length) {
		BigArrays.ensureLength(length);
		long oldLength = length(array);
		if (length >= oldLength) {
			return array;
		} else {
			int baseLength = (int)(length + 134217727L >>> 27);
			boolean[][] base = (boolean[][])Arrays.copyOf(array, baseLength);
			int residual = (int)(length & 134217727L);
			if (residual != 0) {
				base[baseLength - 1] = BooleanArrays.trim(base[baseLength - 1], residual);
			}

			return base;
		}
	}

	public static boolean[][] setLength(boolean[][] array, long length) {
		long oldLength = length(array);
		if (length == oldLength) {
			return array;
		} else {
			return length < oldLength ? trim(array, length) : ensureCapacity(array, length);
		}
	}

	public static boolean[][] copy(boolean[][] array, long offset, long length) {
		ensureOffsetLength(array, offset, length);
		boolean[][] a = newBigArray(length);
		copy(array, offset, a, 0L, length);
		return a;
	}

	public static boolean[][] copy(boolean[][] array) {
		boolean[][] base = (boolean[][])array.clone();
		int i = base.length;

		while (i-- != 0) {
			base[i] = (boolean[])array[i].clone();
		}

		return base;
	}

	public static void fill(boolean[][] array, boolean value) {
		int i = array.length;

		while (i-- != 0) {
			Arrays.fill(array[i], value);
		}
	}

	public static void fill(boolean[][] array, long from, long to, boolean value) {
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

	public static boolean equals(boolean[][] a1, boolean[][] a2) {
		if (length(a1) != length(a2)) {
			return false;
		} else {
			int i = a1.length;

			while (i-- != 0) {
				boolean[] t = a1[i];
				boolean[] u = a2[i];
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

	public static String toString(boolean[][] a) {
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

	public static void ensureFromTo(boolean[][] a, long from, long to) {
		BigArrays.ensureFromTo(length(a), from, to);
	}

	public static void ensureOffsetLength(boolean[][] a, long offset, long length) {
		BigArrays.ensureOffsetLength(length(a), offset, length);
	}

	private static void vecSwap(boolean[][] x, long a, long b, long n) {
		int i = 0;

		while ((long)i < n) {
			swap(x, a, b);
			i++;
			a++;
			b++;
		}
	}

	private static long med3(boolean[][] x, long a, long b, long c, BooleanComparator comp) {
		int ab = comp.compare(get(x, a), get(x, b));
		int ac = comp.compare(get(x, a), get(x, c));
		int bc = comp.compare(get(x, b), get(x, c));
		return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
	}

	private static void selectionSort(boolean[][] a, long from, long to, BooleanComparator comp) {
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

	public static void quickSort(boolean[][] x, long from, long to, BooleanComparator comp) {
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

			boolean v = get(x, m);
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

	private static long med3(boolean[][] x, long a, long b, long c) {
		int ab = Boolean.compare(get(x, a), get(x, b));
		int ac = Boolean.compare(get(x, a), get(x, c));
		int bc = Boolean.compare(get(x, b), get(x, c));
		return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
	}

	private static void selectionSort(boolean[][] a, long from, long to) {
		for (long i = from; i < to - 1L; i++) {
			long m = i;

			for (long j = i + 1L; j < to; j++) {
				if (!get(a, j) && get(a, m)) {
					m = j;
				}
			}

			if (m != i) {
				swap(a, i, m);
			}
		}
	}

	public static void quickSort(boolean[][] x, BooleanComparator comp) {
		quickSort(x, 0L, length(x), comp);
	}

	public static void quickSort(boolean[][] x, long from, long to) {
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

			boolean v = get(x, m);
			long a = from;
			long b = from;
			long c = to - 1L;
			long d = c;

			while (true) {
				int comparison;
				while (b > c || (comparison = Boolean.compare(get(x, b), v)) > 0) {
					for (; c >= b && (comparison = Boolean.compare(get(x, c), v)) >= 0; c--) {
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

	public static void quickSort(boolean[][] x) {
		quickSort(x, 0L, length(x));
	}

	public static boolean[][] shuffle(boolean[][] a, long from, long to, Random random) {
		long i = to - from;

		while (i-- != 0L) {
			long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
			boolean t = get(a, from + i);
			set(a, from + i, get(a, from + p));
			set(a, from + p, t);
		}

		return a;
	}

	public static boolean[][] shuffle(boolean[][] a, Random random) {
		long i = length(a);

		while (i-- != 0L) {
			long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
			boolean t = get(a, i);
			set(a, i, get(a, p));
			set(a, p, t);
		}

		return a;
	}

	private static final class BigArrayHashStrategy implements Strategy<boolean[][]>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;

		private BigArrayHashStrategy() {
		}

		public int hashCode(boolean[][] o) {
			return Arrays.deepHashCode(o);
		}

		public boolean equals(boolean[][] a, boolean[][] b) {
			return BooleanBigArrays.equals(a, b);
		}
	}
}
