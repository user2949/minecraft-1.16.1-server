public class bms extends bmn {
	public bms(uh uh) {
		super(uh);
	}

	public boolean a(bgu bgu, bqb bqb) {
		int integer4 = 0;
		bki bki5 = bki.b;

		for (int integer6 = 0; integer6 < bgu.ab_(); integer6++) {
			bki bki7 = bgu.a(integer6);
			if (!bki7.a()) {
				if (bki7.b() == bkk.nf) {
					if (!bki5.a()) {
						return false;
					}

					bki5 = bki7;
				} else {
					if (bki7.b() != bkk.pb) {
						return false;
					}

					integer4++;
				}
			}
		}

		return !bki5.a() && integer4 > 0;
	}

	public bki a(bgu bgu) {
		int integer3 = 0;
		bki bki4 = bki.b;

		for (int integer5 = 0; integer5 < bgu.ab_(); integer5++) {
			bki bki6 = bgu.a(integer5);
			if (!bki6.a()) {
				if (bki6.b() == bkk.nf) {
					if (!bki4.a()) {
						return bki.b;
					}

					bki4 = bki6;
				} else {
					if (bki6.b() != bkk.pb) {
						return bki.b;
					}

					integer3++;
				}
			}
		}

		if (!bki4.a() && integer3 >= 1) {
			bki bki5 = bki4.i();
			bki5.e(integer3 + 1);
			return bki5;
		} else {
			return bki.b;
		}
	}

	@Override
	public bmw<?> ai_() {
		return bmw.e;
	}
}
