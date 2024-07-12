public class bgl extends bgi {
	private final amz c = new anm(1) {
		@Override
		public boolean b(int integer, bki bki) {
			return bki.b().a(ada.Z);
		}

		@Override
		public int X_() {
			return 1;
		}
	};
	private final bgl.a d;
	private final bgs e;
	private final bgr f;

	public bgl(int integer, amz amz) {
		this(integer, amz, new bhv(3), bgs.a);
	}

	public bgl(int integer, amz amz, bgr bgr, bgs bgs) {
		super(bhk.i, integer);
		a(bgr, 3);
		this.f = bgr;
		this.e = bgs;
		this.d = new bgl.a(this.c, 0, 136, 110);
		this.a(this.d);
		this.a(bgr);
		int integer6 = 36;
		int integer7 = 137;

		for (int integer8 = 0; integer8 < 3; integer8++) {
			for (int integer9 = 0; integer9 < 9; integer9++) {
				this.a(new bhw(amz, integer9 + integer8 * 9 + 9, 36 + integer9 * 18, 137 + integer8 * 18));
			}
		}

		for (int integer8 = 0; integer8 < 9; integer8++) {
			this.a(new bhw(amz, integer8, 36 + integer8 * 18, 195));
		}
	}

	@Override
	public void b(bec bec) {
		super.b(bec);
		if (!bec.l.v) {
			bki bki3 = this.d.a(this.d.a());
			if (!bki3.a()) {
				bec.a(bki3, false);
			}
		}
	}

	@Override
	public boolean a(bec bec) {
		return a(this.e, bec, bvs.es);
	}

	@Override
	public void a(int integer1, int integer2) {
		super.a(integer1, integer2);
		this.c();
	}

	@Override
	public bki b(bec bec, int integer) {
		bki bki4 = bki.b;
		bhw bhw5 = (bhw)this.a.get(integer);
		if (bhw5 != null && bhw5.f()) {
			bki bki6 = bhw5.e();
			bki4 = bki6.i();
			if (integer == 0) {
				if (!this.a(bki6, 1, 37, true)) {
					return bki.b;
				}

				bhw5.a(bki6, bki4);
			} else if (!this.d.f() && this.d.a(bki6) && bki6.E() == 1) {
				if (!this.a(bki6, 0, 1, false)) {
					return bki.b;
				}
			} else if (integer >= 1 && integer < 28) {
				if (!this.a(bki6, 28, 37, false)) {
					return bki.b;
				}
			} else if (integer >= 28 && integer < 37) {
				if (!this.a(bki6, 1, 28, false)) {
					return bki.b;
				}
			} else if (!this.a(bki6, 1, 37, false)) {
				return bki.b;
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

	public void c(int integer1, int integer2) {
		if (this.d.f()) {
			this.f.a(1, integer1);
			this.f.a(2, integer2);
			this.d.a(1);
		}
	}

	class a extends bhw {
		public a(amz amz, int integer3, int integer4, int integer5) {
			super(amz, integer3, integer4, integer5);
		}

		@Override
		public boolean a(bki bki) {
			return bki.b().a(ada.Z);
		}

		@Override
		public int a() {
			return 1;
		}
	}
}
