import java.util.Random;

public class cca extends bvr {
	private static final cgi a = cfz.az;

	public cca(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Integer.valueOf(0)));
	}

	@Override
	public void a(bqb bqb, cfj cfj, deh deh, bes bes) {
		int integer6 = a((bqc)bqb, cfj, deh, (aom)bes);
		aom aom7 = bes.v();
		if (aom7 instanceof ze) {
			ze ze8 = (ze)aom7;
			ze8.a(acu.aD);
			aa.L.a(ze8, bes, deh.e(), integer6);
		}
	}

	private static int a(bqc bqc, cfj cfj, deh deh, aom aom) {
		int integer5 = a(deh, deh.e());
		int integer6 = aom instanceof beg ? 20 : 8;
		if (!bqc.G().a(deh.a(), cfj.b())) {
			a(bqc, cfj, integer5, deh.a(), integer6);
		}

		return integer5;
	}

	private static int a(deh deh, dem dem) {
		fz fz3 = deh.b();
		double double4 = Math.abs(aec.h(dem.b) - 0.5);
		double double6 = Math.abs(aec.h(dem.c) - 0.5);
		double double8 = Math.abs(aec.h(dem.d) - 0.5);
		fz.a a12 = fz3.n();
		double double10;
		if (a12 == fz.a.Y) {
			double10 = Math.max(double4, double8);
		} else if (a12 == fz.a.Z) {
			double10 = Math.max(double4, double6);
		} else {
			double10 = Math.max(double6, double8);
		}

		return Math.max(1, aec.f(15.0 * aec.a((0.5 - double10) / 0.5, 0.0, 1.0)));
	}

	private static void a(bqc bqc, cfj cfj, int integer3, fu fu, int integer5) {
		bqc.a(fu, cfj.a(a, Integer.valueOf(integer3)), 3);
		bqc.G().a(fu, cfj.b(), integer5);
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		if ((Integer)cfj.c(a) != 0) {
			zd.a(fu, cfj.a(a, Integer.valueOf(0)), 3);
		}
	}

	@Override
	public int a(cfj cfj, bpg bpg, fu fu, fz fz) {
		return (Integer)cfj.c(a);
	}

	@Override
	public boolean b_(cfj cfj) {
		return true;
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cca.a);
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!bqb.s_() && !cfj1.a(cfj4.b())) {
			if ((Integer)cfj1.c(a) > 0 && !bqb.G().a(fu, this)) {
				bqb.a(fu, cfj1.a(a, Integer.valueOf(0)), 18);
			}
		}
	}
}
