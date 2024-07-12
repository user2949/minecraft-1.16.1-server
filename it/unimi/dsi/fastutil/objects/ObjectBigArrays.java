package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.Hash.Strategy;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;

public final class ObjectBigArrays {
	public static final Object[][] EMPTY_BIG_ARRAY = new Object[0][];
	public static final Object[][] DEFAULT_EMPTY_BIG_ARRAY = new Object[0][];
	public static final Strategy HASH_STRATEGY = new ObjectBigArrays.BigArrayHashStrategy();
	private static final int SMALL = 7;
	private static final int MEDIUM = 40;

	private ObjectBigArrays() {
	}

	public static <K> K get(K[][] array, long index) {
		return array[BigArrays.segment(index)][BigArrays.displacement(index)];
	}

	public static <K> void set(K[][] array, long index, K value) {
		array[BigArrays.segment(index)][BigArrays.displacement(index)] = value;
	}

	public static <K> void swap(K[][] array, long first, long second) {
		K t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
		array[BigArrays.segment(first)][BigArrays.displacement(first)] = array[BigArrays.segment(second)][BigArrays.displacement(second)];
		array[BigArrays.segment(second)][BigArrays.displacement(second)] = t;
	}

	public static <K> long length(K[][] array) {
		int length = array.length;
		return length == 0 ? 0L : BigArrays.start(length - 1) + (long)array[length - 1].length;
	}

	public static <K> void copy(K[][] srcArray, long srcPos, K[][] destArray, long destPos, long length) {
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

	public static <K> void copyFromBig(K[][] srcArray, long srcPos, K[] destArray, int destPos, int length) {
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

	public static <K> void copyToBig(K[] srcArray, int srcPos, K[][] destArray, long destPos, long length) {
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

	public static <K> K[][] newBigArray(K[][] prototype, long length) {
		return (K[][])newBigArray(prototype.getClass().getComponentType(), length);
	}

	private static Object[][] newBigArray(Class<?> componentType, long length) {
		if (length == 0L && componentType == Object[].class) {
			return EMPTY_BIG_ARRAY;
		} else {
			BigArrays.ensureLength(length);
			int baseLength = (int)(length + 134217727L >>> 27);
			Object[][] base = (Object[][])Array.newInstance(componentType, baseLength);
			int residual = (int)(length & 134217727L);
			if (residual != 0) {
				for (int i = 0; i < baseLength - 1; i++) {
					base[i] = (Object[])Array.newInstance(componentType.getComponentType(), 134217728);
				}

				base[baseLength - 1] = (Object[])Array.newInstance(componentType.getComponentType(), residual);
			} else {
				for (int i = 0; i < baseLength; i++) {
					base[i] = (Object[])Array.newInstance(componentType.getComponentType(), 134217728);
				}
			}

			return base;
		}
	}

	public static Object[][] newBigArray(long length) {
		if (length == 0L) {
			return EMPTY_BIG_ARRAY;
		} else {
			BigArrays.ensureLength(length);
			int baseLength = (int)(length + 134217727L >>> 27);
			Object[][] base = new Object[baseLength][];
			int residual = (int)(length & 134217727L);
			if (residual != 0) {
				for (int i = 0; i < baseLength - 1; i++) {
					base[i] = new Object[134217728];
				}

				base[baseLength - 1] = new Object[residual];
			} else {
				for (int i = 0; i < baseLength; i++) {
					base[i] = new Object[134217728];
				}
			}

			return base;
		}
	}

	public static <K> K[][] wrap(K[] array) {
		if (array.length == 0 && array.getClass() == Object[].class) {
			return (K[][])EMPTY_BIG_ARRAY;
		} else if (array.length <= 134217728) {
			K[][] bigArray = (K[][])Array.newInstance(array.getClass(), 1);
			bigArray[0] = array;
			return bigArray;
		} else {
			K[][] bigArray = (K[][])newBigArray(array.getClass(), (long)array.length);

			for (int i = 0; i < bigArray.length; i++) {
				System.arraycopy(array, (int)BigArrays.start(i), bigArray[i], 0, bigArray[i].length);
			}

			return bigArray;
		}
	}

	public static <K> K[][] ensureCapacity(K[][] array, long length) {
		return (K[][])ensureCapacity(array, length, length(array));
	}

	public static <K> K[][] forceCapacity(K[][] array, long length, long preserve) {
		BigArrays.ensureLength(length);
		int valid = array.length - (array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728) ? 1 : 0);
		int baseLength = (int)(length + 134217727L >>> 27);
		K[][] base = (K[][])Arrays.copyOf(array, baseLength);
		Class<?> componentType = array.getClass().getComponentType();
		int residual = (int)(length & 134217727L);
		if (residual != 0) {
			for (int i = valid; i < baseLength - 1; i++) {
				base[i] = (K[])Array.newInstance(componentType.getComponentType(), 134217728);
			}

			base[baseLength - 1] = (K[])Array.newInstance(componentType.getComponentType(), residual);
		} else {
			for (int i = valid; i < baseLength; i++) {
				base[i] = (K[])Array.newInstance(componentType.getComponentType(), 134217728);
			}
		}

		if (preserve - (long)valid * 134217728L > 0L) {
			copy(array, (long)valid * 134217728L, base, (long)valid * 134217728L, preserve - (long)valid * 134217728L);
		}

		return base;
	}

	public static <K> K[][] ensureCapacity(K[][] array, long length, long preserve) {
		return (K[][])(length > length(array) ? forceCapacity(array, length, preserve) : array);
	}

	public static <K> K[][] grow(K[][] array, long length) {
		long oldLength = length(array);
		return (K[][])(length > oldLength ? grow(array, length, oldLength) : array);
	}

	public static <K> K[][] grow(K[][] array, long length, long preserve) {
		long oldLength = length(array);
		return (K[][])(length > oldLength ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1), length), preserve) : array);
	}

	public static <K> K[][] trim(K[][] array, long length) {
		BigArrays.ensureLength(length);
		long oldLength = length(array);
		if (length >= oldLength) {
			return array;
		} else {
			int baseLength = (int)(length + 134217727L >>> 27);
			K[][] base = (K[][])Arrays.copyOf(array, baseLength);
			int residual = (int)(length & 134217727L);
			if (residual != 0) {
				base[baseLength - 1] = (K[])ObjectArrays.trim(base[baseLength - 1], residual);
			}

			return base;
		}
	}

	public static <K> K[][] setLength(K[][] array, long length) {
		long oldLength = length(array);
		if (length == oldLength) {
			return array;
		} else {
			return (K[][])(length < oldLength ? trim(array, length) : ensureCapacity(array, length));
		}
	}

	public static <K> K[][] copy(K[][] array, long offset, long length) {
		ensureOffsetLength(array, offset, length);
		K[][] a = (K[][])newBigArray(array, length);
		copy(array, offset, a, 0L, length);
		return a;
	}

	public static <K> K[][] copy(K[][] array) {
		K[][] base = (K[][])array.clone();
		int i = base.length;

		while (i-- != 0) {
			base[i] = (K[])array[i].clone();
		}

		return base;
	}

	public static <K> void fill(K[][] array, K value) {
		int i = array.length;

		while (i-- != 0) {
			Arrays.fill(array[i], value);
		}
	}

	public static <K> void fill(K[][] array, long from, long to, K value) {
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

	public static <K> boolean equals(K[][] a1, K[][] a2) {
		if (length(a1) != length(a2)) {
			return false;
		} else {
			int i = a1.length;

			while (i-- != 0) {
				K[] t = a1[i];
				K[] u = a2[i];
				int j = t.length;

				while (j-- != 0) {
					if (!Objects.equals(t[j], u[j])) {
						return false;
					}
				}
			}

			return true;
		}
	}

	public static <K> String toString(K[][] a) {
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

	public static <K> void ensureFromTo(K[][] a, long from, long to) {
		BigArrays.ensureFromTo(length(a), from, to);
	}

	public static <K> void ensureOffsetLength(K[][] a, long offset, long length) {
		BigArrays.ensureOffsetLength(length(a), offset, length);
	}

	private static <K> void vecSwap(K[][] x, long a, long b, long n) {
		int i = 0;

		while ((long)i < n) {
			swap(x, a, b);
			i++;
			a++;
			b++;
		}
	}

	private static <K> long med3(K[][] x, long a, long b, long c, Comparator<K> comp) {
		int ab = comp.compare(get(x, a), get(x, b));
		int ac = comp.compare(get(x, a), get(x, c));
		int bc = comp.compare(get(x, b), get(x, c));
		return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
	}

	private static <K> void selectionSort(K[][] a, long from, long to, Comparator<K> comp) {
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

	public static <K> void quickSort(K[][] x, long from, long to, Comparator<K> comp) {
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

			K v = get(x, m);
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

	private static <K> long med3(K[][] x, long a, long b, long c) {
		int ab = get(x, a).compareTo(get(x, b));
		int ac = get(x, a).compareTo(get(x, c));
		int bc = get(x, b).compareTo(get(x, c));
		return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
	}

	private static <K> void selectionSort(K[][] a, long from, long to) {
		for (long i = from; i < to - 1L; i++) {
			long m = i;

			for (long j = i + 1L; j < to; j++) {
				if (get(a, j).compareTo(get(a, m)) < 0) {
					m = j;
				}
			}

			if (m != i) {
				swap(a, i, m);
			}
		}
	}

	public static <K> void quickSort(K[][] x, Comparator<K> comp) {
		quickSort(x, 0L, length(x), comp);
	}

	public static <K> void quickSort(K[][] x, long from, long to) {
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

			K v = get(x, m);
			long a = from;
			long b = from;
			long c = to - 1L;
			long d = c;

			while (true) {
				int comparison;
				while (b > c || (comparison = get(x, b).compareTo(v)) > 0) {
					for (; c >= b && (comparison = get(x, c).compareTo(v)) >= 0; c--) {
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

	public static <K> void quickSort(K[][] x) {
		quickSort(x, 0L, length(x));
	}

	public static <K> long binarySearch(K[][] a, long from, long to, K key) {
		to--;

		while (from <= to) {
			long mid = from + to >>> 1;
			K midVal = get(a, mid);
			int cmp = ((Comparable)midVal).compareTo(key);
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

	public static <K> long binarySearch(K[][] a, Object key) {
		return binarySearch(a, 0L, length(a), key);
	}

	public static <K> long binarySearch(K[][] a, long from, long to, K key, Comparator<K> c) {
		to--;

		while (from <= to) {
			long mid = from + to >>> 1;
			K midVal = get(a, mid);
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

	public static <K> long binarySearch(K[][] a, K key, Comparator<K> c) {
		return binarySearch(a, 0L, length(a), key, c);
	}

	public static <K> K[][] shuffle(K[][] a, long from, long to, Random random) {
		long i = to - from;

		while (i-- != 0L) {
			long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
			K t = get(a, from + i);
			set(a, from + i, get(a, from + p));
			set(a, from + p, t);
		}

		return a;
	}

	public static <K> K[][] shuffle(K[][] a, Random random) {
		long i = length(a);

		while (i-- != 0L) {
			long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
			K t = get(a, i);
			set(a, i, get(a, p));
			set(a, p, t);
		}

		return a;
	}

	private static final class BigArrayHashStrategy<K> implements Strategy<K[][]>, Serializable {
		private static final long serialVersionUID = -7046029254386353129L;

		private BigArrayHashStrategy() {
		}

		public int hashCode(K[][] o) {
			return Arrays.deepHashCode(o);
		}

		public boolean equals(K[][] a, K[][] b) {
			return ObjectBigArrays.equals(a, b);
		}
	}
}
