public class gv implements gw {
	@Override
	public final bki dispense(fv fv, bki bki) {
		bki bki4 = this.a(fv, bki);
		this.a(fv);
		this.a(fv, fv.e().c(bxd.a));
		return bki4;
	}

	protected bki a(fv fv, bki bki) {
		fz fz4 = fv.e().c(bxd.a);
		gj gj5 = bxd.a(fv);
		bki bki6 = bki.a(1);
		a(fv.h(), bki6, 6, fz4, gj5);
		return bki;
	}

	public static void a(bqb bqb, bki bki, int integer, fz fz, gj gj) {
		double double6 = gj.a();
		double double8 = gj.b();
		double double10 = gj.c();
		if (fz.n() == fz.a.Y) {
			double8 -= 0.125;
		} else {
			double8 -= 0.15625;
		}

		bbg bbg12 = new bbg(bqb, double6, double8, double10, bki);
		double double13 = bqb.t.nextDouble() * 0.1 + 0.2;
		bbg12.m(
			bqb.t.nextGaussian() * 0.0075F * (double)integer + (double)fz.i() * double13,
			bqb.t.nextGaussian() * 0.0075F * (double)integer + 0.2F,
			bqb.t.nextGaussian() * 0.0075F * (double)integer + (double)fz.k() * double13
		);
		bqb.c(bbg12);
	}

	protected void a(fv fv) {
		fv.h().c(1000, fv.d(), 0);
	}

	protected void a(fv fv, fz fz) {
		fv.h().c(2000, fv.d(), fz.c());
	}
}
