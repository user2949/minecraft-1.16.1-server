public class bkc extends bke {
	public bkc(bke.a a) {
		super(a);
	}

	@Override
	public bki a(bki bki, bqb bqb, aoy aoy) {
		super.a(bki, bqb, aoy);
		if (aoy instanceof ze) {
			ze ze5 = (ze)aoy;
			aa.z.a(ze5, bki);
			ze5.b(acu.c.b(this));
		}

		if (!bqb.v) {
			aoy.d(aoi.s);
		}

		if (bki.a()) {
			return new bki(bkk.nw);
		} else {
			if (aoy instanceof bec && !((bec)aoy).bJ.d) {
				bki bki5 = new bki(bkk.nw);
				bec bec6 = (bec)aoy;
				if (!bec6.bt.e(bki5)) {
					bec6.a(bki5, false);
				}
			}

			return bki;
		}
	}

	@Override
	public int e_(bki bki) {
		return 40;
	}

	@Override
	public blu d_(bki bki) {
		return blu.DRINK;
	}

	@Override
	public ack ag_() {
		return acl.fP;
	}

	@Override
	public ack af_() {
		return acl.fP;
	}

	@Override
	public anh<bki> a(bqb bqb, bec bec, anf anf) {
		return bkj.a(bqb, bec, anf);
	}
}
