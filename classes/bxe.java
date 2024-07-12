import javax.annotation.Nullable;

public class bxe extends bvr {
	public static final cgd a = byp.aq;
	public static final cga b = cfz.u;
	public static final cgg<cge> c = cfz.aH;
	public static final cga d = cfz.w;
	public static final cgg<cgf> e = cfz.aa;
	protected static final dfg f = bvr.a(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
	protected static final dfg g = bvr.a(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
	protected static final dfg h = bvr.a(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
	protected static final dfg i = bvr.a(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);

	protected bxe(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.NORTH).a(b, Boolean.valueOf(false)).a(bxe.c, cge.LEFT).a(d, Boolean.valueOf(false)).a(e, cgf.LOWER));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		fz fz6 = cfj.c(a);
		boolean boolean7 = !(Boolean)cfj.c(b);
		boolean boolean8 = cfj.c(c) == cge.RIGHT;
		switch (fz6) {
			case EAST:
			default:
				return boolean7 ? i : (boolean8 ? g : f);
			case SOUTH:
				return boolean7 ? f : (boolean8 ? i : h);
			case WEST:
				return boolean7 ? h : (boolean8 ? f : g);
			case NORTH:
				return boolean7 ? g : (boolean8 ? h : i);
		}
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		cgf cgf8 = cfj1.c(e);
		if (fz.n() != fz.a.Y || cgf8 == cgf.LOWER != (fz == fz.UP)) {
			return cgf8 == cgf.LOWER && fz == fz.DOWN && !cfj1.a(bqc, fu5) ? bvs.a.n() : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
		} else {
			return cfj3.a(this) && cfj3.c(e) != cgf8 ? cfj1.a(a, cfj3.c(a)).a(b, cfj3.c(b)).a(c, cfj3.c(c)).a(d, cfj3.c(d)) : bvs.a.n();
		}
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, bec bec) {
		if (!bqb.v && bec.b_()) {
			bxg.b(bqb, fu, cfj, bec);
		}

		super.a(bqb, fu, cfj, bec);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		switch (czg) {
			case LAND:
				return (Boolean)cfj.c(b);
			case WATER:
				return false;
			case AIR:
				return (Boolean)cfj.c(b);
			default:
				return false;
		}
	}

	private int c() {
		return this.as == cxd.I ? 1011 : 1012;
	}

	private int d() {
		return this.as == cxd.I ? 1005 : 1006;
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		fu fu3 = bin.a();
		if (fu3.v() < 255 && bin.o().d_(fu3.b()).a(bin)) {
			bqb bqb4 = bin.o();
			boolean boolean5 = bqb4.r(fu3) || bqb4.r(fu3.b());
			return this.n().a(a, bin.f()).a(c, this.b(bin)).a(d, Boolean.valueOf(boolean5)).a(b, Boolean.valueOf(boolean5)).a(e, cgf.LOWER);
		} else {
			return null;
		}
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, aoy aoy, bki bki) {
		bqb.a(fu.b(), cfj.a(e, cgf.UPPER), 3);
	}

	private cge b(bin bin) {
		bpg bpg3 = bin.o();
		fu fu4 = bin.a();
		fz fz5 = bin.f();
		fu fu6 = fu4.b();
		fz fz7 = fz5.h();
		fu fu8 = fu4.a(fz7);
		cfj cfj9 = bpg3.d_(fu8);
		fu fu10 = fu6.a(fz7);
		cfj cfj11 = bpg3.d_(fu10);
		fz fz12 = fz5.g();
		fu fu13 = fu4.a(fz12);
		cfj cfj14 = bpg3.d_(fu13);
		fu fu15 = fu6.a(fz12);
		cfj cfj16 = bpg3.d_(fu15);
		int integer17 = (cfj9.r(bpg3, fu8) ? -1 : 0) + (cfj11.r(bpg3, fu10) ? -1 : 0) + (cfj14.r(bpg3, fu13) ? 1 : 0) + (cfj16.r(bpg3, fu15) ? 1 : 0);
		boolean boolean18 = cfj9.a(this) && cfj9.c(e) == cgf.LOWER;
		boolean boolean19 = cfj14.a(this) && cfj14.c(e) == cgf.LOWER;
		if ((!boolean18 || boolean19) && integer17 <= 0) {
			if ((!boolean19 || boolean18) && integer17 >= 0) {
				int integer20 = fz5.i();
				int integer21 = fz5.k();
				dem dem22 = bin.j();
				double double23 = dem22.b - (double)fu4.u();
				double double25 = dem22.d - (double)fu4.w();
				return (integer20 >= 0 || !(double25 < 0.5))
						&& (integer20 <= 0 || !(double25 > 0.5))
						&& (integer21 >= 0 || !(double23 > 0.5))
						&& (integer21 <= 0 || !(double23 < 0.5))
					? cge.LEFT
					: cge.RIGHT;
			} else {
				return cge.LEFT;
			}
		} else {
			return cge.RIGHT;
		}
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (this.as == cxd.I) {
			return ang.PASS;
		} else {
			cfj = cfj.a(b);
			bqb.a(fu, cfj, 10);
			bqb.a(bec, cfj.c(b) ? this.d() : this.c(), fu, 0);
			return ang.a(bqb.v);
		}
	}

	public void a(bqb bqb, fu fu, boolean boolean3) {
		cfj cfj5 = bqb.d_(fu);
		if (cfj5.a(this) && (Boolean)cfj5.c(b) != boolean3) {
			bqb.a(fu, cfj5.a(b, Boolean.valueOf(boolean3)), 10);
			this.b(bqb, fu, boolean3);
		}
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		boolean boolean8 = bqb.r(fu3) || bqb.r(fu3.a(cfj.c(e) == cgf.LOWER ? fz.UP : fz.DOWN));
		if (bvr != this && boolean8 != (Boolean)cfj.c(d)) {
			if (boolean8 != (Boolean)cfj.c(b)) {
				this.b(bqb, fu3, boolean8);
			}

			bqb.a(fu3, cfj.a(d, Boolean.valueOf(boolean8)).a(b, Boolean.valueOf(boolean8)), 2);
		}
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		fu fu5 = fu.c();
		cfj cfj6 = bqd.d_(fu5);
		return cfj.c(e) == cgf.LOWER ? cfj6.d(bqd, fu5, fz.UP) : cfj6.a(this);
	}

	private void b(bqb bqb, fu fu, boolean boolean3) {
		bqb.a(null, boolean3 ? this.d() : this.c(), fu, 0);
	}

	@Override
	public cxf f(cfj cfj) {
		return cxf.DESTROY;
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a(a, cap.a(cfj.c(a)));
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		return bzj == bzj.NONE ? cfj : cfj.a(bzj.a(cfj.c(a))).a(c);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(e, bxe.a, b, c, d);
	}

	public static boolean a(bqb bqb, fu fu) {
		return h(bqb.d_(fu));
	}

	public static boolean h(cfj cfj) {
		return cfj.b() instanceof bxe && (cfj.c() == cxd.x || cfj.c() == cxd.y);
	}
}
