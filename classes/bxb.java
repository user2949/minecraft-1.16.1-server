import java.util.Random;

public abstract class bxb extends byp {
	protected static final dfg b = bvr.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
	public static final cga c = cfz.w;

	protected bxb(cfi.c c) {
		super(c);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return b;
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		return c(bqd, fu.c());
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		if (!this.a((bqd)zd, fu, cfj)) {
			boolean boolean6 = (Boolean)cfj.c(c);
			boolean boolean7 = this.a((bqb)zd, fu, cfj);
			if (boolean6 && !boolean7) {
				zd.a(fu, cfj.a(c, Boolean.valueOf(false)), 2);
			} else if (!boolean6) {
				zd.a(fu, cfj.a(c, Boolean.valueOf(true)), 2);
				if (!boolean7) {
					zd.j().a(fu, this, this.g(cfj), bqt.VERY_HIGH);
				}
			}
		}
	}

	@Override
	public int b(cfj cfj, bpg bpg, fu fu, fz fz) {
		return cfj.b(bpg, fu, fz);
	}

	@Override
	public int a(cfj cfj, bpg bpg, fu fu, fz fz) {
		if (!(Boolean)cfj.c(c)) {
			return 0;
		} else {
			return cfj.c(aq) == fz ? this.b(bpg, fu, cfj) : 0;
		}
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		if (cfj.a((bqd)bqb, fu3)) {
			this.c(bqb, fu3, cfj);
		} else {
			cdl cdl8 = this.q() ? bqb.c(fu3) : null;
			a(cfj, bqb, fu3, cdl8);
			bqb.a(fu3, false);

			for (fz fz12 : fz.values()) {
				bqb.b(fu3.a(fz12), this);
			}
		}
	}

	protected void c(bqb bqb, fu fu, cfj cfj) {
		if (!this.a((bqd)bqb, fu, cfj)) {
			boolean boolean5 = (Boolean)cfj.c(c);
			boolean boolean6 = this.a(bqb, fu, cfj);
			if (boolean5 != boolean6 && !bqb.G().b(fu, this)) {
				bqt bqt7 = bqt.HIGH;
				if (this.c((bpg)bqb, fu, cfj)) {
					bqt7 = bqt.EXTREMELY_HIGH;
				} else if (boolean5) {
					bqt7 = bqt.VERY_HIGH;
				}

				bqb.G().a(fu, this, this.g(cfj), bqt7);
			}
		}
	}

	public boolean a(bqd bqd, fu fu, cfj cfj) {
		return false;
	}

	protected boolean a(bqb bqb, fu fu, cfj cfj) {
		return this.b(bqb, fu, cfj) > 0;
	}

	protected int b(bqb bqb, fu fu, cfj cfj) {
		fz fz5 = cfj.c(aq);
		fu fu6 = fu.a(fz5);
		int integer7 = bqb.b(fu6, fz5);
		if (integer7 >= 15) {
			return integer7;
		} else {
			cfj cfj8 = bqb.d_(fu6);
			return Math.max(integer7, cfj8.a(bvs.bS) ? (Integer)cfj8.c(cag.e) : 0);
		}
	}

	protected int b(bqd bqd, fu fu, cfj cfj) {
		fz fz5 = cfj.c(aq);
		fz fz6 = fz5.g();
		fz fz7 = fz5.h();
		return Math.max(this.b(bqd, fu.a(fz6), fz6), this.b(bqd, fu.a(fz7), fz7));
	}

	protected int b(bqd bqd, fu fu, fz fz) {
		cfj cfj5 = bqd.d_(fu);
		if (this.h(cfj5)) {
			if (cfj5.a(bvs.fw)) {
				return 15;
			} else {
				return cfj5.a(bvs.bS) ? (Integer)cfj5.c(cag.e) : bqd.c(fu, fz);
			}
		} else {
			return 0;
		}
	}

	@Override
	public boolean b_(cfj cfj) {
		return true;
	}

	@Override
	public cfj a(bin bin) {
		return this.n().a(aq, bin.f().f());
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, aoy aoy, bki bki) {
		if (this.a(bqb, fu, cfj)) {
			bqb.G().a(fu, this, 1);
		}
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		this.d(bqb, fu, cfj1);
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!boolean5 && !cfj1.a(cfj4.b())) {
			super.a(cfj1, bqb, fu, cfj4, boolean5);
			this.d(bqb, fu, cfj1);
		}
	}

	protected void d(bqb bqb, fu fu, cfj cfj) {
		fz fz5 = cfj.c(aq);
		fu fu6 = fu.a(fz5.f());
		bqb.a(fu6, this, fu);
		bqb.a(fu6, this, fz5);
	}

	protected boolean h(cfj cfj) {
		return cfj.i();
	}

	protected int b(bpg bpg, fu fu, cfj cfj) {
		return 15;
	}

	public static boolean l(cfj cfj) {
		return cfj.b() instanceof bxb;
	}

	public boolean c(bpg bpg, fu fu, cfj cfj) {
		fz fz5 = ((fz)cfj.c(aq)).f();
		cfj cfj6 = bpg.d_(fu.a(fz5));
		return l(cfj6) && cfj6.c(aq) != fz5;
	}

	protected abstract int g(cfj cfj);
}
