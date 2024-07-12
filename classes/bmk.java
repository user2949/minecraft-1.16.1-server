public class bmk extends bmn {
	public bmk(uh uh) {
		super(uh);
	}

	public boolean a(bgu bgu, bqb bqb) {
		int integer4 = 0;
		bki bki5 = bki.b;

		for (int integer6 = 0; integer6 < bgu.ab_(); integer6++) {
			bki bki7 = bgu.a(integer6);
			if (!bki7.a()) {
				if (bki7.b() == bkk.oT) {
					if (!bki5.a()) {
						return false;
					}

					bki5 = bki7;
				} else {
					if (bki7.b() != bkk.oS) {
						return false;
					}

					integer4++;
				}
			}
		}

		return !bki5.a() && bki5.n() && integer4 > 0;
	}

	public bki a(bgu bgu) {
		int integer3 = 0;
		bki bki4 = bki.b;

		for (int integer5 = 0; integer5 < bgu.ab_(); integer5++) {
			bki bki6 = bgu.a(integer5);
			if (!bki6.a()) {
				if (bki6.b() == bkk.oT) {
					if (!bki4.a()) {
						return bki.b;
					}

					bki4 = bki6;
				} else {
					if (bki6.b() != bkk.oS) {
						return bki.b;
					}

					integer3++;
				}
			}
		}

		if (!bki4.a() && bki4.n() && integer3 >= 1 && bma.d(bki4) < 2) {
			bki bki5 = new bki(bkk.oT, integer3);
			le le6 = bki4.o().g();
			le6.b("generation", bma.d(bki4) + 1);
			bki5.c(le6);
			return bki5;
		} else {
			return bki.b;
		}
	}

	public gi<bki> b(bgu bgu) {
		gi<bki> gi3 = gi.a(bgu.ab_(), bki.b);

		for (int integer4 = 0; integer4 < gi3.size(); integer4++) {
			bki bki5 = bgu.a(integer4);
			if (bki5.b().p()) {
				gi3.set(integer4, new bki(bki5.b().o()));
			} else if (bki5.b() instanceof bma) {
				bki bki6 = bki5.i();
				bki6.e(1);
				gi3.set(integer4, bki6);
				break;
			}
		}

		return gi3;
	}

	@Override
	public bmw<?> ai_() {
		return bmw.d;
	}
}
