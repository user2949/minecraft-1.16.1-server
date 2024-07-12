public class bxt extends bww {
	private final dfg[] i;

	public bxt(cfi.c c) {
		super(2.0F, 2.0F, 16.0F, 16.0F, 24.0F, c);
		this.j(
			this.n
				.b()
				.a(a, Boolean.valueOf(false))
				.a(b, Boolean.valueOf(false))
				.a(bxt.c, Boolean.valueOf(false))
				.a(d, Boolean.valueOf(false))
				.a(e, Boolean.valueOf(false))
		);
		this.i = this.a(2.0F, 1.0F, 16.0F, 6.0F, 15.0F);
	}

	@Override
	public dfg d(cfj cfj, bpg bpg, fu fu) {
		return this.i[this.g(cfj)];
	}

	@Override
	public dfg a(cfj cfj, bpg bpg, fu fu, der der) {
		return this.b(cfj, bpg, fu, der);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}

	public boolean a(cfj cfj, boolean boolean2, fz fz) {
		bvr bvr5 = cfj.b();
		boolean boolean6 = this.c(bvr5);
		boolean boolean7 = bvr5 instanceof bxu && bxu.a(cfj, fz);
		return !b(bvr5) && boolean2 || boolean6 || boolean7;
	}

	private boolean c(bvr bvr) {
		return bvr.a(acx.L) && bvr.a(acx.j) == this.n().a(acx.j);
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			bki bki8 = bec.b(anf);
			return bki8.b() == bkk.pG ? ang.SUCCESS : ang.PASS;
		} else {
			return bkm.a(bec, bqb, fu);
		}
	}

	@Override
	public cfj a(bin bin) {
		bpg bpg3 = bin.o();
		fu fu4 = bin.a();
		cxa cxa5 = bin.o().b(bin.a());
		fu fu6 = fu4.d();
		fu fu7 = fu4.g();
		fu fu8 = fu4.e();
		fu fu9 = fu4.f();
		cfj cfj10 = bpg3.d_(fu6);
		cfj cfj11 = bpg3.d_(fu7);
		cfj cfj12 = bpg3.d_(fu8);
		cfj cfj13 = bpg3.d_(fu9);
		return super.a(bin)
			.a(a, Boolean.valueOf(this.a(cfj10, cfj10.d(bpg3, fu6, fz.SOUTH), fz.SOUTH)))
			.a(b, Boolean.valueOf(this.a(cfj11, cfj11.d(bpg3, fu7, fz.WEST), fz.WEST)))
			.a(c, Boolean.valueOf(this.a(cfj12, cfj12.d(bpg3, fu8, fz.NORTH), fz.NORTH)))
			.a(d, Boolean.valueOf(this.a(cfj13, cfj13.d(bpg3, fu9, fz.EAST), fz.EAST)))
			.a(e, Boolean.valueOf(cxa5.a() == cxb.c));
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if ((Boolean)cfj1.c(e)) {
			bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
		}

		return fz.n().e() == fz.c.HORIZONTAL
			? cfj1.a((cgl)f.get(fz), Boolean.valueOf(this.a(cfj3, cfj3.d(bqc, fu6, fz.f()), fz.f())))
			: super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bxt.a, b, d, c, e);
	}
}
