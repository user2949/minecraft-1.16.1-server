import java.util.Random;

public class cas extends bvr implements cax {
	private static final dfg d;
	private static final dfg e;
	private static final dfg f = bvr.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
	private static final dfg g = dfd.b().a(0.0, -1.0, 0.0);
	public static final cgi a = cfz.aB;
	public static final cga b = cfz.C;
	public static final cga c = cfz.b;

	protected cas(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Integer.valueOf(7)).a(b, Boolean.valueOf(false)).a(cas.c, Boolean.valueOf(false)));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cas.a, b, c);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		if (!der.a(cfj.b().h())) {
			return cfj.c(c) ? e : d;
		} else {
			return dfd.b();
		}
	}

	@Override
	public dfg a_(cfj cfj, bpg bpg, fu fu) {
		return dfd.b();
	}

	@Override
	public boolean a(cfj cfj, bin bin) {
		return bin.l().b() == this.h();
	}

	@Override
	public cfj a(bin bin) {
		fu fu3 = bin.a();
		bqb bqb4 = bin.o();
		int integer5 = a(bqb4, fu3);
		return this.n().a(b, Boolean.valueOf(bqb4.b(fu3).a() == cxb.c)).a(a, Integer.valueOf(integer5)).a(c, Boolean.valueOf(this.a(bqb4, fu3, integer5)));
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!bqb.v) {
			bqb.G().a(fu, this, 1);
		}
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if ((Boolean)cfj1.c(b)) {
			bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
		}

		if (!bqc.s_()) {
			bqc.G().a(fu5, this, 1);
		}

		return cfj1;
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		int integer6 = a(zd, fu);
		cfj cfj7 = cfj.a(a, Integer.valueOf(integer6)).a(c, Boolean.valueOf(this.a(zd, fu, integer6)));
		if ((Integer)cfj7.c(a) == 7) {
			if ((Integer)cfj.c(a) == 7) {
				zd.c(new bbf(zd, (double)fu.u() + 0.5, (double)fu.v(), (double)fu.w() + 0.5, cfj7.a(b, Boolean.valueOf(false))));
			} else {
				zd.b(fu, true);
			}
		} else if (cfj != cfj7) {
			zd.a(fu, cfj7, 3);
		}
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		return a(bqd, fu) < 7;
	}

	@Override
	public dfg c(cfj cfj, bpg bpg, fu fu, der der) {
		if (der.a(dfd.b(), fu, true) && !der.b()) {
			return d;
		} else {
			return cfj.c(a) != 0 && cfj.c(c) && der.a(g, fu, true) ? f : dfd.a();
		}
	}

	@Override
	public cxa d(cfj cfj) {
		return cfj.c(b) ? cxb.c.a(false) : super.d(cfj);
	}

	private boolean a(bpg bpg, fu fu, int integer) {
		return integer > 0 && !bpg.d_(fu.c()).a(this);
	}

	public static int a(bpg bpg, fu fu) {
		fu.a a3 = fu.i().c(fz.DOWN);
		cfj cfj4 = bpg.d_(a3);
		int integer5 = 7;
		if (cfj4.a(bvs.lQ)) {
			integer5 = (Integer)cfj4.c(a);
		} else if (cfj4.d(bpg, a3, fz.UP)) {
			return 0;
		}

		for (fz fz7 : fz.c.HORIZONTAL) {
			cfj cfj8 = bpg.d_(a3.a(fu, fz7));
			if (cfj8.a(bvs.lQ)) {
				integer5 = Math.min(integer5, (Integer)cfj8.c(a) + 1);
				if (integer5 == 1) {
					break;
				}
			}
		}

		return integer5;
	}

	static {
		dfg dfg1 = bvr.a(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);
		dfg dfg2 = bvr.a(0.0, 0.0, 0.0, 2.0, 16.0, 2.0);
		dfg dfg3 = bvr.a(14.0, 0.0, 0.0, 16.0, 16.0, 2.0);
		dfg dfg4 = bvr.a(0.0, 0.0, 14.0, 2.0, 16.0, 16.0);
		dfg dfg5 = bvr.a(14.0, 0.0, 14.0, 16.0, 16.0, 16.0);
		d = dfd.a(dfg1, dfg2, dfg3, dfg4, dfg5);
		dfg dfg6 = bvr.a(0.0, 0.0, 0.0, 2.0, 2.0, 16.0);
		dfg dfg7 = bvr.a(14.0, 0.0, 0.0, 16.0, 2.0, 16.0);
		dfg dfg8 = bvr.a(0.0, 0.0, 14.0, 16.0, 2.0, 16.0);
		dfg dfg9 = bvr.a(0.0, 0.0, 0.0, 16.0, 2.0, 2.0);
		e = dfd.a(cas.f, d, dfg7, dfg6, dfg9, dfg8);
	}
}
