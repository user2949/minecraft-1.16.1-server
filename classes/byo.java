public class byo extends bvg {
	public static final cgd a = cfz.N;
	public static final cga b = cfz.f;
	private static final dfg c = bvr.a(0.0, 10.0, 0.0, 16.0, 16.0, 16.0);
	private static final dfg d = bvr.a(4.0, 4.0, 4.0, 12.0, 10.0, 12.0);
	private static final dfg e = dfd.a(d, c);
	private static final dfg f = dfd.a(e, cdz.a, deq.e);
	private static final dfg g = dfd.a(f, bvr.a(6.0, 0.0, 6.0, 10.0, 4.0, 10.0));
	private static final dfg h = dfd.a(f, bvr.a(12.0, 4.0, 6.0, 16.0, 8.0, 10.0));
	private static final dfg i = dfd.a(f, bvr.a(6.0, 4.0, 0.0, 10.0, 8.0, 4.0));
	private static final dfg j = dfd.a(f, bvr.a(6.0, 4.0, 12.0, 10.0, 8.0, 16.0));
	private static final dfg k = dfd.a(f, bvr.a(0.0, 4.0, 6.0, 4.0, 8.0, 10.0));
	private static final dfg o = cdz.a;
	private static final dfg p = dfd.a(cdz.a, bvr.a(12.0, 8.0, 6.0, 16.0, 10.0, 10.0));
	private static final dfg q = dfd.a(cdz.a, bvr.a(6.0, 8.0, 0.0, 10.0, 10.0, 4.0));
	private static final dfg r = dfd.a(cdz.a, bvr.a(6.0, 8.0, 12.0, 10.0, 10.0, 16.0));
	private static final dfg s = dfd.a(cdz.a, bvr.a(0.0, 8.0, 6.0, 4.0, 10.0, 10.0));

	public byo(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.DOWN).a(b, Boolean.valueOf(true)));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		switch ((fz)cfj.c(a)) {
			case DOWN:
				return g;
			case NORTH:
				return i;
			case SOUTH:
				return j;
			case WEST:
				return k;
			case EAST:
				return h;
			default:
				return f;
		}
	}

	@Override
	public dfg a_(cfj cfj, bpg bpg, fu fu) {
		switch ((fz)cfj.c(a)) {
			case DOWN:
				return o;
			case NORTH:
				return q;
			case SOUTH:
				return r;
			case WEST:
				return s;
			case EAST:
				return p;
			default:
				return cdz.a;
		}
	}

	@Override
	public cfj a(bin bin) {
		fz fz3 = bin.i().f();
		return this.n().a(a, fz3.n() == fz.a.Y ? fz.DOWN : fz3).a(b, Boolean.valueOf(true));
	}

	@Override
	public cdl a(bpg bpg) {
		return new cea();
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, aoy aoy, bki bki) {
		if (bki.t()) {
			cdl cdl7 = bqb.c(fu);
			if (cdl7 instanceof cea) {
				((cea)cdl7).a(bki.r());
			}
		}
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj4.a(cfj1.b())) {
			this.a(bqb, fu, cfj1);
		}
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		if (bqb.v) {
			return ang.SUCCESS;
		} else {
			cdl cdl8 = bqb.c(fu);
			if (cdl8 instanceof cea) {
				bec.a((cea)cdl8);
				bec.a(acu.ac);
			}

			return ang.CONSUME;
		}
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu3, bvr bvr, fu fu5, boolean boolean6) {
		this.a(bqb, fu3, cfj);
	}

	private void a(bqb bqb, fu fu, cfj cfj) {
		boolean boolean5 = !bqb.r(fu);
		if (boolean5 != (Boolean)cfj.c(b)) {
			bqb.a(fu, cfj.a(b, Boolean.valueOf(boolean5)), 4);
		}
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj1.a(cfj4.b())) {
			cdl cdl7 = bqb.c(fu);
			if (cdl7 instanceof cea) {
				anc.a(bqb, fu, (cea)cdl7);
				bqb.c(fu, this);
			}

			super.a(cfj1, bqb, fu, cfj4, boolean5);
		}
	}

	@Override
	public cak b(cfj cfj) {
		return cak.MODEL;
	}

	@Override
	public boolean a(cfj cfj) {
		return true;
	}

	@Override
	public int a(cfj cfj, bqb bqb, fu fu) {
		return bgi.a(bqb.c(fu));
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
		a.a(byo.a, b);
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, aom aom) {
		cdl cdl6 = bqb.c(fu);
		if (cdl6 instanceof cea) {
			((cea)cdl6).a(aom);
		}
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
