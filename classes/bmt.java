public class bmt extends bmz {
	public bmt(uh uh) {
		super(
			uh,
			"",
			3,
			3,
			gi.a(bmr.a, bmr.a(bkk.mb), bmr.a(bkk.mb), bmr.a(bkk.mb), bmr.a(bkk.mb), bmr.a(bkk.nf), bmr.a(bkk.mb), bmr.a(bkk.mb), bmr.a(bkk.mb), bmr.a(bkk.mb)),
			new bki(bkk.pb)
		);
	}

	@Override
	public boolean a(bgu bgu, bqb bqb) {
		if (!super.a(bgu, bqb)) {
			return false;
		} else {
			bki bki4 = bki.b;

			for (int integer5 = 0; integer5 < bgu.ab_() && bki4.a(); integer5++) {
				bki bki6 = bgu.a(integer5);
				if (bki6.b() == bkk.nf) {
					bki4 = bki6;
				}
			}

			if (bki4.a()) {
				return false;
			} else {
				czv czv5 = bko.b(bki4, bqb);
				if (czv5 == null) {
					return false;
				} else {
					return this.a(czv5) ? false : czv5.f < 4;
				}
			}
		}
	}

	private boolean a(czv czv) {
		if (czv.j != null) {
			for (czs czs4 : czv.j.values()) {
				if (czs4.b() == czs.a.MANSION || czs4.b() == czs.a.MONUMENT) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public bki a(bgu bgu) {
		bki bki3 = bki.b;

		for (int integer4 = 0; integer4 < bgu.ab_() && bki3.a(); integer4++) {
			bki bki5 = bgu.a(integer4);
			if (bki5.b() == bkk.nf) {
				bki3 = bki5;
			}
		}

		bki3 = bki3.i();
		bki3.e(1);
		bki3.p().b("map_scale_direction", 1);
		return bki3;
	}

	@Override
	public boolean ah_() {
		return true;
	}

	@Override
	public bmw<?> ai_() {
		return bmw.f;
	}
}
