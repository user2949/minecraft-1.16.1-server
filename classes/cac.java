public class cac extends cbq {
	protected cac(cfi.c c) {
		super(c);
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		bki bki8 = bec.b(anf);
		if (bki8.b() == bkk.ng) {
			if (!bqb.v) {
				fz fz9 = deh.b();
				fz fz10 = fz9.n() == fz.a.Y ? bec.bY().f() : fz9;
				bqb.a(null, fu, acl.lQ, acm.BLOCKS, 1.0F, 1.0F);
				bqb.a(fu, bvs.cU.n().a(bwe.a, fz10), 11);
				bbg bbg11 = new bbg(
					bqb, (double)fu.u() + 0.5 + (double)fz10.i() * 0.65, (double)fu.v() + 0.1, (double)fu.w() + 0.5 + (double)fz10.k() * 0.65, new bki(bkk.nj, 4)
				);
				bbg11.m(0.05 * (double)fz10.i() + bqb.t.nextDouble() * 0.02, 0.05, 0.05 * (double)fz10.k() + bqb.t.nextDouble() * 0.02);
				bqb.c(bbg11);
				bki8.a(1, bec, becx -> becx.d(anf));
			}

			return ang.a(bqb.v);
		} else {
			return super.a(cfj, bqb, fu, bec, anf, deh);
		}
	}

	@Override
	public cbp c() {
		return (cbp)bvs.dN;
	}

	@Override
	public buw d() {
		return (buw)bvs.dL;
	}
}
