public class cbe extends bvr {
	public static final cga a = cfz.z;

	protected cbe(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Boolean.valueOf(false)));
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return fz != fz.UP ? super.a(cfj1, fz, cfj3, bqc, fu5, fu6) : cfj1.a(a, Boolean.valueOf(cfj3.a(bvs.cE) || cfj3.a(bvs.cC)));
	}

	@Override
	public cfj a(bin bin) {
		cfj cfj3 = bin.o().d_(bin.a().b());
		return this.n().a(a, Boolean.valueOf(cfj3.a(bvs.cE) || cfj3.a(bvs.cC)));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cbe.a);
	}
}
