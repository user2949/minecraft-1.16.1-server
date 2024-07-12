public class cde extends cef {
	private gi<bki> a = gi.a(27, bki.b);
	private int b;

	private cde(cdm<?> cdm) {
		super(cdm);
	}

	public cde() {
		this(cdm.z);
	}

	@Override
	public le a(le le) {
		super.a(le);
		if (!this.c(le)) {
			ana.a(le, this.a);
		}

		return le;
	}

	@Override
	public void a(cfj cfj, le le) {
		super.a(cfj, le);
		this.a = gi.a(this.ab_(), bki.b);
		if (!this.b(le)) {
			ana.b(le, this.a);
		}
	}

	@Override
	public int ab_() {
		return 27;
	}

	@Override
	protected gi<bki> f() {
		return this.a;
	}

	@Override
	protected void a(gi<bki> gi) {
		this.a = gi;
	}

	@Override
	protected mr g() {
		return new ne("container.barrel");
	}

	@Override
	protected bgi a(int integer, beb beb) {
		return bgp.a(integer, beb, this);
	}

	@Override
	public void c_(bec bec) {
		if (!bec.a_()) {
			if (this.b < 0) {
				this.b = 0;
			}

			this.b++;
			cfj cfj3 = this.p();
			boolean boolean4 = (Boolean)cfj3.c(bva.b);
			if (!boolean4) {
				this.a(cfj3, acl.aj);
				this.a(cfj3, true);
			}

			this.j();
		}
	}

	private void j() {
		this.d.G().a(this.o(), this.p().b(), 5);
	}

	public void h() {
		int integer2 = this.e.u();
		int integer3 = this.e.v();
		int integer4 = this.e.w();
		this.b = cdp.a(this.d, this, integer2, integer3, integer4);
		if (this.b > 0) {
			this.j();
		} else {
			cfj cfj5 = this.p();
			if (!cfj5.a(bvs.lS)) {
				this.an_();
				return;
			}

			boolean boolean6 = (Boolean)cfj5.c(bva.b);
			if (boolean6) {
				this.a(cfj5, acl.ai);
				this.a(cfj5, false);
			}
		}
	}

	@Override
	public void b_(bec bec) {
		if (!bec.a_()) {
			this.b--;
		}
	}

	private void a(cfj cfj, boolean boolean2) {
		this.d.a(this.o(), cfj.a(bva.b, Boolean.valueOf(boolean2)), 3);
	}

	private void a(cfj cfj, ack ack) {
		gr gr4 = ((fz)cfj.c(bva.a)).p();
		double double5 = (double)this.e.u() + 0.5 + (double)gr4.u() / 2.0;
		double double7 = (double)this.e.v() + 0.5 + (double)gr4.v() / 2.0;
		double double9 = (double)this.e.w() + 0.5 + (double)gr4.w() / 2.0;
		this.d.a(null, double5, double7, double9, ack, acm.BLOCKS, 0.5F, this.d.t.nextFloat() * 0.1F + 0.9F);
	}
}
