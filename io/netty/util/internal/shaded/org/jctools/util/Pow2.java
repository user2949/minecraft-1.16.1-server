package io.netty.util.internal.shaded.org.jctools.util;

public final class Pow2 {
	public static final int MAX_POW2 = 1073741824;

	public static int roundToPowerOfTwo(int value) {
		if (value > 1073741824) {
			throw new IllegalArgumentException("There is no larger power of 2 int for value:" + value + " since it exceeds 2^31.");
		} else if (value < 0) {
			throw new IllegalArgumentException("Given value:" + value + ". Expecting value >= 0.");
		} else {
			return 1 << 32 - Integer.numberOfLeadingZeros(value - 1);
		}
	}

	public static boolean isPowerOfTwo(int value) {
		return (value & value - 1) == 0;
	}

	public static long align(long value, int alignment) {
		if (!isPowerOfTwo(alignment)) {
			throw new IllegalArgumentException("alignment must be a power of 2:" + alignment);
		} else {
			return value + (long)(alignment - 1) & (long)(~(alignment - 1));
		}
	}
}
