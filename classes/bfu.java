public class bfu {
	public static int[][] a(fz fz) {
		fz fz2 = fz.g();
		fz fz3 = fz2.f();
		fz fz4 = fz.f();
		return new int[][]{
			{fz2.i(), fz2.k()},
			{fz3.i(), fz3.k()},
			{fz4.i() + fz2.i(), fz4.k() + fz2.k()},
			{fz4.i() + fz3.i(), fz4.k() + fz3.k()},
			{fz.i() + fz2.i(), fz.k() + fz2.k()},
			{fz.i() + fz3.i(), fz.k() + fz3.k()},
			{fz4.i(), fz4.k()},
			{fz.i(), fz.k()}
		};
	}

	public static boolean a(double double1) {
		return !Double.isInfinite(double1) && double1 < 1.0;
	}

	public static boolean a(bqb bqb, aoy aoy, deg deg) {
		return bqb.b(aoy, deg).allMatch(dfg::b);
	}
}
