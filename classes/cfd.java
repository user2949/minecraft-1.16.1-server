public class cfd extends bxc {
	public static final cgg<cgk> b = cfz.aJ;
	public static final cga c = cfz.x;
	protected static final dfg d = bvr.a(12.0, 0.0, 0.0, 16.0, 16.0, 16.0);
	protected static final dfg e = bvr.a(0.0, 0.0, 0.0, 4.0, 16.0, 16.0);
	protected static final dfg f = bvr.a(0.0, 0.0, 12.0, 16.0, 16.0, 16.0);
	protected static final dfg g = bvr.a(0.0, 0.0, 0.0, 16.0, 16.0, 4.0);
	protected static final dfg h = bvr.a(0.0, 12.0, 0.0, 16.0, 16.0, 16.0);
	protected static final dfg i = bvr.a(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
	protected static final dfg j = bvr.a(6.0, -4.0, 6.0, 10.0, 12.0, 10.0);
	protected static final dfg k = bvr.a(6.0, 4.0, 6.0, 10.0, 20.0, 10.0);
	protected static final dfg o = bvr.a(6.0, 6.0, -4.0, 10.0, 10.0, 12.0);
	protected static final dfg p = bvr.a(6.0, 6.0, 4.0, 10.0, 10.0, 20.0);
	protected static final dfg q = bvr.a(-4.0, 6.0, 6.0, 12.0, 10.0, 10.0);
	protected static final dfg r = bvr.a(4.0, 6.0, 6.0, 20.0, 10.0, 10.0);
	protected static final dfg s = bvr.a(6.0, 0.0, 6.0, 10.0, 12.0, 10.0);
	protected static final dfg t = bvr.a(6.0, 4.0, 6.0, 10.0, 16.0, 10.0);
	protected static final dfg u = bvr.a(6.0, 6.0, 0.0, 10.0, 10.0, 12.0);
	protected static final dfg v = bvr.a(6.0, 6.0, 4.0, 10.0, 10.0, 16.0);
	protected static final dfg w = bvr.a(0.0, 6.0, 6.0, 12.0, 10.0, 10.0);
	protected static final dfg x = bvr.a(4.0, 6.0, 6.0, 16.0, 10.0, 10.0);

	public cfd(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.NORTH).a(b, cgk.DEFAULT).a(cfd.c, Boolean.valueOf(false)));
	}

	private dfg h(cfj cfj) {
		switch ((fz)cfj.c(a)) {
			case DOWN:
			default:
				return i;
			case UP:
				return h;
			case NORTH:
				return g;
			case SOUTH:
				return f;
			case WEST:
				return e;
			case EAST:
				return d;
		}
	}

	@Override
	public boolean c_(cfj cfj) {
		return true;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return dfd.a(this.h(cfj), this.l(cfj));
	}

	private dfg l(cfj cfj) {
		boolean boolean3 = (Boolean)cfj.c(c);
		switch ((fz)cfj.c(a)) {
			case DOWN:
			default:
				return boolean3 ? t : k;
			case UP:
				return boolean3 ? s : j;
			case NORTH:
				return boolean3 ? v : p;
			case SOUTH:
				return boolean3 ? u : o;
			case WEST:
				return boolean3 ? x : r;
			case EAST:
				return boolean3 ? w : q;
		}
	}

	private boolean a(cfj cfj1, cfj cfj2) {
		bvr bvr4 = cfj1.c(b) == cgk.DEFAULT ? bvs.aW : bvs.aP;
		return cfj2.a(bvr4) && (Boolean)cfj2.c(cfc.b) && cfj2.c(a) == cfj1.c(a);
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, bec bec) {
		if (!bqb.v && bec.bJ.d) {
			fu fu6 = fu.a(((fz)cfj.c(a)).f());
			if (this.a(cfj, bqb.d_(fu6))) {
				bqb.b(fu6, false);
			}
		}

		super.a(bqb, fu, cfj, bec);
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj1.a(cfj4.b())) {
			super.a(cfj1, bqb, fu, cfj4, boolean5);
			fu fu7 = fu.a(((fz)cfj1.c(a)).f());
			if (this.a(cfj1, bqb.d_(fu7))) {
				bqb.b(fu7, true);
			}
		}
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return fz.f() == cfj1.c(a) && !cfj1.a(bqc, fu5) ? bvs.a.n() : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		cfj cfj5 = bqd.d_(fu.a(((fz)cfj.c(a)).f()));
		return this.a(cfj, cfj5) || cfj5.a(bvs.bo) && cfj5.c(a) == cfj.c(a);
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		if (cfj.a((bqd)bqb, fu3)) {
			fu fu8 = fu3.a(((fz)cfj.c(a)).f());
			bqb.d_(fu8).a(bqb, fu8, bvr, fu5, false);
		}
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a(a, cap.a(cfj.c(a)));
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		return cfj.a(bzj.a(cfj.c(a)));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cfd.a, b, c);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
