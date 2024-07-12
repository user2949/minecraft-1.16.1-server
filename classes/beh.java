public abstract class beh extends bes {
	public double b;
	public double c;
	public double d;

	protected beh(aoq<? extends beh> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public beh(aoq<? extends beh> aoq, double double2, double double3, double double4, double double5, double double6, double double7, bqb bqb) {
		this(aoq, bqb);
		this.b(double2, double3, double4, this.p, this.q);
		this.ac();
		double double16 = (double)aec.a(double5 * double5 + double6 * double6 + double7 * double7);
		if (double16 != 0.0) {
			this.b = double5 / double16 * 0.1;
			this.c = double6 / double16 * 0.1;
			this.d = double7 / double16 * 0.1;
		}
	}

	public beh(aoq<? extends beh> aoq, aoy aoy, double double3, double double4, double double5, bqb bqb) {
		this(aoq, aoy.cC(), aoy.cD(), aoy.cG(), double3, double4, double5, bqb);
		this.b(aoy);
		this.a(aoy.p, aoy.q);
	}

	@Override
	protected void e() {
	}

	@Override
	public void j() {
		aom aom2 = this.v();
		if (this.l.v || (aom2 == null || !aom2.y) && this.l.C(this.cA())) {
			super.j();
			if (this.Y_()) {
				this.f(1);
			}

			dej dej3 = bet.a(this, this::a, bpj.a.COLLIDER);
			if (dej3.c() != dej.a.MISS) {
				this.a(dej3);
			}

			dem dem4 = this.cB();
			double double5 = this.cC() + dem4.b;
			double double7 = this.cD() + dem4.c;
			double double9 = this.cG() + dem4.d;
			bet.a(this, 0.2F);
			float float11 = this.i();
			if (this.aA()) {
				for (int integer12 = 0; integer12 < 4; integer12++) {
					float float13 = 0.25F;
					this.l.a(hh.e, double5 - dem4.b * 0.25, double7 - dem4.c * 0.25, double9 - dem4.d * 0.25, dem4.b, dem4.c, dem4.d);
				}

				float11 = 0.8F;
			}

			this.e(dem4.b(this.b, this.c, this.d).a((double)float11));
			this.l.a(this.h(), double5, double7 + 0.5, double9, 0.0, 0.0, 0.0);
			this.d(double5, double7, double9);
		} else {
			this.aa();
		}
	}

	@Override
	protected boolean a(aom aom) {
		return super.a(aom) && !aom.H;
	}

	protected boolean Y_() {
		return true;
	}

	protected hf h() {
		return hh.S;
	}

	protected float i() {
		return 0.95F;
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.a("power", this.a(new double[]{this.b, this.c, this.d}));
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.c("power", 9)) {
			lk lk3 = le.d("power", 6);
			if (lk3.size() == 3) {
				this.b = lk3.h(0);
				this.c = lk3.h(1);
				this.d = lk3.h(2);
			}
		}
	}

	@Override
	public boolean aQ() {
		return true;
	}

	@Override
	public float bc() {
		return 1.0F;
	}

	@Override
	public boolean a(anw anw, float float2) {
		if (this.b(anw)) {
			return false;
		} else {
			this.aP();
			aom aom4 = anw.k();
			if (aom4 != null) {
				dem dem5 = aom4.bd();
				this.e(dem5);
				this.b = dem5.b * 0.1;
				this.c = dem5.c * 0.1;
				this.d = dem5.d * 0.1;
				this.b(aom4);
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public float aO() {
		return 1.0F;
	}

	@Override
	public ni<?> O() {
		aom aom2 = this.v();
		int integer3 = aom2 == null ? 0 : aom2.V();
		return new nm(this.V(), this.bR(), this.cC(), this.cD(), this.cG(), this.q, this.p, this.U(), integer3, new dem(this.b, this.c, this.d));
	}
}
