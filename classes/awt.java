public class awt extends awv {
	public awt(aoz aoz, bqb bqb) {
		super(aoz, bqb);
	}

	@Override
	protected czh a(int integer) {
		this.o = new czc();
		this.o.a(true);
		return new czh(this.o, integer);
	}

	@Override
	protected boolean a() {
		return this.r() && this.p() || !this.a.bn();
	}

	@Override
	protected dem b() {
		return this.a.cz();
	}

	@Override
	public czf a(aom aom, int integer) {
		return this.a(aom.cA(), integer);
	}

	@Override
	public void c() {
		this.e++;
		if (this.m) {
			this.j();
		}

		if (!this.m()) {
			if (this.a()) {
				this.l();
			} else if (this.c != null && this.c.f() < this.c.e()) {
				dem dem2 = this.c.a(this.a, this.c.f());
				if (aec.c(this.a.cC()) == aec.c(dem2.b) && aec.c(this.a.cD()) == aec.c(dem2.c) && aec.c(this.a.cG()) == aec.c(dem2.d)) {
					this.c.c(this.c.f() + 1);
				}
			}

			qy.a(this.b, this.a, this.c, this.l);
			if (!this.m()) {
				dem dem2 = this.c.a(this.a);
				this.a.u().a(dem2.b, dem2.c, dem2.d, this.d);
			}
		}
	}

	@Override
	protected boolean a(dem dem1, dem dem2, int integer3, int integer4, int integer5) {
		int integer7 = aec.c(dem1.b);
		int integer8 = aec.c(dem1.c);
		int integer9 = aec.c(dem1.d);
		double double10 = dem2.b - dem1.b;
		double double12 = dem2.c - dem1.c;
		double double14 = dem2.d - dem1.d;
		double double16 = double10 * double10 + double12 * double12 + double14 * double14;
		if (double16 < 1.0E-8) {
			return false;
		} else {
			double double18 = 1.0 / Math.sqrt(double16);
			double10 *= double18;
			double12 *= double18;
			double14 *= double18;
			double double20 = 1.0 / Math.abs(double10);
			double double22 = 1.0 / Math.abs(double12);
			double double24 = 1.0 / Math.abs(double14);
			double double26 = (double)integer7 - dem1.b;
			double double28 = (double)integer8 - dem1.c;
			double double30 = (double)integer9 - dem1.d;
			if (double10 >= 0.0) {
				double26++;
			}

			if (double12 >= 0.0) {
				double28++;
			}

			if (double14 >= 0.0) {
				double30++;
			}

			double26 /= double10;
			double28 /= double12;
			double30 /= double14;
			int integer32 = double10 < 0.0 ? -1 : 1;
			int integer33 = double12 < 0.0 ? -1 : 1;
			int integer34 = double14 < 0.0 ? -1 : 1;
			int integer35 = aec.c(dem2.b);
			int integer36 = aec.c(dem2.c);
			int integer37 = aec.c(dem2.d);
			int integer38 = integer35 - integer7;
			int integer39 = integer36 - integer8;
			int integer40 = integer37 - integer9;

			while (integer38 * integer32 > 0 || integer39 * integer33 > 0 || integer40 * integer34 > 0) {
				if (double26 < double30 && double26 <= double28) {
					double26 += double20;
					integer7 += integer32;
					integer38 = integer35 - integer7;
				} else if (double28 < double26 && double28 <= double30) {
					double28 += double22;
					integer8 += integer33;
					integer39 = integer36 - integer8;
				} else {
					double30 += double24;
					integer9 += integer34;
					integer40 = integer37 - integer9;
				}
			}

			return true;
		}
	}

	public void a(boolean boolean1) {
		this.o.b(boolean1);
	}

	public void b(boolean boolean1) {
		this.o.a(boolean1);
	}

	@Override
	public boolean a(fu fu) {
		return this.b.d_(fu).a(this.b, fu, this.a);
	}
}
