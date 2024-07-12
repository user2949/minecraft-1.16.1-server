import javax.annotation.Nullable;

public class bxq extends byp {
	public static final cgg<cfv> u = cfz.Q;

	protected bxq(cfi.c c) {
		super(c);
	}

	@Override
	public boolean a(cfj cfj, bqd bqd, fu fu) {
		return b(bqd, fu, h(cfj).f());
	}

	public static boolean b(bqd bqd, fu fu, fz fz) {
		fu fu4 = fu.a(fz);
		return bqd.d_(fu4).d(bqd, fu4, fz.f());
	}

	@Nullable
	@Override
	public cfj a(bin bin) {
		for (fz fz6 : bin.e()) {
			cfj cfj7;
			if (fz6.n() == fz.a.Y) {
				cfj7 = this.n().a(u, fz6 == fz.UP ? cfv.CEILING : cfv.FLOOR).a(aq, bin.f());
			} else {
				cfj7 = this.n().a(u, cfv.WALL).a(aq, fz6.f());
			}

			if (cfj7.a((bqd)bin.o(), bin.a())) {
				return cfj7;
			}
		}

		return null;
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return h(cfj1).f() == fz && !cfj1.a(bqc, fu5) ? bvs.a.n() : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	protected static fz h(cfj cfj) {
		switch ((cfv)cfj.c(u)) {
			case CEILING:
				return fz.DOWN;
			case FLOOR:
				return fz.UP;
			default:
				return cfj.c(aq);
		}
	}
}
