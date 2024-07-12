import javax.annotation.Nullable;

public class caz extends bvr implements cax {
	public static final cgg<cgo> a = cfz.aK;
	public static final cga b = cfz.C;
	protected static final dfg c = bvr.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
	protected static final dfg d = bvr.a(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);

	public caz(cfi.c c) {
		super(c);
		this.j(this.n().a(a, cgo.BOTTOM).a(b, Boolean.valueOf(false)));
	}

	@Override
	public boolean c_(cfj cfj) {
		return cfj.c(a) != cgo.DOUBLE;
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(caz.a, b);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		cgo cgo6 = cfj.c(a);
		switch (cgo6) {
			case DOUBLE:
				return dfd.b();
			case TOP:
				return d;
			default:
				return c;
		}
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		fu fu3 = bin.a();
		cfj cfj4 = bin.o().d_(fu3);
		if (cfj4.a(this)) {
			return cfj4.a(a, cgo.DOUBLE).a(b, Boolean.valueOf(false));
		} else {
			cxa cxa5 = bin.o().b(fu3);
			cfj cfj6 = this.n().a(a, cgo.BOTTOM).a(b, Boolean.valueOf(cxa5.a() == cxb.c));
			fz fz7 = bin.i();
			return fz7 != fz.DOWN && (fz7 == fz.UP || !(bin.j().c - (double)fu3.v() > 0.5)) ? cfj6 : cfj6.a(a, cgo.TOP);
		}
	}

	@Override
	public boolean a(cfj cfj, bin bin) {
		bki bki4 = bin.l();
		cgo cgo5 = cfj.c(a);
		if (cgo5 == cgo.DOUBLE || bki4.b() != this.h()) {
			return false;
		} else if (bin.c()) {
			boolean boolean6 = bin.j().c - (double)bin.a().v() > 0.5;
			fz fz7 = bin.i();
			return cgo5 == cgo.BOTTOM ? fz7 == fz.UP || boolean6 && fz7.n().d() : fz7 == fz.DOWN || !boolean6 && fz7.n().d();
		} else {
			return true;
		}
	}

	@Override
	public cxa d(cfj cfj) {
		return cfj.c(b) ? cxb.c.a(false) : super.d(cfj);
	}

	@Override
	public boolean a(bqc bqc, fu fu, cfj cfj, cxa cxa) {
		return cfj.c(a) != cgo.DOUBLE ? cax.super.a(bqc, fu, cfj, cxa) : false;
	}

	@Override
	public boolean a(bpg bpg, fu fu, cfj cfj, cwz cwz) {
		return cfj.c(a) != cgo.DOUBLE ? cax.super.a(bpg, fu, cfj, cwz) : false;
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if ((Boolean)cfj1.c(b)) {
			bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
		}

		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public boolean a(cfj cfj, bpg bpg, fu fu, czg czg) {
		switch (czg) {
			case LAND:
				return false;
			case WATER:
				return bpg.b(fu).a(acz.a);
			case AIR:
				return false;
			default:
				return false;
		}
	}
}
