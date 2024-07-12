public class bnc extends bmn {
	public bnc(uh uh) {
		super(uh);
	}

	public boolean a(bgu bgu, bqb bqb) {
		int integer4 = 0;
		int integer5 = 0;

		for (int integer6 = 0; integer6 < bgu.ab_(); integer6++) {
			bki bki7 = bgu.a(integer6);
			if (!bki7.a()) {
				if (bvr.a(bki7.b()) instanceof cav) {
					integer4++;
				} else {
					if (!(bki7.b() instanceof bjf)) {
						return false;
					}

					integer5++;
				}

				if (integer5 > 1 || integer4 > 1) {
					return false;
				}
			}
		}

		return integer4 == 1 && integer5 == 1;
	}

	public bki a(bgu bgu) {
		bki bki3 = bki.b;
		bjf bjf4 = (bjf)bkk.mK;

		for (int integer5 = 0; integer5 < bgu.ab_(); integer5++) {
			bki bki6 = bgu.a(integer5);
			if (!bki6.a()) {
				bke bke7 = bki6.b();
				if (bvr.a(bke7) instanceof cav) {
					bki3 = bki6;
				} else if (bke7 instanceof bjf) {
					bjf4 = (bjf)bke7;
				}
			}
		}

		bki bki5 = cav.b(bjf4.d());
		if (bki3.n()) {
			bki5.c(bki3.o().g());
		}

		return bki5;
	}

	@Override
	public bmw<?> ai_() {
		return bmw.m;
	}
}
