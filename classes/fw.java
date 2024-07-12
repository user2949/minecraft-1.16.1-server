public class fw implements fv {
	private final bqb a;
	private final fu b;

	public fw(bqb bqb, fu fu) {
		this.a = bqb;
		this.b = fu;
	}

	@Override
	public bqb h() {
		return this.a;
	}

	@Override
	public double a() {
		return (double)this.b.u() + 0.5;
	}

	@Override
	public double b() {
		return (double)this.b.v() + 0.5;
	}

	@Override
	public double c() {
		return (double)this.b.w() + 0.5;
	}

	@Override
	public fu d() {
		return this.b;
	}

	@Override
	public cfj e() {
		return this.a.d_(this.b);
	}

	@Override
	public <T extends cdl> T g() {
		return (T)this.a.c(this.b);
	}
}
