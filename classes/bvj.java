public abstract class bvj extends bvr {
	protected static final dfg a = bvr.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
	protected static final dfg b = bvr.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
	private final boolean c;

	public static boolean a(bqb bqb, fu fu) {
		return g(bqb.d_(fu));
	}

	public static boolean g(cfj cfj) {
		return cfj.a(acx.G) && cfj.b() instanceof bvj;
	}

	protected bvj(boolean boolean1, cfi.c c) {
		super(c);
		this.c = boolean1;
	}

	public boolean c() {
		return this.c;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		cgm cgm6 = cfj.a(this) ? cfj.c(this.d()) : null;
		return cgm6 != null && cgm6.c() ? b : a;
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		return c(bqd, fu.c());
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj4.a(cfj1.b())) {
			this.a(cfj1, bqb, fu, boolean5);
		}
	}

	protected cfj a(cfj cfj, bqb bqb, fu fu, boolean boolean4) {
		cfj = this.a(bqb, fu, cfj, true);
		if (this.c) {
			cfj.a(bqb, fu, this, fu, boolean4);
		}

		return cfj;
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		if (!bqb.v) {
			cgm cgm8 = cfj.c(this.d());
			if (a(fu3, bqb, cgm8) && !bqb.w(fu3)) {
				if (!boolean6) {
					c(cfj, bqb, fu3);
				}

				bqb.a(fu3, boolean6);
			} else {
				this.a(cfj, bqb, fu3, bvr);
			}
		}
	}

	private static boolean a(fu fu, bqb bqb, cgm cgm) {
		if (!c((bpg)bqb, fu.c())) {
			return true;
		} else {
			switch (cgm) {
				case ASCENDING_EAST:
					return !c((bpg)bqb, fu.g());
				case ASCENDING_WEST:
					return !c((bpg)bqb, fu.f());
				case ASCENDING_NORTH:
					return !c((bpg)bqb, fu.d());
				case ASCENDING_SOUTH:
					return !c((bpg)bqb, fu.e());
				default:
					return false;
			}
		}
	}

	protected void a(cfj cfj, bqb bqb, fu fu, bvr bvr) {
	}

	protected cfj a(bqb bqb, fu fu, cfj cfj, boolean boolean4) {
		if (bqb.v) {
			return cfj;
		} else {
			cgm cgm6 = cfj.c(this.d());
			return new cae(bqb, fu, cfj).a(bqb.r(fu), boolean4, cgm6).c();
		}
	}

	@Override
	public cxf f(cfj cfj) {
		return cxf.NORMAL;
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!boolean5) {
			super.a(cfj1, bqb, fu, cfj4, boolean5);
			if (((cgm)cfj1.c(this.d())).c()) {
				bqb.b(fu.b(), this);
			}

			if (this.c) {
				bqb.b(fu, this);
				bqb.b(fu.c(), this);
			}
		}
	}

	@Override
	public cfj a(bin bin) {
		cfj cfj3 = super.n();
		fz fz4 = bin.f();
		boolean boolean5 = fz4 == fz.EAST || fz4 == fz.WEST;
		return cfj3.a(this.d(), boolean5 ? cgm.EAST_WEST : cgm.NORTH_SOUTH);
	}

	public abstract cgl<cgm> d();
}
