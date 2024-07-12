import java.util.Random;
import java.util.UUID;
import java.util.function.IntPredicate;

public class aec {
	public static final float a = c(2.0F);
	private static final float[] b = v.a(new float[65536], arr -> {
		for (int integer2 = 0; integer2 < arr.length; integer2++) {
			arr[integer2] = (float)Math.sin((double)integer2 * Math.PI * 2.0 / 65536.0);
		}
	});
	private static final Random c = new Random();
	private static final int[] d = new int[]{0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};
	private static final double e = Double.longBitsToDouble(4805340802404319232L);
	private static final double[] f = new double[257];
	private static final double[] g = new double[257];

	public static float a(float float1) {
		return b[(int)(float1 * 10430.378F) & 65535];
	}

	public static float b(float float1) {
		return b[(int)(float1 * 10430.378F + 16384.0F) & 65535];
	}

	public static float c(float float1) {
		return (float)Math.sqrt((double)float1);
	}

	public static float a(double double1) {
		return (float)Math.sqrt(double1);
	}

	public static int d(float float1) {
		int integer2 = (int)float1;
		return float1 < (float)integer2 ? integer2 - 1 : integer2;
	}

	public static int c(double double1) {
		int integer3 = (int)double1;
		return double1 < (double)integer3 ? integer3 - 1 : integer3;
	}

	public static long d(double double1) {
		long long3 = (long)double1;
		return double1 < (double)long3 ? long3 - 1L : long3;
	}

	public static float e(float float1) {
		return Math.abs(float1);
	}

	public static int a(int integer) {
		return Math.abs(integer);
	}

	public static int f(float float1) {
		int integer2 = (int)float1;
		return float1 > (float)integer2 ? integer2 + 1 : integer2;
	}

	public static int f(double double1) {
		int integer3 = (int)double1;
		return double1 > (double)integer3 ? integer3 + 1 : integer3;
	}

	public static int a(int integer1, int integer2, int integer3) {
		if (integer1 < integer2) {
			return integer2;
		} else {
			return integer1 > integer3 ? integer3 : integer1;
		}
	}

	public static float a(float float1, float float2, float float3) {
		if (float1 < float2) {
			return float2;
		} else {
			return float1 > float3 ? float3 : float1;
		}
	}

	public static double a(double double1, double double2, double double3) {
		if (double1 < double2) {
			return double2;
		} else {
			return double1 > double3 ? double3 : double1;
		}
	}

	public static double b(double double1, double double2, double double3) {
		if (double3 < 0.0) {
			return double1;
		} else {
			return double3 > 1.0 ? double2 : d(double3, double1, double2);
		}
	}

	public static double a(double double1, double double2) {
		if (double1 < 0.0) {
			double1 = -double1;
		}

		if (double2 < 0.0) {
			double2 = -double2;
		}

		return double1 > double2 ? double1 : double2;
	}

	public static int a(int integer1, int integer2) {
		return Math.floorDiv(integer1, integer2);
	}

	public static int a(Random random, int integer2, int integer3) {
		return integer2 >= integer3 ? integer2 : random.nextInt(integer3 - integer2 + 1) + integer2;
	}

	public static float a(Random random, float float2, float float3) {
		return float2 >= float3 ? float2 : random.nextFloat() * (float3 - float2) + float2;
	}

	public static double a(Random random, double double2, double double3) {
		return double2 >= double3 ? double2 : random.nextDouble() * (double3 - double2) + double2;
	}

	public static double a(long[] arr) {
		long long2 = 0L;

		for (long long7 : arr) {
			long2 += long7;
		}

		return (double)long2 / (double)arr.length;
	}

	public static boolean b(double double1, double double2) {
		return Math.abs(double2 - double1) < 1.0E-5F;
	}

	public static int b(int integer1, int integer2) {
		return Math.floorMod(integer1, integer2);
	}

	public static float g(float float1) {
		float float2 = float1 % 360.0F;
		if (float2 >= 180.0F) {
			float2 -= 360.0F;
		}

		if (float2 < -180.0F) {
			float2 += 360.0F;
		}

		return float2;
	}

	public static double g(double double1) {
		double double3 = double1 % 360.0;
		if (double3 >= 180.0) {
			double3 -= 360.0;
		}

		if (double3 < -180.0) {
			double3 += 360.0;
		}

		return double3;
	}

	public static float c(float float1, float float2) {
		return g(float2 - float1);
	}

	public static float d(float float1, float float2) {
		return e(c(float1, float2));
	}

	public static float b(float float1, float float2, float float3) {
		float float4 = c(float1, float2);
		float float5 = a(float4, -float3, float3);
		return float2 - float5;
	}

	public static float c(float float1, float float2, float float3) {
		float3 = e(float3);
		return float1 < float2 ? a(float1 + float3, float1, float2) : a(float1 - float3, float2, float1);
	}

	public static float d(float float1, float float2, float float3) {
		float float4 = c(float1, float2);
		return c(float1, float1 + float4, float3);
	}

	public static int c(int integer) {
		int integer2 = integer - 1;
		integer2 |= integer2 >> 1;
		integer2 |= integer2 >> 2;
		integer2 |= integer2 >> 4;
		integer2 |= integer2 >> 8;
		integer2 |= integer2 >> 16;
		return integer2 + 1;
	}

	public static boolean d(int integer) {
		return integer != 0 && (integer & integer - 1) == 0;
	}

	public static int e(int integer) {
		integer = d(integer) ? integer : c(integer);
		return d[(int)((long)integer * 125613361L >> 27) & 31];
	}

	public static int f(int integer) {
		return e(integer) - (d(integer) ? 0 : 1);
	}

	public static int c(int integer1, int integer2) {
		if (integer2 == 0) {
			return 0;
		} else if (integer1 == 0) {
			return integer2;
		} else {
			if (integer1 < 0) {
				integer2 *= -1;
			}

			int integer3 = integer1 % integer2;
			return integer3 == 0 ? integer1 : integer1 + integer2 - integer3;
		}
	}

	public static float h(float float1) {
		return float1 - (float)d(float1);
	}

	public static double h(double double1) {
		return double1 - (double)d(double1);
	}

	public static long a(gr gr) {
		return c(gr.u(), gr.v(), gr.w());
	}

	public static long c(int integer1, int integer2, int integer3) {
		long long4 = (long)(integer1 * 3129871) ^ (long)integer3 * 116129781L ^ (long)integer2;
		long4 = long4 * long4 * 42317861L + long4 * 11L;
		return long4 >> 16;
	}

	public static UUID a(Random random) {
		long long2 = random.nextLong() & -61441L | 16384L;
		long long4 = random.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
		return new UUID(long2, long4);
	}

	public static UUID a() {
		return a(c);
	}

	public static double c(double double1, double double2, double double3) {
		return (double1 - double2) / (double3 - double2);
	}

	public static double d(double double1, double double2) {
		double double5 = double2 * double2 + double1 * double1;
		if (Double.isNaN(double5)) {
			return Double.NaN;
		} else {
			boolean boolean7 = double1 < 0.0;
			if (boolean7) {
				double1 = -double1;
			}

			boolean boolean8 = double2 < 0.0;
			if (boolean8) {
				double2 = -double2;
			}

			boolean boolean9 = double1 > double2;
			if (boolean9) {
				double double10 = double2;
				double2 = double1;
				double1 = double10;
			}

			double double10 = i(double5);
			double2 *= double10;
			double1 *= double10;
			double double12 = e + double1;
			int integer14 = (int)Double.doubleToRawLongBits(double12);
			double double15 = f[integer14];
			double double17 = g[integer14];
			double double19 = double12 - e;
			double double21 = double1 * double17 - double2 * double19;
			double double23 = (6.0 + double21 * double21) * double21 * 0.16666666666666666;
			double double25 = double15 + double23;
			if (boolean9) {
				double25 = (Math.PI / 2) - double25;
			}

			if (boolean8) {
				double25 = Math.PI - double25;
			}

			if (boolean7) {
				double25 = -double25;
			}

			return double25;
		}
	}

	public static double i(double double1) {
		double double3 = 0.5 * double1;
		long long5 = Double.doubleToRawLongBits(double1);
		long5 = 6910469410427058090L - (long5 >> 1);
		double1 = Double.longBitsToDouble(long5);
		return double1 * (1.5 - double3 * double1 * double1);
	}

	public static int f(float float1, float float2, float float3) {
		int integer4 = (int)(float1 * 6.0F) % 6;
		float float5 = float1 * 6.0F - (float)integer4;
		float float6 = float3 * (1.0F - float2);
		float float7 = float3 * (1.0F - float5 * float2);
		float float8 = float3 * (1.0F - (1.0F - float5) * float2);
		float float9;
		float float10;
		float float11;
		switch (integer4) {
			case 0:
				float9 = float3;
				float10 = float8;
				float11 = float6;
				break;
			case 1:
				float9 = float7;
				float10 = float3;
				float11 = float6;
				break;
			case 2:
				float9 = float6;
				float10 = float3;
				float11 = float8;
				break;
			case 3:
				float9 = float6;
				float10 = float7;
				float11 = float3;
				break;
			case 4:
				float9 = float8;
				float10 = float6;
				float11 = float3;
				break;
			case 5:
				float9 = float3;
				float10 = float6;
				float11 = float7;
				break;
			default:
				throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + float1 + ", " + float2 + ", " + float3);
		}

		int integer12 = a((int)(float9 * 255.0F), 0, 255);
		int integer13 = a((int)(float10 * 255.0F), 0, 255);
		int integer14 = a((int)(float11 * 255.0F), 0, 255);
		return integer12 << 16 | integer13 << 8 | integer14;
	}

	public static int g(int integer) {
		integer ^= integer >>> 16;
		integer *= -2048144789;
		integer ^= integer >>> 13;
		integer *= -1028477387;
		return integer ^ integer >>> 16;
	}

	public static int a(int integer1, int integer2, IntPredicate intPredicate) {
		int integer4 = integer2 - integer1;

		while (integer4 > 0) {
			int integer5 = integer4 / 2;
			int integer6 = integer1 + integer5;
			if (intPredicate.test(integer6)) {
				integer4 = integer5;
			} else {
				integer1 = integer6 + 1;
				integer4 -= integer5 + 1;
			}
		}

		return integer1;
	}

	public static float g(float float1, float float2, float float3) {
		return float2 + float1 * (float3 - float2);
	}

	public static double d(double double1, double double2, double double3) {
		return double2 + double1 * (double3 - double2);
	}

	public static double a(double double1, double double2, double double3, double double4, double double5, double double6) {
		return d(double2, d(double1, double3, double4), d(double1, double5, double6));
	}

	public static double a(
		double double1,
		double double2,
		double double3,
		double double4,
		double double5,
		double double6,
		double double7,
		double double8,
		double double9,
		double double10,
		double double11
	) {
		return d(double3, a(double1, double2, double4, double5, double6, double7), a(double1, double2, double8, double9, double10, double11));
	}

	public static double j(double double1) {
		return double1 * double1 * double1 * (double1 * (double1 * 6.0 - 15.0) + 10.0);
	}

	public static int k(double double1) {
		if (double1 == 0.0) {
			return 0;
		} else {
			return double1 > 0.0 ? 1 : -1;
		}
	}

	@Deprecated
	public static float j(float float1, float float2, float float3) {
		float float4 = float2 - float1;

		while (float4 < -180.0F) {
			float4 += 360.0F;
		}

		while (float4 >= 180.0F) {
			float4 -= 360.0F;
		}

		return float1 + float3 * float4;
	}

	public static float k(float float1) {
		return float1 * float1;
	}

	static {
		for (int integer1 = 0; integer1 < 257; integer1++) {
			double double2 = (double)integer1 / 256.0;
			double double4 = Math.asin(double2);
			g[integer1] = Math.cos(double4);
			f[integer1] = double4;
		}
	}
}
