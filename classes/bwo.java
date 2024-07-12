public class bwo extends bxr {
	private final cfj a;

	public bwo(bvr bvr, cfi.c c) {
		super(c);
		this.a = bvr.n();
	}

	@Override
	public void a(bqb bqb, fu fu, cfj cfj3, cfj cfj4, bbf bbf) {
		if (b(bqb, fu, cfj4)) {
			bqb.a(fu, this.a, 3);
		}
	}

	@Override
	public cfj a(bin bin) {
		bpg bpg3 = bin.o();
		fu fu4 = bin.a();
		cfj cfj5 = bpg3.d_(fu4);
		return b(bpg3, fu4, cfj5) ? this.a : super.a(bin);
	}

	private static boolean b(bpg bpg, fu fu, cfj cfj) {
		return l(cfj) || a(bpg, fu);
	}

	private static boolean a(bpg bpg, fu fu) {
		boolean boolean3 = false;
		fu.a a4 = fu.i();

		for (fz fz8 : fz.values()) {
			cfj cfj9 = bpg.d_(a4);
			if (fz8 != fz.DOWN || l(cfj9)) {
				a4.a(fu, fz8);
				cfj9 = bpg.d_(a4);
				if (l(cfj9) && !cfj9.d(bpg, fu, fz8.f())) {
					boolean3 = true;
					break;
				}
			}
		}

		return boolean3;
	}

	private static boolean l(cfj cfj) {
		return cfj.m().a(acz.a);
	}

	@Override
	public cfj a(cfj cfj1, fz fz, cfj cfj3, bqc bqc, fu fu5, fu fu6) {
		return a(bqc, fu5) ? this.a : super.a(cfj1, fz, cfj3, bqc, fu5, fu6);
	}
}
