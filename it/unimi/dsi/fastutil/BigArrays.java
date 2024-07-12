package it.unimi.dsi.fastutil;

import it.unimi.dsi.fastutil.ints.IntBigArrays;
import it.unimi.dsi.fastutil.longs.LongComparator;

public class BigArrays {
	public static final int SEGMENT_SHIFT = 27;
	public static final int SEGMENT_SIZE = 134217728;
	public static final int SEGMENT_MASK = 134217727;
	private static final int SMALL = 7;
	private static final int MEDIUM = 40;

	protected BigArrays() {
	}

	public static int segment(long index) {
		return (int)(index >>> 27);
	}

	public static int displacement(long index) {
		return (int)(index & 134217727L);
	}

	public static long start(int segment) {
		return (long)segment << 27;
	}

	public static long index(int segment, int displacement) {
		return start(segment) + (long)displacement;
	}

	public static void ensureFromTo(long bigArrayLength, long from, long to) {
		if (from < 0L) {
			throw new ArrayIndexOutOfBoundsException("Start index (" + from + ") is negative");
		} else if (from > to) {
			throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
		} else if (to > bigArrayLength) {
			throw new ArrayIndexOutOfBoundsException("End index (" + to + ") is greater than big-array length (" + bigArrayLength + ")");
		}
	}

	public static void ensureOffsetLength(long bigArrayLength, long offset, long length) {
		if (offset < 0L) {
			throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
		} else if (length < 0L) {
			throw new IllegalArgumentException("Length (" + length + ") is negative");
		} else if (offset + length > bigArrayLength) {
			throw new ArrayIndexOutOfBoundsException("Last index (" + (offset + length) + ") is greater than big-array length (" + bigArrayLength + ")");
		}
	}

	public static void ensureLength(long bigArrayLength) {
		if (bigArrayLength < 0L) {
			throw new IllegalArgumentException("Negative big-array size: " + bigArrayLength);
		} else if (bigArrayLength >= 288230376017494016L) {
			throw new IllegalArgumentException("Big-array size too big: " + bigArrayLength);
		}
	}

	private static void inPlaceMerge(long from, long mid, long to, LongComparator comp, BigSwapper swapper) {
		if (from < mid && mid < to) {
			if (to - from == 2L) {
				if (comp.compare(mid, from) < 0) {
					swapper.swap(from, mid);
				}
			} else {
				long firstCut;
				long secondCut;
				if (mid - from > to - mid) {
					firstCut = from + (mid - from) / 2L;
					secondCut = lowerBound(mid, to, firstCut, comp);
				} else {
					secondCut = mid + (to - mid) / 2L;
					firstCut = upperBound(from, mid, secondCut, comp);
				}

				if (mid != firstCut && mid != secondCut) {
					long first1 = firstCut;
					long last1 = mid;

					while (first1 < --last1) {
						swapper.swap(first1++, last1);
					}

					first1 = mid;
					last1 = secondCut;

					while (first1 < --last1) {
						swapper.swap(first1++, last1);
					}

					first1 = firstCut;
					last1 = secondCut;

					while (first1 < --last1) {
						swapper.swap(first1++, last1);
					}
				}

				mid = firstCut + (secondCut - mid);
				inPlaceMerge(from, firstCut, mid, comp, swapper);
				inPlaceMerge(mid, secondCut, to, comp, swapper);
			}
		}
	}

	private static long lowerBound(long mid, long to, long firstCut, LongComparator comp) {
		long len = to - mid;

		while (len > 0L) {
			long half = len / 2L;
			long middle = mid + half;
			if (comp.compare(middle, firstCut) < 0) {
				mid = middle + 1L;
				len -= half + 1L;
			} else {
				len = half;
			}
		}

		return mid;
	}

	private static long med3(long a, long b, long c, LongComparator comp) {
		int ab = comp.compare(a, b);
		int ac = comp.compare(a, c);
		int bc = comp.compare(b, c);
		return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
	}

	public static void mergeSort(long from, long to, LongComparator comp, BigSwapper swapper) {
		long length = to - from;
		if (length >= 7L) {
			long mid = from + to >>> 1;
			mergeSort(from, mid, comp, swapper);
			mergeSort(mid, to, comp, swapper);
			if (comp.compare(mid - 1L, mid) > 0) {
				inPlaceMerge(from, mid, to, comp, swapper);
			}
		} else {
			for (long i = from; i < to; i++) {
				for (long j = i; j > from && comp.compare(j - 1L, j) > 0; j--) {
					swapper.swap(j, j - 1L);
				}
			}
		}
	}

	public static void quickSort(long from, long to, LongComparator comp, BigSwapper swapper) {
		long len = to - from;
		if (len < 7L) {
			for (long i = from; i < to; i++) {
				for (long j = i; j > from && comp.compare(j - 1L, j) > 0; j--) {
					swapper.swap(j, j - 1L);
				}
			}
		} else {
			long m = from + len / 2L;
			if (len > 7L) {
				long l = from;
				long n = to - 1L;
				if (len > 40L) {
					long s = len / 8L;
					l = med3(from, from + s, from + 2L * s, comp);
					m = med3(m - s, m, m + s, comp);
					n = med3(n - 2L * s, n - s, n, comp);
				}

				m = med3(l, m, n, comp);
			}

			long a = from;
			long b = from;
			long c = to - 1L;
			long d = c;

			while (true) {
				int comparison;
				for (; b > c || (comparison = comp.compare(b, m)) > 0; swapper.swap(b++, c--)) {
					for (; c >= b && (comparison = comp.compare(c, m)) >= 0; c--) {
						if (comparison == 0) {
							if (c == m) {
								m = d;
							} else if (d == m) {
								m = c;
							}

							swapper.swap(c, d--);
						}
					}

					if (b > c) {
						long n = from + len;
						long s = Math.min(a - from, b - a);
						vecSwap(swapper, from, b - s, s);
						long var29 = Math.min(d - c, n - d - 1L);
						vecSwap(swapper, b, n - var29, var29);
						long var30;
						if ((var30 = b - a) > 1L) {
							quickSort(from, from + var30, comp, swapper);
						}

						long var31;
						if ((var31 = d - c) > 1L) {
							quickSort(n - var31, n, comp, swapper);
						}

						return;
					}

					if (b == m) {
						m = d;
					} else if (c == m) {
						m = c;
					}
				}

				if (comparison == 0) {
					if (a == m) {
						m = b;
					} else if (b == m) {
						m = a;
					}

					swapper.swap(a++, b);
				}

				b++;
			}
		}
	}

	private static long upperBound(long from, long mid, long secondCut, LongComparator comp) {
		long len = mid - from;

		while (len > 0L) {
			long half = len / 2L;
			long middle = from + half;
			if (comp.compare(secondCut, middle) < 0) {
				len = half;
			} else {
				from = middle + 1L;
				len -= half + 1L;
			}
		}

		return from;
	}

	private static void vecSwap(BigSwapper swapper, long from, long l, long s) {
		int i = 0;

		while ((long)i < s) {
			swapper.swap(from, l);
			i++;
			from++;
			l++;
		}
	}

	public static void main(String[] arg) {
		int[][] a = IntBigArrays.newBigArray(1L << Integer.parseInt(arg[0]));
		int k = 10;

		while (k-- != 0) {
			long start = -System.currentTimeMillis();
			long x = 0L;
			long i = IntBigArrays.length(a);

			while (i-- != 0L) {
				x ^= i ^ (long)IntBigArrays.get(a, i);
			}

			if (x == 0L) {
				System.err.println();
			}

			System.out.println("Single loop: " + (start + System.currentTimeMillis()) + "ms");
			start = -System.currentTimeMillis();
			long y = 0L;
			int ix = a.length;

			while (ix-- != 0) {
				int[] t = a[ix];
				int d = t.length;

				while (d-- != 0) {
					y ^= (long)t[d] ^ index(ix, d);
				}
			}

			if (y == 0L) {
				System.err.println();
			}

			if (x != y) {
				throw new AssertionError();
			}

			System.out.println("Double loop: " + (start + System.currentTimeMillis()) + "ms");
			long z = 0L;
			i = IntBigArrays.length(a);
			int ixx = a.length;

			while (ixx-- != 0) {
				int[] t = a[ixx];
				int d = t.length;

				while (d-- != 0) {
					y ^= (long)t[d] ^ --i;
				}
			}

			if (z == 0L) {
				System.err.println();
			}

			if (x != z) {
				throw new AssertionError();
			}

			System.out.println("Double loop (with additional index): " + (start + System.currentTimeMillis()) + "ms");
		}
	}
}
