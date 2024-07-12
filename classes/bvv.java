import java.util.Random;

public class bvv extends bvr implements bvw {
	public static final cga a = cfz.e;

	public bvv(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Boolean.valueOf(true)));
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, aom aom) {
		cfj cfj6 = bqb.d_(fu.b());
		if (cfj6.g()) {
			aom.k((Boolean)cfj.c(a));
			if (!bqb.v) {
				zd zd7 = (zd)bqb;

				for (int integer8 = 0; integer8 < 2; integer8++) {
					zd7.a(hh.Z, (double)fu.u() + bqb.t.nextDouble(), (double)(fu.v() + 1), (double)fu.w() + bqb.t.nextDouble(), 1, 0.0, 0.0, 0.0, 1.0);
					zd7.a(hh.e, (double)fu.u() + bqb.t.nextDouble(), (double)(fu.v() + 1), (double)fu.w() + bqb.t.nextDouble(), 1, 0.0, 0.01, 0.0, 0.2);
				}
			}
		} else {
			aom.l((Boolean)cfj.c(a));
		}
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		a(bqb, fu.b(), a((bpg)bqb, fu.c()));
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		a(zd, fu.b(), a((bpg)zd, fu));
	}

	@Override
	public cxa d(cfj cfj) {
		return cxb.c.a(false);
	}

	public static void a(bqc bqc, fu fu, boolean boolean3) {
		if (a(bqc, fu)) {
			bqc.a(fu, bvs.lc.n().a(a, Boolean.valueOf(boolean3)), 2);
		}
	}

	public static boolean a(bqc bqc, fu fu) {
		cxa cxa3 = bqc.b(fu);
		return bqc.d_(fu).a(bvs.A) && cxa3.e() >= 8 && cxa3.b();
	}

	private static boolean a(bpg bpg, fu fu) {
		cfj cfj3 = bpg.d_(fu);
		return cfj3.a(bvs.lc) ? (Boolean)cfj3.c(a) : !cfj3.a(bvs.cM);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (!cfj1.a(bqc, fu5)) {
			return bvs.A.n();
		} else {
			if (fz == fz.DOWN) {
				bqc.a(fu5, bvs.lc.n().a(a, Boolean.valueOf(a((bpg)bqc, fu6))), 2);
			} else if (fz == fz.UP && !cfj3.a(bvs.lc) && a(bqc, fu6)) {
				bqc.G().a(fu5, this, 5);
			}

			bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
			return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
		}
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		cfj cfj5 = bqd.d_(fu.c());
		return cfj5.a(bvs.lc) || cfj5.a(bvs.iJ) || cfj5.a(bvs.cM);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return dfd.a();
	}

	@Override
	public cak b(cfj cfj) {
		return cak.INVISIBLE;
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bvv.a);
	}

	@Override
	public cwz b(bqc bqc, fu fu, cfj cfj) {
		bqc.a(fu, bvs.a.n(), 11);
		return cxb.c;
	}
}
