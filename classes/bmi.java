public class bmi extends bmn {
	public bmi(uh uh) {
		super(uh);
	}

	public boolean a(bgu bgu, bqb bqb) {
		bje bje4 = null;
		bki bki5 = null;
		bki bki6 = null;

		for (int integer7 = 0; integer7 < bgu.ab_(); integer7++) {
			bki bki8 = bgu.a(integer7);
			bke bke9 = bki8.b();
			if (bke9 instanceof bij) {
				bij bij10 = (bij)bke9;
				if (bje4 == null) {
					bje4 = bij10.b();
				} else if (bje4 != bij10.b()) {
					return false;
				}

				int integer11 = cdc.b(bki8);
				if (integer11 > 6) {
					return false;
				}

				if (integer11 > 0) {
					if (bki5 != null) {
						return false;
					}

					bki5 = bki8;
				} else {
					if (bki6 != null) {
						return false;
					}

					bki6 = bki8;
				}
			}
		}

		return bki5 != null && bki6 != null;
	}

	public bki a(bgu bgu) {
		for (int integer3 = 0; integer3 < bgu.ab_(); integer3++) {
			bki bki4 = bgu.a(integer3);
			if (!bki4.a()) {
				int integer5 = cdc.b(bki4);
				if (integer5 > 0 && integer5 <= 6) {
					bki bki6 = bki4.i();
					bki6.e(1);
					return bki6;
				}
			}
		}

		return bki.b;
	}

	public gi<bki> b(bgu bgu) {
		gi<bki> gi3 = gi.a(bgu.ab_(), bki.b);

		for (int integer4 = 0; integer4 < gi3.size(); integer4++) {
			bki bki5 = bgu.a(integer4);
			if (!bki5.a()) {
				if (bki5.b().p()) {
					gi3.set(integer4, new bki(bki5.b().o()));
				} else if (bki5.n() && cdc.b(bki5) > 0) {
					bki bki6 = bki5.i();
					bki6.e(1);
					gi3.set(integer4, bki6);
				}
			}
		}

		return gi3;
	}

	@Override
	public bmw<?> ai_() {
		return bmw.k;
	}
}
