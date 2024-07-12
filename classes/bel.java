public class bel extends aom {
	private static final tq<bki> b = tt.a(bel.class, ts.g);
	private double c;
	private double d;
	private double e;
	private int f;
	private boolean g;

	public bel(aoq<? extends bel> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bel(bqb bqb, double double2, double double3, double double4) {
		this(aoq.z, bqb);
		this.f = 0;
		this.d(double2, double3, double4);
	}

	public void b(bki bki) {
		if (bki.b() != bkk.nD || bki.n()) {
			this.Y().b(b, v.a(bki.i(), bkix -> bkix.e(1)));
		}
	}

	private bki h() {
		return this.Y().a(b);
	}

	public bki g() {
		bki bki2 = this.h();
		return bki2.a() ? new bki(bkk.nD) : bki2;
	}

	@Override
	protected void e() {
		this.Y().a(b, bki.b);
	}

	public void a(fu fu) {
		double double3 = (double)fu.u();
		int integer5 = fu.v();
		double double6 = (double)fu.w();
		double double8 = double3 - this.cC();
		double double10 = double6 - this.cG();
		float float12 = aec.a(double8 * double8 + double10 * double10);
		if (float12 > 12.0F) {
			this.c = this.cC() + double8 / (double)float12 * 12.0;
			this.e = this.cG() + double10 / (double)float12 * 12.0;
			this.d = this.cD() + 8.0;
		} else {
			this.c = double3;
			this.d = (double)integer5;
			this.e = double6;
		}

		this.f = 0;
		this.g = this.J.nextInt(5) > 0;
	}

	@Override
	public void j() {
		super.j();
		dem dem2 = this.cB();
		double double3 = this.cC() + dem2.b;
		double double5 = this.cD() + dem2.c;
		double double7 = this.cG() + dem2.d;
		float float9 = aec.a(b(dem2));
		this.q = bes.e(this.s, (float)(aec.d(dem2.c, (double)float9) * 180.0F / (float)Math.PI));
		this.p = bes.e(this.r, (float)(aec.d(dem2.b, dem2.d) * 180.0F / (float)Math.PI));
		if (!this.l.v) {
			double double10 = this.c - double3;
			double double12 = this.e - double7;
			float float14 = (float)Math.sqrt(double10 * double10 + double12 * double12);
			float float15 = (float)aec.d(double12, double10);
			double double16 = aec.d(0.0025, (double)float9, (double)float14);
			double double18 = dem2.c;
			if (float14 < 1.0F) {
				double16 *= 0.8;
				double18 *= 0.8;
			}

			int integer20 = this.cD() < this.d ? 1 : -1;
			dem2 = new dem(Math.cos((double)float15) * double16, double18 + ((double)integer20 - double18) * 0.015F, Math.sin((double)float15) * double16);
			this.e(dem2);
		}

		float float10 = 0.25F;
		if (this.aA()) {
			for (int integer11 = 0; integer11 < 4; integer11++) {
				this.l.a(hh.e, double3 - dem2.b * 0.25, double5 - dem2.c * 0.25, double7 - dem2.d * 0.25, dem2.b, dem2.c, dem2.d);
			}
		} else {
			this.l
				.a(
					hh.Q,
					double3 - dem2.b * 0.25 + this.J.nextDouble() * 0.6 - 0.3,
					double5 - dem2.c * 0.25 - 0.5,
					double7 - dem2.d * 0.25 + this.J.nextDouble() * 0.6 - 0.3,
					dem2.b,
					dem2.c,
					dem2.d
				);
		}

		if (!this.l.v) {
			this.d(double3, double5, double7);
			this.f++;
			if (this.f > 80 && !this.l.v) {
				this.a(acl.dw, 1.0F, 1.0F);
				this.aa();
				if (this.g) {
					this.l.c(new bbg(this.l, this.cC(), this.cD(), this.cG(), this.g()));
				} else {
					this.l.c(2003, this.cA(), 0);
				}
			}
		} else {
			this.n(double3, double5, double7);
		}
	}

	@Override
	public void b(le le) {
		bki bki3 = this.h();
		if (!bki3.a()) {
			le.a("Item", bki3.b(new le()));
		}
	}

	@Override
	public void a(le le) {
		bki bki3 = bki.a(le.p("Item"));
		this.b(bki3);
	}

	@Override
	public float aO() {
		return 1.0F;
	}

	@Override
	public boolean bH() {
		return false;
	}

	@Override
	public ni<?> O() {
		return new nm(this);
	}
}
