public class bvu extends bvg {
	public static final cga[] a = new cga[]{cfz.k, cfz.l, cfz.m};
	protected static final dfg b = dfd.a(bvr.a(1.0, 0.0, 1.0, 15.0, 2.0, 15.0), bvr.a(7.0, 0.0, 7.0, 9.0, 14.0, 9.0));

	public bvu(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a[0], Boolean.valueOf(false)).a(a[1], Boolean.valueOf(false)).a(a[2], Boolean.valueOf(false)));
	}

	@Override
	public cak b(cfj cfj) {
		return cak.MODEL;
	}

	@Override
	public cdl a(bpg bpg) {
		return new cdn();
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return b;
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			return ang.SUCCESS;
		} else {
			cdl cdl8 = bqb.c(fu);
			if (cdl8 instanceof cdn) {
				bec.a((cdn)cdl8);
				bec.a(acu.Z);
			}

			return ang.CONSUME;
		}
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, aoy aoy, bki bki) {
		if (bki.t()) {
			cdl cdl7 = bqb.c(fu);
			if (cdl7 instanceof cdn) {
				((cdn)cdl7).a(bki.r());
			}
		}
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj1.a(cfj4.b())) {
			cdl cdl7 = bqb.c(fu);
			if (cdl7 instanceof cdn) {
				anc.a(bqb, fu, (cdn)cdl7);
			}

			super.a(cfj1, bqb, fu, cfj4, boolean5);
		}
	}

	@Override
	public boolean a(cfj cfj) {
		return true;
	}

	@Override
	public int a(cfj cfj, bqb bqb, fu fu) {
		return bgi.a(bqb.c(fu));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bvu.a[0], bvu.a[1], bvu.a[2]);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
