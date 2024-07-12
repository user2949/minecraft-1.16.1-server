public class bkp extends bke {
	public bkp(bke.a a) {
		super(a);
	}

	@Override
	public bki a(bki bki, bqb bqb, aoy aoy) {
		if (aoy instanceof ze) {
			ze ze5 = (ze)aoy;
			aa.z.a(ze5, bki);
			ze5.b(acu.c.b(this));
		}

		if (aoy instanceof bec && !((bec)aoy).bJ.d) {
			bki.g(1);
		}

		if (!bqb.v) {
			aoy.df();
		}

		return bki.a() ? new bki(bkk.lK) : bki;
	}

	@Override
	public int e_(bki bki) {
		return 32;
	}

	@Override
	public blu d_(bki bki) {
		return blu.DRINK;
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		return bkj.a(bqb, bec, anf);
	}
}
