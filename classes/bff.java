public class bff extends beh {
	private static final tq<Boolean> e = tt.a(bff.class, ts.i);

	public bff(aoq<? extends bff> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bff(bqb bqb, aoy aoy, double double3, double double4, double double5) {
		super(aoq.aU, aoy, double3, double4, double5, bqb);
	}

	@Override
	protected float i() {
		return this.k() ? 0.73F : super.i();
	}

	@Override
	public boolean bm() {
		return false;
	}

	@Override
	public float a(bpt bpt, bpg bpg, fu fu, cfj cfj, cxa cxa, float float6) {
		return this.k() && baw.c(cfj) ? Math.min(0.8F, float6) : float6;
	}

	@Override
	protected void a(dei dei) {
		super.a(dei);
		if (!this.l.v) {
			aom aom3 = dei.a();
			aom aom4 = this.v();
			boolean boolean5;
			if (aom4 instanceof aoy) {
				aoy aoy6 = (aoy)aom4;
				boolean5 = aom3.a(anw.a(this, aoy6), 8.0F);
				if (boolean5) {
					if (aom3.aU()) {
						this.a(aoy6, aom3);
					} else {
						aoy6.b(5.0F);
					}
				}
			} else {
				boolean5 = aom3.a(anw.o, 5.0F);
			}

			if (boolean5 && aom3 instanceof aoy) {
				int integer6 = 0;
				if (this.l.ac() == and.NORMAL) {
					integer6 = 10;
				} else if (this.l.ac() == and.HARD) {
					integer6 = 40;
				}

				if (integer6 > 0) {
					((aoy)aom3).c(new aog(aoi.t, 20 * integer6, 1));
				}
			}
		}
	}

	@Override
	protected void a(dej dej) {
		super.a(dej);
		if (!this.l.v) {
			bpt.a a3 = this.l.S().b(bpx.b) ? bpt.a.DESTROY : bpt.a.NONE;
			this.l.a(this, this.cC(), this.cD(), this.cG(), 1.0F, false, a3);
			this.aa();
		}
	}

	@Override
	public boolean aQ() {
		return false;
	}

	@Override
	public boolean a(anw anw, float float2) {
		return false;
	}

	@Override
	protected void e() {
		this.S.a(e, false);
	}

	@Override
	public boolean k() {
		return this.S.a(e);
	}

	public void a(boolean boolean1) {
		this.S.b(e, boolean1);
	}

	@Override
	protected boolean Y_() {
		return false;
	}
}
