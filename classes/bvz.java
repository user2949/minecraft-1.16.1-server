import java.util.Random;

public class bvz extends bvr {
	public static final cgi a = cfz.aj;
	protected static final dfg b = bvr.a(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);
	protected static final dfg c = bvr.a(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

	protected bvz(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Integer.valueOf(0)));
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		if (!cfj.a(zd, fu)) {
			zd.b(fu, true);
		}
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		fu fu6 = fu.b();
		if (zd.w(fu6)) {
			int integer7 = 1;

			while (zd.d_(fu.c(integer7)).a(this)) {
				integer7++;
			}

			if (integer7 < 3) {
				int integer8 = (Integer)cfj.c(a);
				if (integer8 == 15) {
					zd.a(fu6, this.n());
					cfj cfj9 = cfj.a(a, Integer.valueOf(0));
					zd.a(fu, cfj9, 4);
					cfj9.a(zd, fu6, this, fu, false);
				} else {
					zd.a(fu, cfj.a(a, Integer.valueOf(integer8 + 1)), 4);
				}
			}
		}
	}

	@Override
	public dfg c(cfj cfj, bpg bpg, fu fu, der der) {
		return b;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return c;
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (!cfj1.a(bqc, fu5)) {
			bqc.G().a(fu5, this, 1);
		}

		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		for (fz fz6 : fz.c.HORIZONTAL) {
			cfj cfj7 = bqd.d_(fu.a(fz6));
			cxd cxd8 = cfj7.c();
			if (cxd8.b() || bqd.b(fu.a(fz6)).a(acz.b)) {
				return false;
			}
		}

		cfj cfj5 = bqd.d_(fu.c());
		return (cfj5.a(bvs.cF) || cfj5.a(bvs.C) || cfj5.a(bvs.D)) && !bqd.d_(fu.b()).c().a();
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, aom aom) {
		aom.a(anw.j, 1.0F);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bvz.a);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
