import java.util.Random;

public final class cwc {
	private final byte[] d;
	public final double a;
	public final double b;
	public final double c;

	public cwc(Random random) {
		this.a = random.nextDouble() * 256.0;
		this.b = random.nextDouble() * 256.0;
		this.c = random.nextDouble() * 256.0;
		this.d = new byte[256];

		for (int integer3 = 0; integer3 < 256; integer3++) {
			this.d[integer3] = (byte)integer3;
		}

		for (int integer3 = 0; integer3 < 256; integer3++) {
			int integer4 = random.nextInt(256 - integer3);
			byte byte5 = this.d[integer3];
			this.d[integer3] = this.d[integer3 + integer4];
			this.d[integer3 + integer4] = byte5;
		}
	}

	public double a(double double1, double double2, double double3, double double4, double double5) {
		double double12 = double1 + this.a;
		double double14 = double2 + this.b;
		double double16 = double3 + this.c;
		int integer18 = aec.c(double12);
		int integer19 = aec.c(double14);
		int integer20 = aec.c(double16);
		double double21 = double12 - (double)integer18;
		double double23 = double14 - (double)integer19;
		double double25 = double16 - (double)integer20;
		double double27 = aec.j(double21);
		double double29 = aec.j(double23);
		double double31 = aec.j(double25);
		double double33;
		if (double4 != 0.0) {
			double double35 = Math.min(double5, double23);
			double33 = (double)aec.c(double35 / double4) * double4;
		} else {
			double33 = 0.0;
		}

		return this.a(integer18, integer19, integer20, double21, double23 - double33, double25, double27, double29, double31);
	}

	private static double a(int integer, double double2, double double3, double double4) {
		int integer8 = integer & 15;
		return cwg.a(cwg.a[integer8], double2, double3, double4);
	}

	private int a(int integer) {
		return this.d[integer & 0xFF] & 0xFF;
	}

	public double a(int integer1, int integer2, int integer3, double double4, double double5, double double6, double double7, double double8, double double9) {
		int integer17 = this.a(integer1) + integer2;
		int integer18 = this.a(integer17) + integer3;
		int integer19 = this.a(integer17 + 1) + integer3;
		int integer20 = this.a(integer1 + 1) + integer2;
		int integer21 = this.a(integer20) + integer3;
		int integer22 = this.a(integer20 + 1) + integer3;
		double double23 = a(this.a(integer18), double4, double5, double6);
		double double25 = a(this.a(integer21), double4 - 1.0, double5, double6);
		double double27 = a(this.a(integer19), double4, double5 - 1.0, double6);
		double double29 = a(this.a(integer22), double4 - 1.0, double5 - 1.0, double6);
		double double31 = a(this.a(integer18 + 1), double4, double5, double6 - 1.0);
		double double33 = a(this.a(integer21 + 1), double4 - 1.0, double5, double6 - 1.0);
		double double35 = a(this.a(integer19 + 1), double4, double5 - 1.0, double6 - 1.0);
		double double37 = a(this.a(integer22 + 1), double4 - 1.0, double5 - 1.0, double6 - 1.0);
		return aec.a(double7, double8, double9, double23, double25, double27, double29, double31, double33, double35, double37);
	}
}
