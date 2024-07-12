public class cal extends bxb {
	public static final cga a = cfz.s;
	public static final cgi d = cfz.am;

	protected cal(cfi.c c) {
		super(c);
		this.j(this.n.b().a(aq, fz.NORTH).a(d, Integer.valueOf(1)).a(a, Boolean.valueOf(false)).a(cal.c, Boolean.valueOf(false)));
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (!bec.bJ.e) {
			return ang.PASS;
		} else {
			bqb.a(fu, cfj.a(d), 3);
			return ang.a(bqb.v);
		}
	}

	@Override
	protected int g(cfj cfj) {
		return (Integer)cfj.c(d) * 2;
	}

	@Override
	public cfj a(bin bin) {
		cfj cfj3 = super.a(bin);
		return cfj3.a(a, Boolean.valueOf(this.a(bin.o(), bin.a(), cfj3)));
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return !bqc.s_() && fz.n() != ((fz)cfj1.c(aq)).n() ? cfj1.a(a, Boolean.valueOf(this.a(bqc, fu5, cfj1))) : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public boolean a(bqd bqd, fu fu, cfj cfj) {
		return this.b(bqd, fu, cfj) > 0;
	}

	@Override
	protected boolean h(cfj cfj) {
		return l(cfj);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(aq, d, cal.a, c);
	}
}
