public class bfy extends bfr {
	private static final tq<Boolean> d = tt.a(bfy.class, ts.i);
	private int e;
	public double b;
	public double c;
	private static final bmr f = bmr.a(bkk.kh, bkk.ki);

	public bfy(aoq<? extends bfy> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bfy(bqb bqb, double double2, double double3, double double4) {
		super(aoq.W, bqb, double2, double3, double4);
	}

	@Override
	public bfr.a o() {
		return bfr.a.FURNACE;
	}

	@Override
	protected void e() {
		super.e();
		this.S.a(d, false);
	}

	@Override
	public void j() {
		super.j();
		if (!this.l.s_()) {
			if (this.e > 0) {
				this.e--;
			}

			if (this.e <= 0) {
				this.b = 0.0;
				this.c = 0.0;
			}

			this.o(this.e > 0);
		}

		if (this.u() && this.J.nextInt(4) == 0) {
			this.l.a(hh.L, this.cC(), this.cD() + 0.8, this.cG(), 0.0, 0.0, 0.0);
		}
	}

	@Override
	protected double g() {
		return 0.2;
	}

	@Override
	public void a(anw anw) {
		super.a(anw);
		if (!anw.d() && this.l.S().b(bpx.g)) {
			this.a(bvs.bY);
		}
	}

	@Override
	protected void b(fu fu, cfj cfj) {
		double double4 = 1.0E-4;
		double double6 = 0.001;
		super.b(fu, cfj);
		dem dem8 = this.cB();
		double double9 = b(dem8);
		double double11 = this.b * this.b + this.c * this.c;
		if (double11 > 1.0E-4 && double9 > 0.001) {
			double double13 = (double)aec.a(double9);
			double double15 = (double)aec.a(double11);
			this.b = dem8.b / double13 * double15;
			this.c = dem8.d / double13 * double15;
		}
	}

	@Override
	protected void i() {
		double double2 = this.b * this.b + this.c * this.c;
		if (double2 > 1.0E-7) {
			double2 = (double)aec.a(double2);
			this.b /= double2;
			this.c /= double2;
			this.e(this.cB().d(0.8, 0.0, 0.8).b(this.b, 0.0, this.c));
		} else {
			this.e(this.cB().d(0.98, 0.0, 0.98));
		}

		super.i();
	}

	@Override
	public ang a(bec bec, anf anf) {
		bki bki4 = bec.b(anf);
		if (f.a(bki4) && this.e + 3600 <= 32000) {
			if (!bec.bJ.d) {
				bki4.g(1);
			}

			this.e += 3600;
		}

		if (this.e > 0) {
			this.b = this.cC() - bec.cC();
			this.c = this.cG() - bec.cG();
		}

		return ang.a(this.l.v);
	}

	@Override
	protected void b(le le) {
		super.b(le);
		le.a("PushX", this.b);
		le.a("PushZ", this.c);
		le.a("Fuel", (short)this.e);
	}

	@Override
	protected void a(le le) {
		super.a(le);
		this.b = le.k("PushX");
		this.c = le.k("PushZ");
		this.e = le.g("Fuel");
	}

	protected boolean u() {
		return this.S.a(d);
	}

	protected void o(boolean boolean1) {
		this.S.b(d, boolean1);
	}

	@Override
	public cfj q() {
		return bvs.bY.n().a(byb.a, fz.NORTH).a(byb.b, Boolean.valueOf(this.u()));
	}
}
