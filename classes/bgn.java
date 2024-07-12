public class bgn extends bgi {
	private final amz c;
	private final bgr d;
	private final bhw e;

	public bgn(int integer, beb beb) {
		this(integer, beb, new anm(5), new bhv(2));
	}

	public bgn(int integer, beb beb, amz amz, bgr bgr) {
		super(bhk.k, integer);
		a(amz, 5);
		a(bgr, 2);
		this.c = amz;
		this.d = bgr;
		this.a(new bgn.c(amz, 0, 56, 51));
		this.a(new bgn.c(amz, 1, 79, 58));
		this.a(new bgn.c(amz, 2, 102, 51));
		this.e = this.a(new bgn.b(amz, 3, 79, 17));
		this.a(new bgn.a(amz, 4, 17, 17));
		this.a(bgr);

		for (int integer6 = 0; integer6 < 3; integer6++) {
			for (int integer7 = 0; integer7 < 9; integer7++) {
				this.a(new bhw(beb, integer7 + integer6 * 9 + 9, 8 + integer7 * 18, 84 + integer6 * 18));
			}
		}

		for (int integer6 = 0; integer6 < 9; integer6++) {
			this.a(new bhw(beb, integer6, 8 + integer6 * 18, 142));
		}
	}

	@Override
	public boolean a(bec bec) {
		return this.c.a(bec);
	}

	@Override
	public bki b(bec bec, int integer) {
		bki bki4 = bki.b;
		bhw bhw5 = (bhw)this.a.get(integer);
		if (bhw5 != null && bhw5.f()) {
			bki bki6 = bhw5.e();
			bki4 = bki6.i();
			if ((integer < 0 || integer > 2) && integer != 3 && integer != 4) {
				if (bgn.a.a_(bki4)) {
					if (this.a(bki6, 4, 5, false) || this.e.a(bki6) && !this.a(bki6, 3, 4, false)) {
						return bki.b;
					}
				} else if (this.e.a(bki6)) {
					if (!this.a(bki6, 3, 4, false)) {
						return bki.b;
					}
				} else if (bgn.c.b_(bki4) && bki4.E() == 1) {
					if (!this.a(bki6, 0, 3, false)) {
						return bki.b;
					}
				} else if (integer >= 5 && integer < 32) {
					if (!this.a(bki6, 32, 41, false)) {
						return bki.b;
					}
				} else if (integer >= 32 && integer < 41) {
					if (!this.a(bki6, 5, 32, false)) {
						return bki.b;
					}
				} else if (!this.a(bki6, 5, 41, false)) {
					return bki.b;
				}
			} else {
				if (!this.a(bki6, 5, 41, true)) {
					return bki.b;
				}

				bhw5.a(bki6, bki4);
			}

			if (bki6.a()) {
				bhw5.d(bki.b);
			} else {
				bhw5.d();
			}

			if (bki6.E() == bki4.E()) {
				return bki.b;
			}

			bhw5.a(bec, bki6);
		}

		return bki4;
	}

	static class a extends bhw {
		public a(amz amz, int integer2, int integer3, int integer4) {
			super(amz, integer2, integer3, integer4);
		}

		@Override
		public boolean a(bki bki) {
			return a_(bki);
		}

		public static boolean a_(bki bki) {
			return bki.b() == bkk.nz;
		}

		@Override
		public int a() {
			return 64;
		}
	}

	static class b extends bhw {
		public b(amz amz, int integer2, int integer3, int integer4) {
			super(amz, integer2, integer3, integer4);
		}

		@Override
		public boolean a(bki bki) {
			return bmc.a(bki);
		}

		@Override
		public int a() {
			return 64;
		}
	}

	static class c extends bhw {
		public c(amz amz, int integer2, int integer3, int integer4) {
			super(amz, integer2, integer3, integer4);
		}

		@Override
		public boolean a(bki bki) {
			return b_(bki);
		}

		@Override
		public int a() {
			return 1;
		}

		@Override
		public bki a(bec bec, bki bki) {
			bmb bmb4 = bmd.d(bki);
			if (bec instanceof ze) {
				aa.k.a((ze)bec, bmb4);
			}

			super.a(bec, bki);
			return bki;
		}

		public static boolean b_(bki bki) {
			bke bke2 = bki.b();
			return bke2 == bkk.nv || bke2 == bkk.qi || bke2 == bkk.ql || bke2 == bkk.nw;
		}
	}
}
