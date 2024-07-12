public class bzd extends bxq {
	public static final cga a = cfz.w;
	protected static final dfg b = bvr.a(5.0, 4.0, 10.0, 11.0, 12.0, 16.0);
	protected static final dfg c = bvr.a(5.0, 4.0, 0.0, 11.0, 12.0, 6.0);
	protected static final dfg d = bvr.a(10.0, 4.0, 5.0, 16.0, 12.0, 11.0);
	protected static final dfg e = bvr.a(0.0, 4.0, 5.0, 6.0, 12.0, 11.0);
	protected static final dfg f = bvr.a(5.0, 0.0, 4.0, 11.0, 6.0, 12.0);
	protected static final dfg g = bvr.a(4.0, 0.0, 5.0, 12.0, 6.0, 11.0);
	protected static final dfg h = bvr.a(5.0, 10.0, 4.0, 11.0, 16.0, 12.0);
	protected static final dfg i = bvr.a(4.0, 10.0, 5.0, 12.0, 16.0, 11.0);

	protected bzd(cfi.c c) {
		super(c);
		this.j(this.n.b().a(aq, fz.NORTH).a(a, Boolean.valueOf(false)).a(u, cfv.WALL));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		switch ((cfv)cfj.c(u)) {
			case FLOOR:
				switch (((fz)cfj.c(aq)).n()) {
					case X:
						return g;
					case Z:
					default:
						return f;
				}
			case WALL:
				switch ((fz)cfj.c(aq)) {
					case EAST:
						return e;
					case WEST:
						return d;
					case SOUTH:
						return c;
					case NORTH:
					default:
						return b;
				}
			case CEILING:
			default:
				switch (((fz)cfj.c(aq)).n()) {
					case X:
						return i;
					case Z:
					default:
						return h;
				}
		}
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			cfj cfj8 = cfj.a(a);
			if ((Boolean)cfj8.c(a)) {
				a(cfj8, bqb, fu, 1.0F);
			}

			return ang.SUCCESS;
		} else {
			cfj cfj8 = this.d(cfj, bqb, fu);
			float float9 = cfj8.c(a) ? 0.6F : 0.5F;
			bqb.a(null, fu, acl.hb, acm.BLOCKS, 0.3F, float9);
			return ang.CONSUME;
		}
	}

	public cfj d(cfj cfj, bqb bqb, fu fu) {
		cfj = cfj.a(a);
		bqb.a(fu, cfj, 3);
		this.e(cfj, bqb, fu);
		return cfj;
	}

	private static void a(cfj cfj, bqc bqc, fu fu, float float4) {
		fz fz5 = ((fz)cfj.c(aq)).f();
		fz fz6 = h(cfj).f();
		double double7 = (double)fu.u() + 0.5 + 0.1 * (double)fz5.i() + 0.2 * (double)fz6.i();
		double double9 = (double)fu.v() + 0.5 + 0.1 * (double)fz5.j() + 0.2 * (double)fz6.j();
		double double11 = (double)fu.w() + 0.5 + 0.1 * (double)fz5.k() + 0.2 * (double)fz6.k();
		bqc.a(new hd(1.0F, 0.0F, 0.0F, float4), double7, double9, double11, 0.0, 0.0, 0.0);
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!boolean5 && !cfj1.a(cfj4.b())) {
			if ((Boolean)cfj1.c(a)) {
				this.e(cfj1, bqb, fu);
			}

			super.a(cfj1, bqb, fu, cfj4, boolean5);
		}
	}

	@Override
	public int a(cfj cfj, bpg bpg, fu fu, fz fz) {
		return cfj.c(a) ? 15 : 0;
	}

	@Override
	public int b(cfj cfj, bpg bpg, fu fu, fz fz) {
		return cfj.c(a) && h(cfj) == fz ? 15 : 0;
	}

	@Override
	public boolean b_(cfj cfj) {
		return true;
	}

	private void e(cfj cfj, bqb bqb, fu fu) {
		bqb.b(fu, this);
		bqb.b(fu.a(h(cfj).f()), this);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(u, aq, bzd.a);
	}
}
