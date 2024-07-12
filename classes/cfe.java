public class cfe {
	public static deg a(deg deg, fz fz, double double3) {
		double double5 = double3 * (double)fz.e().a();
		double double7 = Math.min(double5, 0.0);
		double double9 = Math.max(double5, 0.0);
		switch (fz) {
			case WEST:
				return new deg(deg.a + double7, deg.b, deg.c, deg.a + double9, deg.e, deg.f);
			case EAST:
				return new deg(deg.d + double7, deg.b, deg.c, deg.d + double9, deg.e, deg.f);
			case DOWN:
				return new deg(deg.a, deg.b + double7, deg.c, deg.d, deg.b + double9, deg.f);
			case UP:
			default:
				return new deg(deg.a, deg.e + double7, deg.c, deg.d, deg.e + double9, deg.f);
			case NORTH:
				return new deg(deg.a, deg.b, deg.c + double7, deg.d, deg.e, deg.c + double9);
			case SOUTH:
				return new deg(deg.a, deg.b, deg.f + double7, deg.d, deg.e, deg.f + double9);
		}
	}
}
