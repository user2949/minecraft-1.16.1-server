public class bkm extends bke {
	public bkm(bke.a a) {
		super(a);
	}

	@Override
	public ang a(blv blv) {
		bqb bqb3 = blv.o();
		fu fu4 = blv.a();
		bvr bvr5 = bqb3.d_(fu4).b();
		if (bvr5.a(acx.L)) {
			bec bec6 = blv.m();
			if (!bqb3.v && bec6 != null) {
				a(bec6, bqb3, fu4);
			}

			return ang.a(bqb3.v);
		} else {
			return ang.PASS;
		}
	}

	public static ang a(bec bec, bqb bqb, fu fu) {
		bbb bbb4 = null;
		boolean boolean5 = false;
		double double6 = 7.0;
		int integer8 = fu.u();
		int integer9 = fu.v();
		int integer10 = fu.w();

		for (aoz aoz13 : bqb.a(
			aoz.class,
			new deg((double)integer8 - 7.0, (double)integer9 - 7.0, (double)integer10 - 7.0, (double)integer8 + 7.0, (double)integer9 + 7.0, (double)integer10 + 7.0)
		)) {
			if (aoz13.eD() == bec) {
				if (bbb4 == null) {
					bbb4 = bbb.a(bqb, fu);
				}

				aoz13.b(bbb4, true);
				boolean5 = true;
			}
		}

		return boolean5 ? ang.SUCCESS : ang.PASS;
	}
}
