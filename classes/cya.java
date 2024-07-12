public enum cya implements cyn {
	INSTANCE;

	@Override
	public int a(cxn cxn, int integer2, int integer3) {
		cwc cwc5 = cxn.b();
		double double6 = cwc5.a((double)integer2 / 8.0, (double)integer3 / 8.0, 0.0, 0.0, 0.0);
		if (double6 > 0.4) {
			return cxz.a;
		} else if (double6 > 0.2) {
			return cxz.b;
		} else if (double6 < -0.4) {
			return cxz.e;
		} else {
			return double6 < -0.2 ? cxz.d : cxz.c;
		}
	}
}
