import java.util.Random;

public class caf extends bvr {
	public static final cga a = cai.a;

	public caf(cfi.c c) {
		super(c);
		this.j(this.n().a(a, Boolean.valueOf(false)));
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, bec bec) {
		d(cfj, bqb, fu);
		super.a(cfj, bqb, fu, bec);
	}

	@Override
	public void a(bqb bqb, fu fu, aom aom) {
		d(bqb.d_(fu), bqb, fu);
		super.a(bqb, fu, aom);
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			a(bqb, fu);
		} else {
			d(cfj, bqb, fu);
		}

		bki bki8 = bec.b(anf);
		return bki8.b() instanceof bim && new bin(bec, anf, bki8, deh).b() ? ang.PASS : ang.SUCCESS;
	}

	private static void d(cfj cfj, bqb bqb, fu fu) {
		a(bqb, fu);
		if (!(Boolean)cfj.c(a)) {
			bqb.a(fu, cfj.a(a, Boolean.valueOf(true)), 3);
		}
	}

	@Override
	public boolean a_(cfj cfj) {
		return (Boolean)cfj.c(a);
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		if ((Boolean)cfj.c(a)) {
			zd.a(fu, cfj.a(a, Boolean.valueOf(false)), 3);
		}
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, bki bki) {
		super.a(cfj, bqb, fu, bki);
		if (bny.a(boa.u, bki) == 0) {
			int integer6 = 1 + bqb.t.nextInt(5);
			this.a(bqb, fu, integer6);
		}
	}

	private static void a(bqb bqb, fu fu) {
		double double3 = 0.5625;
		Random random5 = bqb.t;

		for (fz fz9 : fz.values()) {
			fu fu10 = fu.a(fz9);
			if (!bqb.d_(fu10).i(bqb, fu10)) {
				fz.a a11 = fz9.n();
				double double12 = a11 == fz.a.X ? 0.5 + 0.5625 * (double)fz9.i() : (double)random5.nextFloat();
				double double14 = a11 == fz.a.Y ? 0.5 + 0.5625 * (double)fz9.j() : (double)random5.nextFloat();
				double double16 = a11 == fz.a.Z ? 0.5 + 0.5625 * (double)fz9.k() : (double)random5.nextFloat();
				bqb.a(hd.a, (double)fu.u() + double12, (double)fu.v() + double14, (double)fu.w() + double16, 0.0, 0.0, 0.0);
			}
		}
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(caf.a);
	}
}
