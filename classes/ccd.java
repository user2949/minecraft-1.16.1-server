import javax.annotation.Nullable;

public class ccd extends byp implements cax {
	public static final cga a = cfz.u;
	public static final cgg<cgh> b = cfz.ab;
	public static final cga c = cfz.w;
	public static final cga d = cfz.C;
	protected static final dfg e = bvr.a(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
	protected static final dfg f = bvr.a(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
	protected static final dfg g = bvr.a(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
	protected static final dfg h = bvr.a(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
	protected static final dfg i = bvr.a(0.0, 0.0, 0.0, 16.0, 3.0, 16.0);
	protected static final dfg j = bvr.a(0.0, 13.0, 0.0, 16.0, 16.0, 16.0);

	protected ccd(cfi.c c) {
		super(c);
		this.j(this.n.b().a(aq, fz.NORTH).a(a, Boolean.valueOf(false)).a(b, cgh.BOTTOM).a(ccd.c, Boolean.valueOf(false)).a(d, Boolean.valueOf(false)));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		if (!(Boolean)cfj.c(a)) {
			return cfj.c(b) == cgh.TOP ? j : i;
		} else {
			switch ((fz)cfj.c(aq)) {
				case NORTH:
				default:
					return h;
				case SOUTH:
					return g;
				case WEST:
					return f;
				case EAST:
					return e;
			}
		}
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		switch (czg) {
			case LAND:
				return (Boolean)cfj.c(a);
			case WATER:
				return (Boolean)cfj.c(d);
			case AIR:
				return (Boolean)cfj.c(a);
			default:
				return false;
		}
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (this.as == cxd.I) {
			return ang.PASS;
		} else {
			cfj = cfj.a(a);
			bqb.a(fu, cfj, 2);
			if ((Boolean)cfj.c(d)) {
				bqb.F().a(fu, cxb.c, cxb.c.a(bqb));
			}

			this.a(bec, bqb, fu, (Boolean)cfj.c(a));
			return ang.a(bqb.v);
		}
	}

	protected void a(@Nullable bec bec, bqb bqb, fu fu, boolean boolean4) {
		if (boolean4) {
			int integer6 = this.as == cxd.I ? 1037 : 1007;
			bqb.a(bec, integer6, fu, 0);
		} else {
			int integer6 = this.as == cxd.I ? 1036 : 1013;
			bqb.a(bec, integer6, fu, 0);
		}
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		if (!bqb.v) {
			boolean boolean8 = bqb.r(fu3);
			if (boolean8 != (Boolean)cfj.c(c)) {
				if ((Boolean)cfj.c(a) != boolean8) {
					cfj = cfj.a(a, Boolean.valueOf(boolean8));
					this.a(null, bqb, fu3, boolean8);
				}

				bqb.a(fu3, cfj.a(c, Boolean.valueOf(boolean8)), 2);
				if ((Boolean)cfj.c(d)) {
					bqb.F().a(fu3, cxb.c, cxb.c.a(bqb));
				}
			}
		}
	}

	@Override
	public cfj a(bin bin) {
		cfj cfj3 = this.n();
		cxa cxa4 = bin.o().b(bin.a());
		fz fz5 = bin.i();
		if (!bin.c() && fz5.n().d()) {
			cfj3 = cfj3.a(aq, fz5).a(b, bin.j().c - (double)bin.a().v() > 0.5 ? cgh.TOP : cgh.BOTTOM);
		} else {
			cfj3 = cfj3.a(aq, bin.f().f()).a(b, fz5 == fz.UP ? cgh.BOTTOM : cgh.TOP);
		}

		if (bin.o().r(bin.a())) {
			cfj3 = cfj3.a(a, Boolean.valueOf(true)).a(c, Boolean.valueOf(true));
		}

		return cfj3.a(d, Boolean.valueOf(cxa4.a() == cxb.c));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(aq, ccd.a, b, c, d);
	}

	@Override
	public cxa d(cfj cfj) {
		return cfj.c(d) ? cxb.c.a(false) : super.d(cfj);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if ((Boolean)cfj1.c(d)) {
			bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
		}

		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}
}
