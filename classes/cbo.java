public class cbo extends caw {
	public static final cgi c = cfz.aD;

	public cbo(cfi.c c, cgs cgs) {
		super(c, cgs);
		this.j(this.n.b().a(cbo.c, Integer.valueOf(0)).a(a, Boolean.valueOf(false)));
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		return bqd.d_(fu.c()).c().b();
	}

	@Override
	public cfj a(bin bin) {
		cxa cxa3 = bin.o().b(bin.a());
		return this.n().a(c, Integer.valueOf(aec.c((double)((180.0F + bin.h()) * 16.0F / 360.0F) + 0.5) & 15)).a(a, Boolean.valueOf(cxa3.a() == cxb.c));
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return fz == fz.DOWN && !this.a(cfj1, bqc, fu5) ? bvs.a.n() : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a(c, Integer.valueOf(cap.a((Integer)cfj.c(c), 16)));
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		return cfj.a(c, Integer.valueOf(bzj.a((Integer)cfj.c(c), 16)));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(c, cbo.a);
	}
}
