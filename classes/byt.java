public class byt extends bww {
	protected byt(cfi.c c) {
		super(1.0F, 1.0F, 16.0F, 16.0F, 16.0F, c);
		this.j(
			this.n
				.b()
				.a(a, Boolean.valueOf(false))
				.a(b, Boolean.valueOf(false))
				.a(byt.c, Boolean.valueOf(false))
				.a(d, Boolean.valueOf(false))
				.a(e, Boolean.valueOf(false))
		);
	}

	@Override
	public cfj a(bin bin) {
		bpg bpg3 = bin.o();
		fu fu4 = bin.a();
		cxa cxa5 = bin.o().b(bin.a());
		fu fu6 = fu4.d();
		fu fu7 = fu4.e();
		fu fu8 = fu4.f();
		fu fu9 = fu4.g();
		cfj cfj10 = bpg3.d_(fu6);
		cfj cfj11 = bpg3.d_(fu7);
		cfj cfj12 = bpg3.d_(fu8);
		cfj cfj13 = bpg3.d_(fu9);
		return this.n()
			.a(a, Boolean.valueOf(this.a(cfj10, cfj10.d(bpg3, fu6, fz.SOUTH))))
			.a(c, Boolean.valueOf(this.a(cfj11, cfj11.d(bpg3, fu7, fz.NORTH))))
			.a(d, Boolean.valueOf(this.a(cfj12, cfj12.d(bpg3, fu8, fz.EAST))))
			.a(b, Boolean.valueOf(this.a(cfj13, cfj13.d(bpg3, fu9, fz.WEST))))
			.a(e, Boolean.valueOf(cxa5.a() == cxb.c));
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if ((Boolean)cfj1.c(e)) {
			bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
		}

		return fz.n().d() ? cfj1.a((cgl)f.get(fz), Boolean.valueOf(this.a(cfj3, cfj3.d(bqc, fu6, fz.f())))) : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public dfg a(cfj cfj, bpg bpg, fu fu, der der) {
		return dfd.a();
	}

	public final boolean a(cfj cfj, boolean boolean2) {
		bvr bvr4 = cfj.b();
		return !b(bvr4) && boolean2 || bvr4 instanceof byt || bvr4.a(acx.E);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(byt.a, b, d, c, e);
	}
}
