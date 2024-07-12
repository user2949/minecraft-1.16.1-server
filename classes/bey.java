public abstract class bey extends bez {
	private static final tq<bki> b = tt.a(bey.class, ts.g);

	public bey(aoq<? extends bey> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bey(aoq<? extends bey> aoq, double double2, double double3, double double4, bqb bqb) {
		super(aoq, double2, double3, double4, bqb);
	}

	public bey(aoq<? extends bey> aoq, aoy aoy, bqb bqb) {
		super(aoq, aoy, bqb);
	}

	public void b(bki bki) {
		if (bki.b() != this.h() || bki.n()) {
			this.Y().b(b, v.a(bki.i(), bkix -> bkix.e(1)));
		}
	}

	protected abstract bke h();

	protected bki i() {
		return this.Y().a(b);
	}

	public bki g() {
		bki bki2 = this.i();
		return bki2.a() ? new bki(this.h()) : bki2;
	}

	@Override
	protected void e() {
		this.Y().a(b, bki.b);
	}

	@Override
	public void b(le le) {
		super.b(le);
		bki bki3 = this.i();
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
