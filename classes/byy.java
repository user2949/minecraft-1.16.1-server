import javax.annotation.Nullable;

public class byy extends bvr implements cax {
	public static final cgd a = byp.aq;
	public static final cga b = cfz.C;
	protected static final dfg c = bvr.a(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
	protected static final dfg d = bvr.a(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
	protected static final dfg e = bvr.a(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
	protected static final dfg f = bvr.a(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);

	protected byy(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, fz.NORTH).a(b, Boolean.valueOf(false)));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		switch ((fz)cfj.c(a)) {
			case NORTH:
				return f;
			case SOUTH:
				return e;
			case WEST:
				return d;
			case EAST:
			default:
				return c;
		}
	}

	private boolean a(bpg bpg, fu fu, fz fz) {
		cfj cfj5 = bpg.d_(fu);
		return cfj5.d(bpg, fu, fz);
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		fz fz5 = cfj.c(a);
		return this.a(bqd, fu.a(fz5.f()), fz5);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if (fz.f() == cfj1.c(a) && !cfj1.a(bqc, fu5)) {
			return bvs.a.n();
		} else {
			if ((Boolean)cfj1.c(b)) {
				bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
			}

			return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
		}
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		if (!bin.c()) {
			cfj cfj3 = bin.o().d_(bin.a().a(bin.i().f()));
			if (cfj3.a(this) && cfj3.c(a) == bin.i()) {
				return null;
			}
		}

		cfj cfj3 = this.n();
		bqd bqd4 = bin.o();
		fu fu5 = bin.a();
		cxa cxa6 = bin.o().b(bin.a());

		for (fz fz10 : bin.e()) {
			if (fz10.n().d()) {
				cfj3 = cfj3.a(a, fz10.f());
				if (cfj3.a(bqd4, fu5)) {
					return cfj3.a(b, Boolean.valueOf(cxa6.a() == cxb.c));
				}
			}
		}

		return null;
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
		a.a(byy.a, b);
	}

	@Override
	public cxa d(cfj cfj) {
		return cfj.c(b) ? cxb.c.a(false) : super.d(cfj);
	}
}
