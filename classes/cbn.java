import java.util.Random;
import java.util.stream.IntStream;

public class cbn extends bvr implements cax {
	public static final cgd a = byp.aq;
	public static final cgg<cgh> b = cfz.ab;
	public static final cgg<cgp> c = cfz.aL;
	public static final cga d = cfz.C;
	protected static final dfg e = caz.d;
	protected static final dfg f = caz.c;
	protected static final dfg g = bvr.a(0.0, 0.0, 0.0, 8.0, 8.0, 8.0);
	protected static final dfg h = bvr.a(0.0, 0.0, 8.0, 8.0, 8.0, 16.0);
	protected static final dfg i = bvr.a(0.0, 8.0, 0.0, 8.0, 16.0, 8.0);
	protected static final dfg j = bvr.a(0.0, 8.0, 8.0, 8.0, 16.0, 16.0);
	protected static final dfg k = bvr.a(8.0, 0.0, 0.0, 16.0, 8.0, 8.0);
	protected static final dfg o = bvr.a(8.0, 0.0, 8.0, 16.0, 8.0, 16.0);
	protected static final dfg p = bvr.a(8.0, 8.0, 0.0, 16.0, 16.0, 8.0);
	protected static final dfg q = bvr.a(8.0, 8.0, 8.0, 16.0, 16.0, 16.0);
	protected static final dfg[] r = a(e, g, k, h, o);
	protected static final dfg[] s = a(f, i, p, j, q);
	private static final int[] t = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};
	private final bvr u;
	private final cfj v;

	private static dfg[] a(dfg dfg1, dfg dfg2, dfg dfg3, dfg dfg4, dfg dfg5) {
		return (dfg[])IntStream.range(0, 16).mapToObj(integer -> a(integer, dfg1, dfg2, dfg3, dfg4, dfg5)).toArray(dfg[]::new);
	}

	private static dfg a(int integer, dfg dfg2, dfg dfg3, dfg dfg4, dfg dfg5, dfg dfg6) {
		dfg dfg7 = dfg2;
		if ((integer & 1) != 0) {
			dfg7 = dfd.a(dfg2, dfg3);
		}

		if ((integer & 2) != 0) {
			dfg7 = dfd.a(dfg7, dfg4);
		}

		if ((integer & 4) != 0) {
			dfg7 = dfd.a(dfg7, dfg5);
		}

		if ((integer & 8) != 0) {
			dfg7 = dfd.a(dfg7, dfg6);
		}

		return dfg7;
	}

	protected cbn(cfj cfj, cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.NORTH).a(b, cgh.BOTTOM).a(cbn.c, cgp.STRAIGHT).a(d, Boolean.valueOf(false)));
		this.u = cfj.b();
		this.v = cfj;
	}

	@Override
	public boolean c_(cfj cfj) {
		return true;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return (cfj.c(b) == cgh.TOP ? r : s)[t[this.l(cfj)]];
	}

	private int l(cfj cfj) {
		return ((cgp)cfj.c(c)).ordinal() * 4 + ((fz)cfj.c(a)).d();
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, bec bec) {
		this.v.a(bqb, fu, bec);
	}

	@Override
	public void a(bqc bqc, fu fu, cfj cfj) {
		this.u.a(bqc, fu, cfj);
	}

	@Override
	public float f() {
		return this.u.f();
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj1.a(cfj1.b())) {
			this.v.a(bqb, fu, bvs.a, fu, false);
			this.u.b(this.v, bqb, fu, cfj4, false);
		}
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj1.a(cfj4.b())) {
			this.v.b(bqb, fu, cfj4, boolean5);
		}
	}

	@Override
	public void a(bqb bqb, fu fu, aom aom) {
		this.u.a(bqb, fu, aom);
	}

	@Override
	public boolean a_(cfj cfj) {
		return this.u.a_(cfj);
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		this.u.b(cfj, zd, fu, random);
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		this.u.a(cfj, zd, fu, random);
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		return this.v.a(bqb, bec, anf, deh);
	}

	@Override
	public void a(bqb bqb, fu fu, bpt bpt) {
		this.u.a(bqb, fu, bpt);
	}

	@Override
	public cfj a(bin bin) {
		fz fz3 = bin.i();
		fu fu4 = bin.a();
		cxa cxa5 = bin.o().b(fu4);
		cfj cfj6 = this.n()
			.a(a, bin.f())
			.a(b, fz3 != fz.DOWN && (fz3 == fz.UP || !(bin.j().c - (double)fu4.v() > 0.5)) ? cgh.BOTTOM : cgh.TOP)
			.a(d, Boolean.valueOf(cxa5.a() == cxb.c));
		return cfj6.a(c, g(cfj6, bin.o(), fu4));
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if ((Boolean)cfj1.c(d)) {
			bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
		}

		return fz.n().d() ? cfj1.a(c, g(cfj1, bqc, fu5)) : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	private static cgp g(cfj cfj, bpg bpg, fu fu) {
		fz fz4 = cfj.c(a);
		cfj cfj5 = bpg.d_(fu.a(fz4));
		if (h(cfj5) && cfj.c(b) == cfj5.c(b)) {
			fz fz6 = cfj5.c(a);
			if (fz6.n() != ((fz)cfj.c(a)).n() && e(cfj, bpg, fu, fz6.f())) {
				if (fz6 == fz4.h()) {
					return cgp.OUTER_LEFT;
				}

				return cgp.OUTER_RIGHT;
			}
		}

		cfj cfj6 = bpg.d_(fu.a(fz4.f()));
		if (h(cfj6) && cfj.c(b) == cfj6.c(b)) {
			fz fz7 = cfj6.c(a);
			if (fz7.n() != ((fz)cfj.c(a)).n() && e(cfj, bpg, fu, fz7)) {
				if (fz7 == fz4.h()) {
					return cgp.INNER_LEFT;
				}

				return cgp.INNER_RIGHT;
			}
		}

		return cgp.STRAIGHT;
	}

	private static boolean e(cfj cfj, bpg bpg, fu fu, fz fz) {
		cfj cfj5 = bpg.d_(fu.a(fz));
		return !h(cfj5) || cfj5.c(a) != cfj.c(a) || cfj5.c(b) != cfj.c(b);
	}

	public static boolean h(cfj cfj) {
		return cfj.b() instanceof cbn;
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a(a, cap.a(cfj.c(a)));
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		fz fz4 = cfj.c(a);
		cgp cgp5 = cfj.c(c);
		switch (bzj) {
			case LEFT_RIGHT:
				if (fz4.n() == fz.a.Z) {
					switch (cgp5) {
						case INNER_LEFT:
							return cfj.a(cap.CLOCKWISE_180).a(c, cgp.INNER_RIGHT);
						case INNER_RIGHT:
							return cfj.a(cap.CLOCKWISE_180).a(c, cgp.INNER_LEFT);
						case OUTER_LEFT:
							return cfj.a(cap.CLOCKWISE_180).a(c, cgp.OUTER_RIGHT);
						case OUTER_RIGHT:
							return cfj.a(cap.CLOCKWISE_180).a(c, cgp.OUTER_LEFT);
						default:
							return cfj.a(cap.CLOCKWISE_180);
					}
				}
				break;
			case FRONT_BACK:
				if (fz4.n() == fz.a.X) {
					switch (cgp5) {
						case INNER_LEFT:
							return cfj.a(cap.CLOCKWISE_180).a(c, cgp.INNER_LEFT);
						case INNER_RIGHT:
							return cfj.a(cap.CLOCKWISE_180).a(c, cgp.INNER_RIGHT);
						case OUTER_LEFT:
							return cfj.a(cap.CLOCKWISE_180).a(c, cgp.OUTER_RIGHT);
						case OUTER_RIGHT:
							return cfj.a(cap.CLOCKWISE_180).a(c, cgp.OUTER_LEFT);
						case STRAIGHT:
							return cfj.a(cap.CLOCKWISE_180);
					}
				}
		}

		return super.a(cfj, bzj);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(cbn.a, b, c, d);
	}

	@Override
	public cxa d(cfj cfj) {
		return cfj.c(d) ? cxb.c.a(false) : super.d(cfj);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		return false;
	}
}
