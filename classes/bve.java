import javax.annotation.Nullable;

public class bve extends bvr implements cax {
	public static final cga b = cfz.C;
	private static final dfg a = bvr.a(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);

	protected bve(cfi.c c) {
		super(c);
		this.j(this.n.b().a(b, Boolean.valueOf(true)));
	}

	protected void a(cfj cfj, bqc bqc, fu fu) {
		if (!c(cfj, bqc, fu)) {
			bqc.G().a(fu, this, 60 + bqc.v_().nextInt(40));
		}
	}

	protected static boolean c(cfj cfj, bpg bpg, fu fu) {
		if ((Boolean)cfj.c(b)) {
			return true;
		} else {
			for (fz fz7 : fz.values()) {
				if (bpg.b(fu.a(fz7)).a(acz.a)) {
					return true;
				}
			}

			return false;
		}
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		cxa cxa3 = bin.o().b(bin.a());
		return this.n().a(b, Boolean.valueOf(cxa3.a(acz.a) && cxa3.e() == 8));
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return a;
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if ((Boolean)cfj1.c(b)) {
			bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
		}

		return fz == fz.DOWN && !this.a(cfj1, (bqd)bqc, fu5) ? bvs.a.n() : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		fu fu5 = fu.c();
		return bqd.d_(fu5).d(bqd, fu5, fz.UP);
	}

	@Override
	protected void a(cfk.a<bvr, cfj> a) {
		a.a(b);
	}

	@Override
	public cxa d(cfj cfj) {
		return cfj.c(b) ? cxb.c.a(false) : super.d(cfj);
	}
}
