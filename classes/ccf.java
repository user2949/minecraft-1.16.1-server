import java.util.List;
import java.util.Map;
import java.util.Random;

public class ccf extends bvr {
	public static final cga a = cfz.w;
	public static final cga b = cfz.a;
	public static final cga c = cfz.d;
	public static final cga d = bzv.a;
	public static final cga e = bzv.b;
	public static final cga f = bzv.c;
	public static final cga g = bzv.d;
	private static final Map<fz, cga> j = bww.f;
	protected static final dfg h = bvr.a(0.0, 1.0, 0.0, 16.0, 2.5, 16.0);
	protected static final dfg i = bvr.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
	private final ccg k;

	public ccf(ccg ccg, cfi.c c) {
		super(c);
		this.j(
			this.n
				.b()
				.a(a, Boolean.valueOf(false))
				.a(b, Boolean.valueOf(false))
				.a(ccf.c, Boolean.valueOf(false))
				.a(d, Boolean.valueOf(false))
				.a(e, Boolean.valueOf(false))
				.a(f, Boolean.valueOf(false))
				.a(g, Boolean.valueOf(false))
		);
		this.k = ccg;
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return cfj.c(b) ? h : i;
	}

	@Override
	public cfj a(bin bin) {
		bpg bpg3 = bin.o();
		fu fu4 = bin.a();
		return this.n()
			.a(d, Boolean.valueOf(this.a(bpg3.d_(fu4.d()), fz.NORTH)))
			.a(e, Boolean.valueOf(this.a(bpg3.d_(fu4.g()), fz.EAST)))
			.a(f, Boolean.valueOf(this.a(bpg3.d_(fu4.e()), fz.SOUTH)))
			.a(g, Boolean.valueOf(this.a(bpg3.d_(fu4.f()), fz.WEST)));
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return fz.n().d() ? cfj1.a((cgl)j.get(fz), Boolean.valueOf(this.a(cfj3, fz))) : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public void b(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!cfj4.a(cfj1.b())) {
			this.a(bqb, fu, cfj1);
		}
	}

	@Override
	public void a(cfj cfj1, bqb bqb, fu fu, cfj cfj4, boolean boolean5) {
		if (!boolean5 && !cfj1.a(cfj4.b())) {
			this.a(bqb, fu, cfj1.a(a, Boolean.valueOf(true)));
		}
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj, bec bec) {
		if (!bqb.v && !bec.dC().a() && bec.dC().b() == bkk.ng) {
			bqb.a(fu, cfj.a(c, Boolean.valueOf(true)), 4);
		}

		super.a(bqb, fu, cfj, bec);
	}

	private void a(bqb bqb, fu fu, cfj cfj) {
		for (fz fz8 : new fz[]{fz.SOUTH, fz.WEST}) {
			for (int integer9 = 1; integer9 < 42; integer9++) {
				fu fu10 = fu.a(fz8, integer9);
				cfj cfj11 = bqb.d_(fu10);
				if (cfj11.a(this.k)) {
					if (cfj11.c(ccg.a) == fz8.f()) {
						this.k.a(bqb, fu10, cfj11, false, true, integer9, cfj);
					}
					break;
				}

				if (!cfj11.a(this)) {
					break;
				}
			}
		}
	}

	@Override
	public void a(cfj cfj, bqb bqb, fu fu, aom aom) {
		if (!bqb.v) {
			if (!(Boolean)cfj.c(a)) {
				this.a(bqb, fu);
			}
		}
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		if ((Boolean)zd.d_(fu).c(a)) {
			this.a(zd, fu);
		}
	}

	private void a(bqb bqb, fu fu) {
		cfj cfj4 = bqb.d_(fu);
		boolean boolean5 = (Boolean)cfj4.c(a);
		boolean boolean6 = false;
		List<? extends aom> list7 = bqb.a(null, cfj4.j(bqb, fu).a().a(fu));
		if (!list7.isEmpty()) {
			for (aom aom9 : list7) {
				if (!aom9.bP()) {
					boolean6 = true;
					break;
				}
			}
		}

		if (boolean6 != boolean5) {
			cfj4 = cfj4.a(a, Boolean.valueOf(boolean6));
			bqb.a(fu, cfj4, 3);
			this.a(bqb, fu, cfj4);
		}

		if (boolean6) {
			bqb.G().a(new fu(fu), this, 10);
		}
	}

	public boolean a(cfj cfj, fz fz) {
		bvr bvr4 = cfj.b();
		return bvr4 == this.k ? cfj.c(ccg.a) == fz.f() : bvr4 == this;
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		switch (cap) {
			case CLOCKWISE_180:
				return cfj.a(d, cfj.c(f)).a(e, cfj.c(g)).a(f, cfj.c(d)).a(g, cfj.c(e));
			case COUNTERCLOCKWISE_90:
				return cfj.a(d, cfj.c(e)).a(e, cfj.c(f)).a(f, cfj.c(g)).a(g, cfj.c(d));
			case CLOCKWISE_90:
				return cfj.a(d, cfj.c(g)).a(e, cfj.c(d)).a(f, cfj.c(e)).a(g, cfj.c(f));
			default:
				return cfj;
		}
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		switch (bzj) {
			case LEFT_RIGHT:
				return cfj.a(d, cfj.c(f)).a(f, cfj.c(d));
			case FRONT_BACK:
				return cfj.a(e, cfj.c(g)).a(g, cfj.c(e));
			default:
				return super.a(cfj, bzj);
		}
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(ccf.a, b, c, d, e, g, f);
	}
}
