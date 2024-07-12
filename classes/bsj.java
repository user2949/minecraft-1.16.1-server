public enum bsj implements brj {
	INSTANCE;

	@Override
	public bre a(long long1, int integer2, int integer3, int integer4, brg.a a) {
		int integer8 = integer2 - 2;
		int integer9 = integer3 - 2;
		int integer10 = integer4 - 2;
		int integer11 = integer8 >> 2;
		int integer12 = integer9 >> 2;
		int integer13 = integer10 >> 2;
		double double14 = (double)(integer8 & 3) / 4.0;
		double double16 = (double)(integer9 & 3) / 4.0;
		double double18 = (double)(integer10 & 3) / 4.0;
		double[] arr20 = new double[8];

		for (int integer21 = 0; integer21 < 8; integer21++) {
			boolean boolean22 = (integer21 & 4) == 0;
			boolean boolean23 = (integer21 & 2) == 0;
			boolean boolean24 = (integer21 & 1) == 0;
			int integer25 = boolean22 ? integer11 : integer11 + 1;
			int integer26 = boolean23 ? integer12 : integer12 + 1;
			int integer27 = boolean24 ? integer13 : integer13 + 1;
			double double28 = boolean22 ? double14 : double14 - 1.0;
			double double30 = boolean23 ? double16 : double16 - 1.0;
			double double32 = boolean24 ? double18 : double18 - 1.0;
			arr20[integer21] = a(long1, integer25, integer26, integer27, double28, double30, double32);
		}

		int integer21 = 0;
		double double22 = arr20[0];

		for (int integer24 = 1; integer24 < 8; integer24++) {
			if (double22 > arr20[integer24]) {
				integer21 = integer24;
				double22 = arr20[integer24];
			}
		}

		int integer24x = (integer21 & 4) == 0 ? integer11 : integer11 + 1;
		int integer25 = (integer21 & 2) == 0 ? integer12 : integer12 + 1;
		int integer26 = (integer21 & 1) == 0 ? integer13 : integer13 + 1;
		return a.b(integer24x, integer25, integer26);
	}

	private static double a(long long1, int integer2, int integer3, int integer4, double double5, double double6, double double7) {
		long long12 = aea.a(long1, (long)integer2);
		long12 = aea.a(long12, (long)integer3);
		long12 = aea.a(long12, (long)integer4);
		long12 = aea.a(long12, (long)integer2);
		long12 = aea.a(long12, (long)integer3);
		long12 = aea.a(long12, (long)integer4);
		double double14 = a(long12);
		long12 = aea.a(long12, long1);
		double double16 = a(long12);
		long12 = aea.a(long12, long1);
		double double18 = a(long12);
		return a(double7 + double18) + a(double6 + double16) + a(double5 + double14);
	}

	private static double a(long long1) {
		double double3 = (double)((int)Math.floorMod(long1 >> 24, 1024L)) / 1024.0;
		return (double3 - 0.5) * 0.9;
	}

	private static double a(double double1) {
		return double1 * double1;
	}
}
