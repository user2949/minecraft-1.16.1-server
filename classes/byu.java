import javax.annotation.Nullable;

public class byu extends bvr implements bxp {
	public static final cgg<gb> a = cfz.P;

	protected byu(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, gb.NORTH_UP));
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(byu.a);
	}

	@Override
	public cfj a(cfj cfj, cap cap) {
		return cfj.a(a, cap.a().a(cfj.c(a)));
	}

	@Override
	public cfj a(cfj cfj, bzj bzj) {
		return cfj.a(a, bzj.a().a(cfj.c(a)));
	}

	@Override
	public cfj a(bin bin) {
		fz fz3 = bin.i();
		fz fz4;
		if (fz3.n() == fz.a.Y) {
			fz4 = bin.f().f();
		} else {
			fz4 = fz.UP;
		}

		return this.n().a(a, gb.a(fz3, fz4));
	}

	@Nullable
	@Override
	public cdl a(bpg bpg) {
		return new ceb();
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		cdl cdl8 = bqb.c(fu);
		if (cdl8 instanceof ceb && bec.eV()) {
			bec.a((ceb)cdl8);
			return ang.a(bqb.v);
		} else {
			return ang.PASS;
		}
	}

	public static boolean a(cve.c c1, cve.c c2) {
		fz fz3 = h(c1.b);
		fz fz4 = h(c2.b);
		fz fz5 = l(c1.b);
		fz fz6 = l(c2.b);
		ceb.a a7 = (ceb.a)ceb.a.a(c1.c.l("joint")).orElseGet(() -> fz3.n().d() ? ceb.a.ALIGNED : ceb.a.ROLLABLE);
		boolean boolean8 = a7 == ceb.a.ROLLABLE;
		return fz3 == fz4.f() && (boolean8 || fz5 == fz6) && c1.c.l("target").equals(c2.c.l("name"));
	}

	public static fz h(cfj cfj) {
		return ((gb)cfj.c(a)).b();
	}

	public static fz l(cfj cfj) {
		return ((gb)cfj.c(a)).c();
	}
}
