public class bxu extends byp {
	public static final cga a = cfz.u;
	public static final cga b = cfz.w;
	public static final cga c = cfz.q;
	protected static final dfg d = bvr.a(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
	protected static final dfg e = bvr.a(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
	protected static final dfg f = bvr.a(0.0, 0.0, 6.0, 16.0, 13.0, 10.0);
	protected static final dfg g = bvr.a(6.0, 0.0, 0.0, 10.0, 13.0, 16.0);
	protected static final dfg h = bvr.a(0.0, 0.0, 6.0, 16.0, 24.0, 10.0);
	protected static final dfg i = bvr.a(6.0, 0.0, 0.0, 10.0, 24.0, 16.0);
	protected static final dfg j = dfd.a(bvr.a(0.0, 5.0, 7.0, 2.0, 16.0, 9.0), bvr.a(14.0, 5.0, 7.0, 16.0, 16.0, 9.0));
	protected static final dfg k = dfd.a(bvr.a(7.0, 5.0, 0.0, 9.0, 16.0, 2.0), bvr.a(7.0, 5.0, 14.0, 9.0, 16.0, 16.0));
	protected static final dfg o = dfd.a(bvr.a(0.0, 2.0, 7.0, 2.0, 13.0, 9.0), bvr.a(14.0, 2.0, 7.0, 16.0, 13.0, 9.0));
	protected static final dfg p = dfd.a(bvr.a(7.0, 2.0, 0.0, 9.0, 13.0, 2.0), bvr.a(7.0, 2.0, 14.0, 9.0, 13.0, 16.0));

	public bxu(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Boolean.valueOf(false)).a(b, Boolean.valueOf(false)).a(bxu.c, Boolean.valueOf(false)));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		if ((Boolean)cfj.c(c)) {
			return ((fz)cfj.c(aq)).n() == fz.a.X ? g : f;
		} else {
			return ((fz)cfj.c(aq)).n() == fz.a.X ? e : d;
		}
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		fz.a a8 = fz.n();
		if (((fz)cfj1.c(aq)).g().n() != a8) {
			return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
		} else {
			boolean boolean9 = this.h(cfj3) || this.h(bqc.d_(fu5.a(fz.f())));
			return cfj1.a(c, Boolean.valueOf(boolean9));
		}
	}

	@Override
	public dfg c(cfj cfj, bpg bpg, fu fu, der der) {
		if ((Boolean)cfj.c(a)) {
			return dfd.a();
		} else {
			return ((fz)cfj.c(aq)).n() == fz.a.Z ? h : i;
		}
	}

	@Override
	public dfg d(cfj cfj, bpg bpg, fu fu) {
		if ((Boolean)cfj.c(c)) {
			return ((fz)cfj.c(aq)).n() == fz.a.X ? p : o;
		} else {
			return ((fz)cfj.c(aq)).n() == fz.a.X ? k : j;
		}
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		switch (czg) {
			case LAND:
				return (Boolean)cfj.c(a);
			case WATER:
				return false;
			case AIR:
				return (Boolean)cfj.c(a);
			default:
				return false;
		}
	}

	@Override
	public cfj a(bin bin) {
		bqb bqb3 = bin.o();
		fu fu4 = bin.a();
		boolean boolean5 = bqb3.r(fu4);
		fz fz6 = bin.f();
		fz.a a7 = fz6.n();
		boolean boolean8 = a7 == fz.a.Z && (this.h(bqb3.d_(fu4.f())) || this.h(bqb3.d_(fu4.g())))
			|| a7 == fz.a.X && (this.h(bqb3.d_(fu4.d())) || this.h(bqb3.d_(fu4.e())));
		return this.n().a(aq, fz6).a(a, Boolean.valueOf(boolean5)).a(b, Boolean.valueOf(boolean5)).a(c, Boolean.valueOf(boolean8));
	}

	private boolean h(cfj cfj) {
		return cfj.b().a(acx.E);
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if ((Boolean)cfj.c(a)) {
			cfj = cfj.a(a, Boolean.valueOf(false));
			bqb.a(fu, cfj, 10);
		} else {
			fz fz8 = bec.bY();
			if (cfj.c(aq) == fz8.f()) {
				cfj = cfj.a(aq, fz8);
			}

			cfj = cfj.a(a, Boolean.valueOf(true));
			bqb.a(fu, cfj, 10);
		}

		bqb.a(bec, cfj.c(a) ? 1008 : 1014, fu, 0);
		return ang.a(bqb.v);
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		if (!bqb.v) {
			boolean boolean8 = bqb.r(fu3);
			if ((Boolean)cfj.c(b) != boolean8) {
				bqb.a(fu3, cfj.a(b, Boolean.valueOf(boolean8)).a(a, Boolean.valueOf(boolean8)), 2);
				if ((Boolean)cfj.c(a) != boolean8) {
					bqb.a(null, boolean8 ? 1008 : 1014, fu3, 0);
				}
			}
		}
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(aq, bxu.a, b, c);
	}

	public static boolean a(cfj cfj, fz fz) {
		return ((fz)cfj.c(aq)).n() == fz.g().n();
	}
}
