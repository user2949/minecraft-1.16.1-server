public abstract class cxg extends cwy {
	@Override
	public cwz d() {
		return cxb.b;
	}

	@Override
	public cwz e() {
		return cxb.c;
	}

	@Override
	public bke a() {
		return bkk.lL;
	}

	@Override
	protected boolean f() {
		return true;
	}

	@Override
	protected void a(bqc bqc, fu fu, cfj cfj) {
		cdl cdl5 = cfj.b().q() ? bqc.c(fu) : null;
		bvr.a(cfj, bqc.n(), fu, cdl5);
	}

	@Override
	public int b(bqd bqd) {
		return 4;
	}

	@Override
	public cfj b(cxa cxa) {
		return bvs.A.n().a(bze.a, Integer.valueOf(e(cxa)));
	}

	@Override
	public boolean a(cwz cwz) {
		return cwz == cxb.c || cwz == cxb.b;
	}

	@Override
	public int c(bqd bqd) {
		return 1;
	}

	@Override
	public int a(bqd bqd) {
		return 5;
	}

	@Override
	public boolean a(cxa cxa, bpg bpg, fu fu, cwz cwz, fz fz) {
		return fz == fz.DOWN && !cwz.a(acz.a);
	}

	@Override
	protected float c() {
		return 100.0F;
	}

	public static class a extends cxg {
		@Override
		protected void a(cfk.a<cwz, cxa> a) {
			super.a(a);
			a.a(b);
		}

		@Override
		public int d(cxa cxa) {
			return (Integer)cxa.c(b);
		}

		@Override
		public boolean c(cxa cxa) {
			return false;
		}
	}

	public static class b extends cxg {
		@Override
		public int d(cxa cxa) {
			return 8;
		}

		@Override
		public boolean c(cxa cxa) {
			return true;
		}
	}
}
