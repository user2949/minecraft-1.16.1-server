public class cba extends byl {
	public cba(cfi.c c) {
		super(c);
	}

	@Override
	public void a(bqb bqb, fu fu, aom aom, float float4) {
		if (aom.bs()) {
			super.a(bqb, fu, aom, float4);
		} else {
			aom.b(float4, 0.0F);
		}
	}

	@Override
	public void a(bpg bpg, aom aom) {
		if (aom.bs()) {
			super.a(bpg, aom);
		} else {
			this.a(aom);
		}
	}

	private void a(aom aom) {
		dem dem3 = aom.cB();
		if (dem3.c < 0.0) {
			double double4 = aom instanceof aoy ? 1.0 : 0.8;
			aom.m(dem3.b, -dem3.c * double4, dem3.d);
		}
	}

	@Override
	public void a(bqb bqb, fu fu, aom aom) {
		double double5 = Math.abs(aom.cB().c);
		if (double5 < 0.1 && !aom.br()) {
			double double7 = 0.4 + double5 * 0.2;
			aom.e(aom.cB().d(double7, 1.0, double7));
		}

		super.a(bqb, fu, aom);
	}
}
