public class atv extends auu {
	private final aym g;

	public atv(aym aym, double double2) {
		super(aym, double2, 8);
		this.g = aym;
	}

	@Override
	public boolean a() {
		return this.g.eL() && !this.g.eP() && super.a();
	}

	@Override
	public void c() {
		super.c();
		this.g.v(false);
	}

	@Override
	public void d() {
		super.d();
		this.g.v(false);
	}

	@Override
	public void e() {
		super.e();
		this.g.v(this.k());
	}

	@Override
	protected boolean a(bqd bqd, fu fu) {
		if (!bqd.w(fu.b())) {
			return false;
		} else {
			cfj cfj4 = bqd.d_(fu);
			if (cfj4.a(bvs.bR)) {
				return cdp.a(bqd, fu) < 1;
			} else {
				return cfj4.a(bvs.bY) && cfj4.c(byb.b) ? true : cfj4.a(acx.K, a -> (Boolean)a.d(bvm.a).map(cfx -> cfx != cfx.HEAD).orElse(true));
			}
		}
	}
}
