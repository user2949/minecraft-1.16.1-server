public class byn extends byl {
	protected static final dfg a = bvr.a(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);

	public byn(cfi.c c) {
		super(c);
	}

	private static boolean c(aom aom) {
		return aom instanceof aoy || aom instanceof bfr || aom instanceof bbh || aom instanceof bft;
	}

	@Override
	public dfg c(cfj cfj, bpg bpg, fu fu, der der) {
		return a;
	}

	@Override
	public void a(bqb bqb, fu fu, aom aom, float float4) {
		aom.a(acl.fN, 1.0F, 1.0F);
		if (!bqb.v) {
			bqb.a(aom, (byte)54);
		}

		if (aom.b(float4, 0.2F)) {
			aom.a(this.aw.g(), this.aw.a() * 0.5F, this.aw.b() * 0.75F);
		}
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, aom aom) {
		if (this.a(fu, aom)) {
			this.a(aom, fu);
			this.d(aom);
			this.a(bqb, aom);
		}

		super.a(cfj, bqb, fu, aom);
	}

	private boolean a(fu fu, aom aom) {
		if (aom.aj()) {
			return false;
		} else if (aom.cD() > (double)fu.v() + 0.9375 - 1.0E-7) {
			return false;
		} else if (aom.cB().c >= -0.08) {
			return false;
		} else {
			double double4 = Math.abs((double)fu.u() + 0.5 - aom.cC());
			double double6 = Math.abs((double)fu.w() + 0.5 - aom.cG());
			double double8 = 0.4375 + (double)(aom.cx() / 2.0F);
			return double4 + 1.0E-7 > double8 || double6 + 1.0E-7 > double8;
		}
	}

	private void a(aom aom, fu fu) {
		if (aom instanceof ze && aom.l.Q() % 20L == 0L) {
			aa.J.a((ze)aom, aom.l.d_(fu));
		}
	}

	private void d(aom aom) {
		dem dem3 = aom.cB();
		if (dem3.c < -0.13) {
			double double4 = -0.05 / dem3.c;
			aom.e(new dem(dem3.b * double4, -0.05, dem3.d * double4));
		} else {
			aom.e(new dem(dem3.b, -0.05, dem3.d));
		}

		aom.C = 0.0F;
	}

	private void a(bqb bqb, aom aom) {
		if (c(aom)) {
			if (bqb.t.nextInt(5) == 0) {
				aom.a(acl.fN, 1.0F, 1.0F);
			}

			if (!bqb.v && bqb.t.nextInt(5) == 0) {
				bqb.a(aom, (byte)53);
			}
		}
	}
}
