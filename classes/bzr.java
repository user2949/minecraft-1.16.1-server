public class bzr extends bvr {
	public static final cgg<cgj> a = cfz.aI;
	public static final cga b = cfz.w;
	public static final cgi c = cfz.ax;

	public bzr(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, cgj.HARP).a(bzr.c, Integer.valueOf(0)).a(b, Boolean.valueOf(false)));
	}

	@Override
	public cfj a(bin bin) {
		return this.n().a(a, cgj.a(bin.o().d_(bin.a().c())));
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return fz == fz.DOWN ? cfj1.a(a, cgj.a(cfj3)) : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		boolean boolean8 = bqb.r(fu3);
		if (boolean8 != (Boolean)cfj.c(b)) {
			if (boolean8) {
				this.a(bqb, fu3);
			}

			bqb.a(fu3, cfj.a(b, Boolean.valueOf(boolean8)), 3);
		}
	}

	private void a(bqb bqb, fu fu) {
		if (bqb.d_(fu.b()).g()) {
			bqb.a(fu, this, 0, 0);
		}
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			return ang.SUCCESS;
		} else {
			cfj = cfj.a(c);
			bqb.a(fu, cfj, 3);
			this.a(bqb, fu);
			bec.a(acu.af);
			return ang.CONSUME;
		}
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, bec bec) {
		if (!bqb.v) {
			this.a(bqb, fu);
			bec.a(acu.ae);
		}
	}

	@Override
	public boolean a(cfj cfj, bqb bqb, fu fu, int integer4, int integer5) {
		int integer7 = (Integer)cfj.c(c);
		float float8 = (float)Math.pow(2.0, (double)(integer7 - 12) / 12.0);
		bqb.a(null, fu, ((cgj)cfj.c(a)).b(), acm.RECORDS, 3.0F, float8);
		bqb.a(hh.O, (double)fu.u() + 0.5, (double)fu.v() + 1.2, (double)fu.w() + 0.5, (double)integer7 / 24.0, 0.0, 0.0);
		return true;
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bzr.a, b, c);
	}
}
