public class cbf extends bvh {
	public cbf(cfi.c c) {
		super(c, 2.0F);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return this.a(cfj1, bqc, fu5) ? this.n() : bvs.a.n();
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		return c(bqd.d_(fu.c()).b());
	}

	public static boolean c(bvr bvr) {
		return bvr.a(acx.av);
	}

	@Override
	protected boolean e(cfj cfj) {
		return true;
	}
}
