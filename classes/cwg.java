import java.util.Random;

public class cwg {
	protected static final int[][] a = new int[][]{
		{1, 1, 0},
		{-1, 1, 0},
		{1, -1, 0},
		{-1, -1, 0},
		{1, 0, 1},
		{-1, 0, 1},
		{1, 0, -1},
		{-1, 0, -1},
		{0, 1, 1},
		{0, -1, 1},
		{0, 1, -1},
		{0, -1, -1},
		{1, 1, 0},
		{0, -1, 1},
		{-1, 1, 0},
		{0, -1, -1}
	};
	private static final double e = Math.sqrt(3.0);
	private static final double f = 0.5 * (e - 1.0);
	private static final double g = (3.0 - e) / 6.0;
	private final int[] h = new int[512];
	public final double b;
	public final double c;
	public final double d;

	public cwg(Random random) {
		this.b = random.nextDouble() * 256.0;
		this.c = random.nextDouble() * 256.0;
		this.d = random.nextDouble() * 256.0;
		int integer3 = 0;

		while (integer3 < 256) {
			this.h[integer3] = integer3++;
		}

		for (int integer3x = 0; integer3x < 256; integer3x++) {
			int integer4 = random.nextInt(256 - integer3x);
			int integer5 = this.h[integer3x];
			this.h[integer3x] = this.h[integer4 + integer3x];
			this.h[integer4 + integer3x] = integer5;
		}
	}

	private int a(int integer) {
		return this.h[integer & 0xFF];
	}

	protected static double a(int[] arr, double double2, double double3, double double4) {
		return (double)arr[0] * double2 + (double)arr[1] * double3 + (double)arr[2] * double4;
	}

	private double a(int integer, double double2, double double3, double double4, double double5) {
		double double13 = double5 - double2 * double2 - double3 * double3 - double4 * double4;
		double double11;
		if (double13 < 0.0) {
			double11 = 0.0;
		} else {
			double13 *= double13;
			double11 = double13 * double13 * a(a[integer], double2, double3, double4);
		}

		return double11;
	}

	public double a(double double1, double double2) {
		double double6 = (double1 + double2) * f;
		int integer8 = aec.c(double1 + double6);
		int integer9 = aec.c(double2 + double6);
		double double10 = (double)(integer8 + integer9) * g;
		double double12 = (double)integer8 - double10;
		double double14 = (double)integer9 - double10;
		double double16 = double1 - double12;
		double double18 = double2 - double14;
		int integer20;
		int integer21;
		if (double16 > double18) {
			integer20 = 1;
			integer21 = 0;
		} else {
			integer20 = 0;
			integer21 = 1;
		}

		double double22 = double16 - (double)integer20 + g;
		double double24 = double18 - (double)integer21 + g;
		double double26 = double16 - 1.0 + 2.0 * g;
		double double28 = double18 - 1.0 + 2.0 * g;
		int integer30 = integer8 & 0xFF;
		int integer31 = integer9 & 0xFF;
		int integer32 = this.a(integer30 + this.a(integer31)) % 12;
		int integer33 = this.a(integer30 + integer20 + this.a(integer31 + integer21)) % 12;
		int integer34 = this.a(integer30 + 1 + this.a(integer31 + 1)) % 12;
		double double35 = this.a(integer32, double16, double18, 0.0, 0.5);
		double double37 = this.a(integer33, double22, double24, 0.0, 0.5);
		double double39 = this.a(integer34, double26, double28, 0.0, 0.5);
		return 70.0 * (double35 + double37 + double39);
	}

	public double a(double double1, double double2, double double3) {
		double double8 = 0.3333333333333333;
		double double10 = (double1 + double2 + double3) * 0.3333333333333333;
		int integer12 = aec.c(double1 + double10);
		int integer13 = aec.c(double2 + double10);
		int integer14 = aec.c(double3 + double10);
		double double15 = 0.16666666666666666;
		double double17 = (double)(integer12 + integer13 + integer14) * 0.16666666666666666;
		double double19 = (double)integer12 - double17;
		double double21 = (double)integer13 - double17;
		double double23 = (double)integer14 - double17;
		double double25 = double1 - double19;
		double double27 = double2 - double21;
		double double29 = double3 - double23;
		int integer31;
		int integer32;
		int integer33;
		int integer34;
		int integer35;
		int integer36;
		if (double25 >= double27) {
			if (double27 >= double29) {
				integer31 = 1;
				integer32 = 0;
				integer33 = 0;
				integer34 = 1;
				integer35 = 1;
				integer36 = 0;
			} else if (double25 >= double29) {
				integer31 = 1;
				integer32 = 0;
				integer33 = 0;
				integer34 = 1;
				integer35 = 0;
				integer36 = 1;
			} else {
				integer31 = 0;
				integer32 = 0;
				integer33 = 1;
				integer34 = 1;
				integer35 = 0;
				integer36 = 1;
			}
		} else if (double27 < double29) {
			integer31 = 0;
			integer32 = 0;
			integer33 = 1;
			integer34 = 0;
			integer35 = 1;
			integer36 = 1;
		} else if (double25 < double29) {
			integer31 = 0;
			integer32 = 1;
			integer33 = 0;
			integer34 = 0;
			integer35 = 1;
			integer36 = 1;
		} else {
			integer31 = 0;
			integer32 = 1;
			integer33 = 0;
			integer34 = 1;
			integer35 = 1;
			integer36 = 0;
		}

		double double37 = double25 - (double)integer31 + 0.16666666666666666;
		double double39 = double27 - (double)integer32 + 0.16666666666666666;
		double double41 = double29 - (double)integer33 + 0.16666666666666666;
		double double43 = double25 - (double)integer34 + 0.3333333333333333;
		double double45 = double27 - (double)integer35 + 0.3333333333333333;
		double double47 = double29 - (double)integer36 + 0.3333333333333333;
		double double49 = double25 - 1.0 + 0.5;
		double double51 = double27 - 1.0 + 0.5;
		double double53 = double29 - 1.0 + 0.5;
		int integer55 = integer12 & 0xFF;
		int integer56 = integer13 & 0xFF;
		int integer57 = integer14 & 0xFF;
		int integer58 = this.a(integer55 + this.a(integer56 + this.a(integer57))) % 12;
		int integer59 = this.a(integer55 + integer31 + this.a(integer56 + integer32 + this.a(integer57 + integer33))) % 12;
		int integer60 = this.a(integer55 + integer34 + this.a(integer56 + integer35 + this.a(integer57 + integer36))) % 12;
		int integer61 = this.a(integer55 + 1 + this.a(integer56 + 1 + this.a(integer57 + 1))) % 12;
		double double62 = this.a(integer58, double25, double27, double29, 0.6);
		double double64 = this.a(integer59, double37, double39, double41, 0.6);
		double double66 = this.a(integer60, double43, double45, double47, 0.6);
		double double68 = this.a(integer61, double49, double51, double53, 0.6);
		return 32.0 * (double62 + double64 + double66 + double68);
	}
}
