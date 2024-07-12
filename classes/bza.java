import java.util.Random;

public class bza extends bvr {
	public static final cgi a = cfz.an;
	public static final cga b = cfz.v;

	public bza(cfi.c c) {
		super(c);
		this.j(this.n.b().a(a, Integer.valueOf(7)).a(b, Boolean.valueOf(false)));
	}

	@Override
	public dfg e(cfj cfj, bpg bpg, fu fu) {
		return dfd.a();
	}

	@Override
	public boolean a_(cfj cfj) {
		return (Integer)cfj.c(a) == 7 && !(Boolean)cfj.c(b);
	}

	@Override
	public void b(cfj cfj, zd zd, fu fu, Random random) {
		if (!(Boolean)cfj.c(b) && (Integer)cfj.c(a) == 7) {
			c(cfj, zd, fu);
			zd.a(fu, false);
		}
	}

	@Override
	public void a(cfj cfj, zd zd, fu fu, Random random) {
		zd.a(fu, a(cfj, zd, fu), 3);
	}

	@Override
	public int f(cfj cfj, bpg bpg, fu fu) {
		return 1;
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		int integer8 = h(cfj3) + 1;
		if (integer8 != 1 || (Integer)cfj1.c(a) != integer8) {
			bqc.G().a(fu5, this, 1);
		}

		return cfj1;
	}

	private static cfj a(cfj cfj, bqc bqc, fu fu) {
		int integer4 = 7;
		fu.a a5 = new fu.a();

		for (fz fz9 : fz.values()) {
			a5.a(fu, fz9);
			integer4 = Math.min(integer4, h(bqc.d_(a5)) + 1);
			if (integer4 == 1) {
				break;
			}
		}

		return cfj.a(a, Integer.valueOf(integer4));
	}

	private static int h(cfj cfj) {
		if (acx.r.a(cfj.b())) {
			return 0;
		} else {
			return cfj.b() instanceof bza ? (Integer)cfj.c(a) : 7;
		}
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(bza.a, b);
	}

	@Override
	public cfj a(bin bin) {
		return a(this.n().a(b, Boolean.valueOf(true)), bin.o(), bin.a());
	}
}
