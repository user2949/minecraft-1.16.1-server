public class bhe extends bgi {
	private final amz c;
	private final azm d;

	public bhe(int integer, beb beb, amz amz, azm azm) {
		super(null, integer);
		this.c = amz;
		this.d = azm;
		int integer6 = 3;
		amz.c_(beb.e);
		int integer7 = -18;
		this.a(new bhw(amz, 0, 8, 18) {
			@Override
			public boolean a(bki bki) {
				return bki.b() == bkk.lO && !this.f() && azm.M_();
			}
		});
		this.a(new bhw(amz, 1, 8, 36) {
			@Override
			public boolean a(bki bki) {
				return azm.l(bki);
			}

			@Override
			public int a() {
				return 1;
			}
		});
		if (azm instanceof azl && ((azl)azm).eN()) {
			for (int integer8 = 0; integer8 < 3; integer8++) {
				for (int integer9 = 0; integer9 < ((azl)azm).eV(); integer9++) {
					this.a(new bhw(amz, 2 + integer9 + integer8 * ((azl)azm).eV(), 80 + integer9 * 18, 18 + integer8 * 18));
				}
			}
		}

		for (int integer8 = 0; integer8 < 3; integer8++) {
			for (int integer9 = 0; integer9 < 9; integer9++) {
				this.a(new bhw(beb, integer9 + integer8 * 9 + 9, 8 + integer9 * 18, 102 + integer8 * 18 + -18));
			}
		}

		for (int integer8 = 0; integer8 < 9; integer8++) {
			this.a(new bhw(beb, integer8, 8 + integer8 * 18, 142));
		}
	}

	@Override
	public boolean a(bec bec) {
		return this.c.a(bec) && this.d.aU() && this.d.g((aom)bec) < 8.0F;
	}

	@Override
	public bki b(bec bec, int integer) {
		bki bki4 = bki.b;
		bhw bhw5 = (bhw)this.a.get(integer);
		if (bhw5 != null && bhw5.f()) {
			bki bki6 = bhw5.e();
			bki4 = bki6.i();
			int integer7 = this.c.ab_();
			if (integer < integer7) {
				if (!this.a(bki6, integer7, this.a.size(), true)) {
					return bki.b;
				}
			} else if (this.a(1).a(bki6) && !this.a(1).f()) {
				if (!this.a(bki6, 1, 2, false)) {
					return bki.b;
				}
			} else if (this.a(0).a(bki6)) {
				if (!this.a(bki6, 0, 1, false)) {
					return bki.b;
				}
			} else if (integer7 <= 2 || !this.a(bki6, 2, integer7, false)) {
				int integer9 = integer7 + 27;
				int integer11 = integer9 + 9;
				if (integer >= integer9 && integer < integer11) {
					if (!this.a(bki6, integer7, integer9, false)) {
						return bki.b;
					}
				} else if (integer >= integer7 && integer < integer9) {
					if (!this.a(bki6, integer9, integer11, false)) {
						return bki.b;
					}
				} else if (!this.a(bki6, integer9, integer9, false)) {
					return bki.b;
				}

				return bki.b;
			}

			if (bki6.a()) {
				bhw5.d(bki.b);
			} else {
				bhw5.d();
			}
		}

		return bki4;
	}

	@Override
	public void b(bec bec) {
		super.b(bec);
		this.c.b_(bec);
	}
}
