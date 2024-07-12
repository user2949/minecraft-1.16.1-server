public class bku extends bke {
	public bku(bke.a a) {
		super(a);
	}

	@Override
	public bki r() {
		return bmd.a(super.r(), bme.b);
	}

	@Override
	public bki a(bki bki, bqb bqb, aoy aoy) {
		bec bec5 = aoy instanceof bec ? (bec)aoy : null;
		if (bec5 instanceof ze) {
			aa.z.a((ze)bec5, bki);
		}

		if (!bqb.v) {
			for (aog aog8 : bmd.a(bki)) {
				if (aog8.a().a()) {
					aog8.a().a(bec5, bec5, aoy, aog8.c(), 1.0);
				} else {
					aoy.c(new aog(aog8));
				}
			}
		}

		if (bec5 != null) {
			bec5.b(acu.c.b(this));
			if (!bec5.bJ.d) {
				bki.g(1);
			}
		}

		if (bec5 == null || !bec5.bJ.d) {
			if (bki.a()) {
				return new bki(bkk.nw);
			}

			if (bec5 != null) {
				bec5.bt.e(new bki(bkk.nw));
			}
		}

		return bki;
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

	@Override
	public String f(bki bki) {
		return bmd.d(bki).b(this.a() + ".effect.");
	}

	@Override
	public boolean e(bki bki) {
		return super.e(bki) || !bmd.a(bki).isEmpty();
	}

	@Override
	public void a(biy biy, gi<bki> gi) {
		if (this.a(biy)) {
			for (bmb bmb5 : gl.an) {
				if (bmb5 != bme.a) {
					gi.add(bmd.a(new bki(this), bmb5));
				}
			}
		}
	}
}
