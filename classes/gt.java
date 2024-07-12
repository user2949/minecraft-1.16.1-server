public abstract class gt extends gv {
	@Override
	public bki a(fv fv, bki bki) {
		bqb bqb4 = fv.h();
		gj gj5 = bxd.a(fv);
		fz fz6 = fv.e().c(bxd.a);
		bes bes7 = this.a(bqb4, gj5, bki);
		bes7.c((double)fz6.i(), (double)((float)fz6.j() + 0.1F), (double)fz6.k(), this.b(), this.a());
		bqb4.c(bes7);
		bki.g(1);
		return bki;
	}

	@Override
	protected void a(fv fv) {
		fv.h().c(1002, fv.d(), 0);
	}

	protected abstract bes a(bqb bqb, gj gj, bki bki);

	protected float a() {
		return 6.0F;
	}

	protected float b() {
		return 1.1F;
	}
}
