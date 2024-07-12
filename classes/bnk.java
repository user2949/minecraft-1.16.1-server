public class bnk extends bmn {
	public bnk(uh uh) {
		super(uh);
	}

	public boolean a(bgu bgu, bqb bqb) {
		if (bgu.g() == 3 && bgu.f() == 3) {
			for (int integer4 = 0; integer4 < bgu.g(); integer4++) {
				for (int integer5 = 0; integer5 < bgu.f(); integer5++) {
					bki bki6 = bgu.a(integer4 + integer5 * bgu.g());
					if (bki6.a()) {
						return false;
					}

					bke bke7 = bki6.b();
					if (integer4 == 1 && integer5 == 1) {
						if (bke7 != bkk.ql) {
							return false;
						}
					} else if (bke7 != bkk.kg) {
						return false;
					}
				}
			}

			return true;
		} else {
			return false;
		}
	}

	public bki a(bgu bgu) {
		bki bki3 = bgu.a(1 + bgu.g());
		if (bki3.b() != bkk.ql) {
			return bki.b;
		} else {
			bki bki4 = new bki(bkk.qk, 8);
			bmd.a(bki4, bmd.d(bki3));
			bmd.a(bki4, bmd.b(bki3));
			return bki4;
		}
	}

	@Override
	public bmw<?> ai_() {
		return bmw.j;
	}
}
