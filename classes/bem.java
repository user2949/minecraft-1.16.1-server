public abstract class bem extends beh {
	private static final tq<bki> e = tt.a(bem.class, ts.g);

	public bem(aoq<? extends bem> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bem(aoq<? extends bem> aoq, double double2, double double3, double double4, double double5, double double6, double double7, bqb bqb) {
		super(aoq, double2, double3, double4, double5, double6, double7, bqb);
	}

	public bem(aoq<? extends bem> aoq, aoy aoy, double double3, double double4, double double5, bqb bqb) {
		super(aoq, aoy, double3, double4, double5, bqb);
	}

	public void b(bki bki) {
		if (bki.b() != bkk.oR || bki.n()) {
			this.Y().b(e, v.a(bki.i(), bkix -> bkix.e(1)));
		}
	}

	protected bki k() {
		return this.Y().a(e);
	}

	@Override
	protected void e() {
		this.Y().a(e, bki.b);
	}

	@Override
	public void b(le le) {
		super.b(le);
		bki bki3 = this.k();
		if (!bki3.a()) {
			le.a("Item", bki3.b(new le()));
		}
	}

	@Override
	public void a(le le) {
		super.a(le);
		bki bki3 = bki.a(le.p("Item"));
		this.b(bki3);
	}
}
