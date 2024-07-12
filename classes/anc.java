import java.util.Random;

public class anc {
	private static final Random a = new Random();

	public static void a(bqb bqb, fu fu, amz amz) {
		a(bqb, (double)fu.u(), (double)fu.v(), (double)fu.w(), amz);
	}

	public static void a(bqb bqb, aom aom, amz amz) {
		a(bqb, aom.cC(), aom.cD(), aom.cG(), amz);
	}

	private static void a(bqb bqb, double double2, double double3, double double4, amz amz) {
		for (int integer9 = 0; integer9 < amz.ab_(); integer9++) {
			a(bqb, double2, double3, double4, amz.a(integer9));
		}
	}

	public static void a(bqb bqb, fu fu, gi<bki> gi) {
		gi.forEach(bki -> a(bqb, (double)fu.u(), (double)fu.v(), (double)fu.w(), bki));
	}

	public static void a(bqb bqb, double double2, double double3, double double4, bki bki) {
		double double9 = (double)aoq.L.j();
		double double11 = 1.0 - double9;
		double double13 = double9 / 2.0;
		double double15 = Math.floor(double2) + a.nextDouble() * double11 + double13;
		double double17 = Math.floor(double3) + a.nextDouble() * double11;
		double double19 = Math.floor(double4) + a.nextDouble() * double11 + double13;

		while (!bki.a()) {
			bbg bbg21 = new bbg(bqb, double15, double17, double19, bki.a(a.nextInt(21) + 10));
			float float22 = 0.05F;
			bbg21.m(a.nextGaussian() * 0.05F, a.nextGaussian() * 0.05F + 0.2F, a.nextGaussian() * 0.05F);
			bqb.c(bbg21);
		}
	}
}
