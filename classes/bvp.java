import javax.annotation.Nullable;

public class bvp extends bvg {
	public static final cgd a = byp.aq;
	public static final cgg<cfy> b = cfz.R;
	public static final cga c = cfz.w;
	private static final dfg d = bvr.a(0.0, 0.0, 4.0, 16.0, 16.0, 12.0);
	private static final dfg e = bvr.a(4.0, 0.0, 0.0, 12.0, 16.0, 16.0);
	private static final dfg f = bvr.a(5.0, 6.0, 5.0, 11.0, 13.0, 11.0);
	private static final dfg g = bvr.a(4.0, 4.0, 4.0, 12.0, 6.0, 12.0);
	private static final dfg h = dfd.a(g, f);
	private static final dfg i = dfd.a(h, bvr.a(7.0, 13.0, 0.0, 9.0, 15.0, 16.0));
	private static final dfg j = dfd.a(h, bvr.a(0.0, 13.0, 7.0, 16.0, 15.0, 9.0));
	private static final dfg k = dfd.a(h, bvr.a(0.0, 13.0, 7.0, 13.0, 15.0, 9.0));
	private static final dfg o = dfd.a(h, bvr.a(3.0, 13.0, 7.0, 16.0, 15.0, 9.0));
	private static final dfg p = dfd.a(h, bvr.a(7.0, 13.0, 0.0, 9.0, 15.0, 13.0));
	private static final dfg q = dfd.a(h, bvr.a(7.0, 13.0, 3.0, 9.0, 15.0, 16.0));
	private static final dfg r = dfd.a(h, bvr.a(7.0, 13.0, 7.0, 9.0, 16.0, 9.0));

	public bvp(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.NORTH).a(b, cfy.FLOOR).a(bvp.c, Boolean.valueOf(false)));
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		boolean boolean8 = bqb.r(fu3);
		if (boolean8 != (Boolean)cfj.c(c)) {
			if (boolean8) {
				this.a(bqb, fu3, null);
			}

			bqb.a(fu3, cfj.a(c, Boolean.valueOf(boolean8)), 3);
		}
	}

	@Override
	public void a(bqb bqb, cfj cfj, deh deh, bes bes) {
		aom aom6 = bes.v();
		bec bec7 = aom6 instanceof bec ? (bec)aom6 : null;
		this.a(bqb, cfj, deh, bec7, true);
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		return this.a(bqb, cfj, deh, bec, true) ? ang.a(bqb.v) : ang.PASS;
	}

	public boolean a(bqb bqb, cfj cfj, deh deh, @Nullable bec bec, boolean boolean5) {
		fz fz7 = deh.b();
		fu fu8 = deh.a();
		boolean boolean9 = !boolean5 || this.a(cfj, fz7, deh.e().c - (double)fu8.v());
		if (boolean9) {
			boolean boolean10 = this.a(bqb, fu8, fz7);
			if (boolean10 && bec != null) {
				bec.a(acu.ay);
			}

			return true;
		} else {
			return false;
		}
	}

	private boolean a(cfj cfj, fz fz, double double3) {
		if (fz.n() != fz.a.Y && !(double3 > 0.8124F)) {
			fz fz6 = cfj.c(a);
			cfy cfy7 = cfj.c(b);
			switch (cfy7) {
				case FLOOR:
					return fz6.n() == fz.n();
				case SINGLE_WALL:
				case DOUBLE_WALL:
					return fz6.n() != fz.n();
				case CEILING:
					return true;
				default:
					return false;
			}
		} else {
			return false;
		}
	}

	public boolean a(bqb bqb, fu fu, @Nullable fz fz) {
		cdl cdl5 = bqb.c(fu);
		if (!bqb.v && cdl5 instanceof cdj) {
			if (fz == null) {
				fz = bqb.d_(fu).c(a);
			}

			((cdj)cdl5).a(fz);
			bqb.a(null, fu, acl.aJ, acm.BLOCKS, 2.0F, 1.0F);
			return true;
		} else {
			return false;
		}
	}

	private dfg h(cfj cfj) {
		fz fz3 = cfj.c(a);
		cfy cfy4 = cfj.c(b);
		if (cfy4 == cfy.FLOOR) {
			return fz3 != fz.NORTH && fz3 != fz.SOUTH ? e : d;
		} else if (cfy4 == cfy.CEILING) {
			return r;
		} else if (cfy4 == cfy.DOUBLE_WALL) {
			return fz3 != fz.NORTH && fz3 != fz.SOUTH ? j : i;
		} else if (fz3 == fz.NORTH) {
			return p;
		} else if (fz3 == fz.SOUTH) {
			return q;
		} else {
			return fz3 == fz.EAST ? o : k;
		}
	}

	@Override
	public dfg c(cfj cfj, bpg bpg, fu fu, der der) {
		return this.h(cfj);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return this.h(cfj);
	}

	@Override
	public cak b(cfj cfj) {
		return cak.MODEL;
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		fz fz4 = bin.i();
		fu fu5 = bin.a();
		bqb bqb6 = bin.o();
		fz.a a7 = fz4.n();
		if (a7 == fz.a.Y) {
			cfj cfj3 = this.n().a(b, fz4 == fz.DOWN ? cfy.CEILING : cfy.FLOOR).a(a, bin.f());
			if (cfj3.a((bqd)bin.o(), fu5)) {
				return cfj3;
			}
		} else {
			boolean boolean8 = a7 == fz.a.X && bqb6.d_(fu5.f()).d(bqb6, fu5.f(), fz.EAST) && bqb6.d_(fu5.g()).d(bqb6, fu5.g(), fz.WEST)
				|| a7 == fz.a.Z && bqb6.d_(fu5.d()).d(bqb6, fu5.d(), fz.SOUTH) && bqb6.d_(fu5.e()).d(bqb6, fu5.e(), fz.NORTH);
			cfj cfj3 = this.n().a(a, fz4.f()).a(b, boolean8 ? cfy.DOUBLE_WALL : cfy.SINGLE_WALL);
			if (cfj3.a((bqd)bin.o(), bin.a())) {
				return cfj3;
			}

			boolean boolean9 = bqb6.d_(fu5.c()).d(bqb6, fu5.c(), fz.UP);
			cfj3 = cfj3.a(b, boolean9 ? cfy.FLOOR : cfy.CEILING);
			if (cfj3.a((bqd)bin.o(), bin.a())) {
				return cfj3;
			}
		}

		return null;
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		cfy cfy8 = cfj1.c(b);
		fz fz9 = l(cfj1).f();
		if (fz9 == fz && !cfj1.a(bqc, fu5) && cfy8 != cfy.DOUBLE_WALL) {
			return bvs.a.n();
		} else {
			if (fz.n() == ((fz)cfj1.c(a)).n()) {
				if (cfy8 == cfy.DOUBLE_WALL && !cfj3.d(bqc, fu6, fz)) {
					return cfj1.a(b, cfy.SINGLE_WALL).a(a, fz.f());
				}

				if (cfy8 == cfy.SINGLE_WALL && fz9.f() == fz && cfj3.d(bqc, fu6, cfj1.c(a))) {
					return cfj1.a(b, cfy.DOUBLE_WALL);
				}
			}

			return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
		}
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		fz fz5 = l(cfj).f();
		return fz5 == fz.UP ? bvr.a(bqd, fu.b(), fz.DOWN) : bxq.b(bqd, fu, fz5);
	}

	private static fz l(cfj cfj) {
		switch ((cfy)cfj.c(b)) {
			case FLOOR:
				return fz.UP;
			case CEILING:
				return fz.DOWN;
			default:
				return ((fz)cfj.c(a)).f();
		}
	}

	@Override
	public cxf f(cfj cfj) {
		return cxf.DESTROY;
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bvp.a, b, c);
	}

	@Nullable
	@Override
	public cdl a(bpg bpg) {
		return new cdj();
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
