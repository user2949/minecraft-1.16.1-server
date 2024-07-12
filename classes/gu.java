public class gu extends gv {
	private final gv b = new gv();
	private final bft.b c;

	public gu(bft.b b) {
		this.c = b;
	}

	@Override
	public bki a(fv fv, bki bki) {
		fz fz4 = fv.e().c(bxd.a);
		bqb bqb5 = fv.h();
		double double6 = fv.a() + (double)((float)fz4.i() * 1.125F);
		double double8 = fv.b() + (double)((float)fz4.j() * 1.125F);
		double double10 = fv.c() + (double)((float)fz4.k() * 1.125F);
		fu fu12 = fv.d().a(fz4);
		double double13;
		if (bqb5.b(fu12).a(acz.a)) {
			double13 = 1.0;
		} else {
			if (!bqb5.d_(fu12).g() || !bqb5.b(fu12.c()).a(acz.a)) {
				return this.b.dispense(fv, bki);
			}

			double13 = 0.0;
		}

		bft bft15 = new bft(bqb5, double6, double8 + double13, double10);
		bft15.a(this.c);
		bft15.p = fz4.o();
		bqb5.c(bft15);
		bki.g(1);
		return bki;
	}

	@Override
	protected void a(fv fv) {
		fv.h().c(1000, fv.d(), 0);
	}
}
