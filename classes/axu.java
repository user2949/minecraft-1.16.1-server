import java.util.Random;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;

public class axu {
	@Nullable
	public static dem a(apg apg, int integer2, int integer3) {
		return a(apg, integer2, integer3, 0, null, true, (float) (Math.PI / 2), apg::f, false, 0, 0, true);
	}

	@Nullable
	public static dem a(apg apg, int integer2, int integer3, int integer4, @Nullable dem dem, double double6) {
		return a(apg, integer2, integer3, integer4, dem, true, double6, apg::f, true, 0, 0, false);
	}

	@Nullable
	public static dem b(apg apg, int integer2, int integer3) {
		return a(apg, integer2, integer3, apg::f);
	}

	@Nullable
	public static dem a(apg apg, int integer2, int integer3, ToDoubleFunction<fu> toDoubleFunction) {
		return a(apg, integer2, integer3, 0, null, false, 0.0, toDoubleFunction, true, 0, 0, true);
	}

	@Nullable
	public static dem a(apg apg, int integer2, int integer3, dem dem, float float5, int integer6, int integer7) {
		return a(apg, integer2, integer3, 0, dem, false, (double)float5, apg::f, true, integer6, integer7, true);
	}

	@Nullable
	public static dem a(apg apg, int integer2, int integer3, dem dem) {
		dem dem5 = dem.a(apg.cC(), apg.cD(), apg.cG());
		return a(apg, integer2, integer3, 0, dem5, false, (float) (Math.PI / 2), apg::f, true, 0, 0, true);
	}

	@Nullable
	public static dem b(apg apg, int integer2, int integer3, dem dem) {
		dem dem5 = dem.a(apg.cC(), apg.cD(), apg.cG());
		return a(apg, integer2, integer3, 0, dem5, true, (float) (Math.PI / 2), apg::f, false, 0, 0, true);
	}

	@Nullable
	public static dem a(apg apg, int integer2, int integer3, dem dem, double double5) {
		dem dem7 = dem.a(apg.cC(), apg.cD(), apg.cG());
		return a(apg, integer2, integer3, 0, dem7, true, double5, apg::f, false, 0, 0, true);
	}

	@Nullable
	public static dem b(apg apg, int integer2, int integer3, int integer4, dem dem, double double6) {
		dem dem8 = dem.a(apg.cC(), apg.cD(), apg.cG());
		return a(apg, integer2, integer3, integer4, dem8, false, double6, apg::f, true, 0, 0, false);
	}

	@Nullable
	public static dem c(apg apg, int integer2, int integer3, dem dem) {
		dem dem5 = apg.cz().d(dem);
		return a(apg, integer2, integer3, 0, dem5, true, (float) (Math.PI / 2), apg::f, false, 0, 0, true);
	}

	@Nullable
	public static dem d(apg apg, int integer2, int integer3, dem dem) {
		dem dem5 = apg.cz().d(dem);
		return a(apg, integer2, integer3, 0, dem5, false, (float) (Math.PI / 2), apg::f, true, 0, 0, true);
	}

	@Nullable
	private static dem a(
		apg apg,
		int integer2,
		int integer3,
		int integer4,
		@Nullable dem dem,
		boolean boolean6,
		double double7,
		ToDoubleFunction<fu> toDoubleFunction,
		boolean boolean9,
		int integer10,
		int integer11,
		boolean boolean12
	) {
		awv awv14 = apg.x();
		Random random15 = apg.cX();
		boolean boolean16;
		if (apg.eA()) {
			boolean16 = apg.ex().a(apg.cz(), (double)(apg.ey() + (float)integer2) + 1.0);
		} else {
			boolean16 = false;
		}

		boolean boolean17 = false;
		double double18 = Double.NEGATIVE_INFINITY;
		fu fu20 = apg.cA();

		for (int integer21 = 0; integer21 < 10; integer21++) {
			fu fu22 = a(random15, integer2, integer3, integer4, dem, double7);
			if (fu22 != null) {
				int integer23 = fu22.u();
				int integer24 = fu22.v();
				int integer25 = fu22.w();
				if (apg.eA() && integer2 > 1) {
					fu fu26 = apg.ex();
					if (apg.cC() > (double)fu26.u()) {
						integer23 -= random15.nextInt(integer2 / 2);
					} else {
						integer23 += random15.nextInt(integer2 / 2);
					}

					if (apg.cG() > (double)fu26.w()) {
						integer25 -= random15.nextInt(integer2 / 2);
					} else {
						integer25 += random15.nextInt(integer2 / 2);
					}
				}

				fu fu26x = new fu((double)integer23 + apg.cC(), (double)integer24 + apg.cD(), (double)integer25 + apg.cG());
				if (fu26x.v() >= 0 && fu26x.v() <= apg.l.I() && (!boolean16 || apg.a(fu26x)) && (!boolean12 || awv14.a(fu26x))) {
					if (boolean9) {
						fu26x = a(fu26x, random15.nextInt(integer10 + 1) + integer11, apg.l.I(), fu -> apg.l.d_(fu).c().b());
					}

					if (boolean6 || !apg.l.b(fu26x).a(acz.a)) {
						czb czb27 = czl.a(apg.l, fu26x.i());
						if (apg.a(czb27) == 0.0F) {
							double double28 = toDoubleFunction.applyAsDouble(fu26x);
							if (double28 > double18) {
								double18 = double28;
								fu20 = fu26x;
								boolean17 = true;
							}
						}
					}
				}
			}
		}

		return boolean17 ? dem.c(fu20) : null;
	}

	@Nullable
	private static fu a(Random random, int integer2, int integer3, int integer4, @Nullable dem dem, double double6) {
		if (dem != null && !(double6 >= Math.PI)) {
			double double8 = aec.d(dem.d, dem.b) - (float) (Math.PI / 2);
			double double10 = double8 + (double)(2.0F * random.nextFloat() - 1.0F) * double6;
			double double12 = Math.sqrt(random.nextDouble()) * (double)aec.a * (double)integer2;
			double double14 = -double12 * Math.sin(double10);
			double double16 = double12 * Math.cos(double10);
			if (!(Math.abs(double14) > (double)integer2) && !(Math.abs(double16) > (double)integer2)) {
				int integer18 = random.nextInt(2 * integer3 + 1) - integer3 + integer4;
				return new fu(double14, (double)integer18, double16);
			} else {
				return null;
			}
		} else {
			int integer8 = random.nextInt(2 * integer2 + 1) - integer2;
			int integer9 = random.nextInt(2 * integer3 + 1) - integer3 + integer4;
			int integer10 = random.nextInt(2 * integer2 + 1) - integer2;
			return new fu(integer8, integer9, integer10);
		}
	}

	static fu a(fu fu, int integer2, int integer3, Predicate<fu> predicate) {
		if (integer2 < 0) {
			throw new IllegalArgumentException("aboveSolidAmount was " + integer2 + ", expected >= 0");
		} else if (!predicate.test(fu)) {
			return fu;
		} else {
			fu fu5 = fu.b();

			while (fu5.v() < integer3 && predicate.test(fu5)) {
				fu5 = fu5.b();
			}

			fu fu6 = fu5;

			while (fu6.v() < integer3 && fu6.v() - fu5.v() < integer2) {
				fu fu7 = fu6.b();
				if (predicate.test(fu7)) {
					break;
				}

				fu6 = fu7;
			}

			return fu6;
		}
	}
}
