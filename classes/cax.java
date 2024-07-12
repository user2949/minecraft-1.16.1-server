public interface cax extends bvw, bzf {
	@Override
	default boolean a(bpg bpg, fu fu, cfj cfj, cwz cwz) {
		return !(Boolean)cfj.c(cfz.C) && cwz == cxb.c;
	}

	@Override
	default boolean a(bqc bqc, fu fu, cfj cfj, cxa cxa) {
		if (!(Boolean)cfj.c(cfz.C) && cxa.a() == cxb.c) {
			if (!bqc.s_()) {
				bqc.a(fu, cfj.a(cfz.C, Boolean.valueOf(true)), 3);
				bqc.F().a(fu, cxa.a(), cxa.a().a(bqc));
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	default cwz b(bqc bqc, fu fu, cfj cfj) {
		if ((Boolean)cfj.c(cfz.C)) {
			bqc.a(fu, cfj.a(cfz.C, Boolean.valueOf(false)), 3);
			return cxb.c;
		} else {
			return cxb.a;
		}
	}
}
