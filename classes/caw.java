public abstract class caw extends bvg implements cax {
	public static final cga a = cfz.C;
	protected static final dfg b = bvr.a(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
	private final cgs c;

	protected caw(cfi.c c, cgs cgs) {
		super(c);
		this.c = cgs;
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		if ((Boolean)cfj1.c(a)) {
			bqc.F().a(fu5, cxb.c, cxb.c.a(bqc));
		}

		return super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}

	@Override
	public dfg b(cfj cfj, bpg bpg, fu fu, der der) {
		return b;
	}

	@Override
	public boolean ak_() {
		return true;
	}

	@Override
	public cdl a(bpg bpg) {
		return new ceh();
	}

	@Override
	public ang a(cfj cfj, bqb bqb, fu fu, bec bec, anf anf, deh deh) {
		bki bki8 = bec.b(anf);
		boolean boolean9 = bki8.b() instanceof bjf && bec.bJ.e;
		if (bqb.v) {
			return boolean9 ? ang.SUCCESS : ang.CONSUME;
		} else {
			cdl cdl10 = bqb.c(fu);
			if (cdl10 instanceof ceh) {
				ceh ceh11 = (ceh)cdl10;
				if (boolean9) {
					boolean boolean12 = ceh11.a(((bjf)bki8.b()).d());
					if (boolean12 && !bec.b_()) {
						bki8.g(1);
					}
				}

				return ceh11.b(bec) ? ang.SUCCESS : ang.PASS;
			} else {
				return ang.PASS;
			}
		}
	}

	@Override
	public cxa d(cfj cfj) {
		return cfj.c(a) ? cxb.c.a(false) : super.d(cfj);
	}
}
