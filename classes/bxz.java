import java.util.Random;

public class bxz extends byr {
	public static final cgi a = cfz.ag;

	public bxz(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Integer.valueOf(0)));
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		this.a(cfj, zd, fu, random);
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		if ((random.nextInt(3) == 0 || this.a(zd, fu, 4)) && zd.B(fu) > 11 - (Integer)cfj.c(a) - cfj.b(zd, fu) && this.e(cfj, zd, fu)) {
			fu.a a6 = new fu.a();

			for (fz fz10 : fz.values()) {
				a6.a(fu, fz10);
				cfj cfj11 = zd.d_(a6);
				if (cfj11.a(this) && !this.e(cfj11, zd, a6)) {
					zd.j().a(a6, this, aec.a(random, 20, 40));
				}
			}
		} else {
			zd.j().a(fu, this, aec.a(random, 20, 40));
		}
	}

	private boolean e(cfj cfj, bqb bqb, fu fu) {
		int integer5 = (Integer)cfj.c(a);
		if (integer5 < 3) {
			bqb.a(fu, cfj.a(a, Integer.valueOf(integer5 + 1)), 2);
			return false;
		} else {
			this.d(cfj, bqb, fu);
			return true;
		}
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		if (bvr == this && this.a(bqb, fu3, 2)) {
			this.d(cfj, bqb, fu3);
		}

		super.a(cfj, bqb, fu3, bvr, fu5, boolean6);
	}

	private boolean a(bpg bpg, fu fu, int integer) {
		int integer5 = 0;
		fu.a a6 = new fu.a();

		for (fz fz10 : fz.values()) {
			a6.a(fu, fz10);
			if (bpg.d_(a6).a(this)) {
				if (++integer5 >= integer) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bxz.a);
	}
}
