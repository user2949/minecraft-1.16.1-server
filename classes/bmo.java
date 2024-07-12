public class bmo extends bmn {
	private static final bmr a = bmr.a(bkk.mb);
	private static final bmr b = bmr.a(bkk.kO);
	private static final bmr c = bmr.a(bkk.po);

	public bmo(uh uh) {
		super(uh);
	}

	public boolean a(bgu bgu, bqb bqb) {
		boolean boolean4 = false;
		int integer5 = 0;

		for (int integer6 = 0; integer6 < bgu.ab_(); integer6++) {
			bki bki7 = bgu.a(integer6);
			if (!bki7.a()) {
				if (a.a(bki7)) {
					if (boolean4) {
						return false;
					}

					boolean4 = true;
				} else if (b.a(bki7)) {
					if (++integer5 > 3) {
						return false;
					}
				} else if (!c.a(bki7)) {
					return false;
				}
			}
		}

		return boolean4 && integer5 >= 1;
	}

	public bki a(bgu bgu) {
		bki bki3 = new bki(bkk.pn, 3);
		le le4 = bki3.a("Fireworks");
		lk lk5 = new lk();
		int integer6 = 0;

		for (int integer7 = 0; integer7 < bgu.ab_(); integer7++) {
			bki bki8 = bgu.a(integer7);
			if (!bki8.a()) {
				if (b.a(bki8)) {
					integer6++;
				} else if (c.a(bki8)) {
					le le9 = bki8.b("Explosion");
					if (le9 != null) {
						lk5.add(le9);
					}
				}
			}
		}

		le4.a("Flight", (byte)integer6);
		if (!lk5.isEmpty()) {
			le4.a("Explosions", lk5);
		}

		return bki3;
	}

	@Override
	public bki c() {
		return new bki(bkk.pn);
	}

	@Override
	public bmw<?> ai_() {
		return bmw.g;
	}
}
