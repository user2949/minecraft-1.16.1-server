import javax.annotation.Nullable;

public class byv extends bvg {
	public static final cga a = cfz.n;

	protected byv(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Boolean.valueOf(false)));
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, @Nullable aoy aoy, bki bki) {
		super.a(bqb, fu, cfj, aoy, bki);
		le le7 = bki.p();
		if (le7.e("BlockEntityTag")) {
			le le8 = le7.p("BlockEntityTag");
			if (le8.e("RecordItem")) {
				bqb.a(fu, cfj.a(a, Boolean.valueOf(true)), 2);
			}
		}
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if ((Boolean)cfj.c(a)) {
			this.a(bqb, fu);
			cfj = cfj.a(a, Boolean.valueOf(false));
			bqb.a(fu, cfj, 2);
			return ang.a(bqb.v);
		} else {
			return ang.PASS;
		}
	}

	public void a(bqc bqc, fu fu, cfj cfj, bki bki) {
		cdl cdl6 = bqc.c(fu);
		if (cdl6 instanceof cec) {
			((cec)cdl6).a(bki.i());
			bqc.a(fu, cfj.a(a, Boolean.valueOf(true)), 2);
		}
	}

	private void a(bqb bqb, fu fu) {
		if (!bqb.v) {
			cdl cdl4 = bqb.c(fu);
			if (cdl4 instanceof cec) {
				cec cec5 = (cec)cdl4;
				bki bki6 = cec5.d();
				if (!bki6.a()) {
					bqb.c(1010, fu, 0);
					cec5.aa_();
					float float7 = 0.7F;
					double double8 = (double)(bqb.t.nextFloat() * 0.7F) + 0.15F;
					double double10 = (double)(bqb.t.nextFloat() * 0.7F) + 0.060000002F + 0.6;
					double double12 = (double)(bqb.t.nextFloat() * 0.7F) + 0.15F;
					bki bki14 = bki6.i();
					bbg bbg15 = new bbg(bqb, (double)fu.u() + double8, (double)fu.v() + double10, (double)fu.w() + double12, bki14);
					bbg15.m();
					bqb.c(bbg15);
				}
			}
		}
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj1.a(cfj4.b())) {
			this.a(bqb, fu);
			super.a(cfj1, bqb, fu, cfj4, boolean5);
		}
	}

	@Override
	public cdl a(bpg bpg) {
		return new cec();
	}

	@Override
	public boolean a(cfj cfj) {
		return true;
	}

	@Override
	public int a(cfj cfj, bqb bqb, fu fu) {
		cdl cdl5 = bqb.c(fu);
		if (cdl5 instanceof cec) {
			bke bke6 = ((cec)cdl5).d().b();
			if (bke6 instanceof bkx) {
				return ((bkx)bke6).f();
			}
		}

		return 0;
	}

	@Override
	public cak b(cfj cfj) {
		return cak.MODEL;
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(byv.a);
	}
}
