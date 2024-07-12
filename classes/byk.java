import java.util.Random;

public abstract class byk extends byi implements bvt {
	public static final cgi d = cfz.ak;
	private final double e;

	protected byk(cfi.c c, fz fz, dfg dfg, boolean boolean4, double double5) {
		super(c, fz, dfg, boolean4);
		this.e = double5;
		this.j(this.n.b().a(d, Integer.valueOf(0)));
	}

	public cfj a(bqc bqc) {
		return this.n().a(d, Integer.valueOf(bqc.v_().nextInt(25)));
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		if (!cfj.a(zd, fu)) {
			zd.b(fu, true);
		}
	}

	@Override
	public boolean a_(cfj cfj) {
		return (Integer)cfj.c(d) < 25;
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		if ((Integer)cfj.c(d) < 25 && random.nextDouble() < this.e) {
			fu fu6 = fu.a(this.a);
			if (this.h(zd.d_(fu6))) {
				zd.a(fu6, cfj.a(d));
			}
		}
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (fz == this.a.f() && !cfj1.a(bqc, fu5)) {
			bqc.G().a(fu5, this, 1);
		}

		if (fz == this.a && cfj3.a(this)) {
			return this.d().n();
		} else {
			if (this.b) {
				bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
			}

			return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
		}
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(d);
	}

	@Override
	public boolean a(bpg bpg, fu fu, cfj cfj, boolean boolean4) {
		return this.h(bpg.d_(fu.a(this.a)));
	}

	@Override
	public boolean a(bqb bqb, Random random, fu fu, cfj cfj) {
		return true;
	}

	@Override
	public void a(zd zd, Random random, fu fu, cfj cfj) {
		fu fu6 = fu.a(this.a);
		int integer7 = Math.min((Integer)cfj.c(d) + 1, 25);
		int integer8 = this.a(random);

		for (int integer9 = 0; integer9 < integer8 && this.h(zd.d_(fu6)); integer9++) {
			zd.a(fu6, cfj.a(d, Integer.valueOf(integer7)));
			fu6 = fu6.a(this.a);
			integer7 = Math.min(integer7 + 1, 25);
		}
	}

	protected abstract int a(Random random);

	protected abstract boolean h(cfj cfj);

	@Override
	protected byk c() {
		return this;
	}
}
