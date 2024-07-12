public class awu extends awv {
	private boolean p;

	public awu(aoz aoz, bqb bqb) {
		super(aoz, bqb);
	}

	@Override
	protected czh a(int integer) {
		this.o = new czl();
		this.o.a(true);
		return new czh(this.o, integer);
	}

	@Override
	protected boolean a() {
		return this.a.aj() || this.p() || this.a.bn();
	}

	@Override
	protected dem b() {
		return new dem(this.a.cC(), (double)this.t(), this.a.cG());
	}

	@Override
	public czf a(fu fu, int integer) {
		if (this.b.d_(fu).g()) {
			fu fu4 = fu.c();

			while (fu4.v() > 0 && this.b.d_(fu4).g()) {
				fu4 = fu4.c();
			}

			if (fu4.v() > 0) {
				return super.a(fu4.b(), integer);
			}

			while (fu4.v() < this.b.I() && this.b.d_(fu4).g()) {
				fu4 = fu4.b();
			}

			fu = fu4;
		}

		if (!this.b.d_(fu).c().b()) {
			return super.a(fu, integer);
		} else {
			fu fu4 = fu.b();

			while (fu4.v() < this.b.I() && this.b.d_(fu4).c().b()) {
				fu4 = fu4.b();
			}

			return super.a(fu4, integer);
		}
	}

	@Override
	public czf a(aom aom, int integer) {
		return this.a(aom.cA(), integer);
	}

	private int t() {
		if (this.a.aA() && this.r()) {
			int integer2 = aec.c(this.a.cD());
			bvr bvr3 = this.b.d_(new fu(this.a.cC(), (double)integer2, this.a.cG())).b();
			int integer4 = 0;

			while (bvr3 == bvs.A) {
				bvr3 = this.b.d_(new fu(this.a.cC(), (double)(++integer2), this.a.cG())).b();
				if (++integer4 > 16) {
					return aec.c(this.a.cD());
				}
			}

			return integer2;
		} else {
			return aec.c(this.a.cD() + 0.5);
		}
	}

	@Override
	protected void E_() {
		super.E_();
		if (this.p) {
			if (this.b.f(new fu(this.a.cC(), this.a.cD() + 0.5, this.a.cG()))) {
				return;
			}

			for (int integer2 = 0; integer2 < this.c.e(); integer2++) {
				czd czd3 = this.c.a(integer2);
				if (this.b.f(new fu(czd3.a, czd3.b, czd3.c))) {
					this.c.b(integer2);
					return;
				}
			}
		}
	}

	@Override
	protected boolean a(dem dem1, dem dem2, int integer3, int integer4, int integer5) {
		int integer7 = aec.c(dem1.b);
		int integer8 = aec.c(dem1.d);
		double double9 = dem2.b - dem1.b;
		double double11 = dem2.d - dem1.d;
		double double13 = double9 * double9 + double11 * double11;
		if (double13 < 1.0E-8) {
			return false;
		} else {
			double double15 = 1.0 / Math.sqrt(double13);
			double9 *= double15;
			double11 *= double15;
			integer3 += 2;
			integer5 += 2;
			if (!this.a(integer7, aec.c(dem1.c), integer8, integer3, integer4, integer5, dem1, double9, double11)) {
				return false;
			} else {
				integer3 -= 2;
				integer5 -= 2;
				double double17 = 1.0 / Math.abs(double9);
				double double19 = 1.0 / Math.abs(double11);
				double double21 = (double)integer7 - dem1.b;
				double double23 = (double)integer8 - dem1.d;
				if (double9 >= 0.0) {
					double21++;
				}

				if (double11 >= 0.0) {
					double23++;
				}

				double21 /= double9;
				double23 /= double11;
				int integer25 = double9 < 0.0 ? -1 : 1;
				int integer26 = double11 < 0.0 ? -1 : 1;
				int integer27 = aec.c(dem2.b);
				int integer28 = aec.c(dem2.d);
				int integer29 = integer27 - integer7;
				int integer30 = integer28 - integer8;

				while (integer29 * integer25 > 0 || integer30 * integer26 > 0) {
					if (double21 < double23) {
						double21 += double17;
						integer7 += integer25;
						integer29 = integer27 - integer7;
					} else {
						double23 += double19;
						integer8 += integer26;
						integer30 = integer28 - integer8;
					}

					if (!this.a(integer7, aec.c(dem1.c), integer8, integer3, integer4, integer5, dem1, double9, double11)) {
						return false;
					}
				}

				return true;
			}
		}
	}

	private boolean a(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, dem dem, double double8, double double9) {
		int integer13 = integer1 - integer4 / 2;
		int integer14 = integer3 - integer6 / 2;
		if (!this.b(integer13, integer2, integer14, integer4, integer5, integer6, dem, double8, double9)) {
			return false;
		} else {
			for (int integer15 = integer13; integer15 < integer13 + integer4; integer15++) {
				for (int integer16 = integer14; integer16 < integer14 + integer6; integer16++) {
					double double17 = (double)integer15 + 0.5 - dem.b;
					double double19 = (double)integer16 + 0.5 - dem.d;
					if (!(double17 * double8 + double19 * double9 < 0.0)) {
						czb czb21 = this.o.a(this.b, integer15, integer2 - 1, integer16, this.a, integer4, integer5, integer6, true, true);
						if (!this.a(czb21)) {
							return false;
						}

						czb21 = this.o.a(this.b, integer15, integer2, integer16, this.a, integer4, integer5, integer6, true, true);
						float float22 = this.a.a(czb21);
						if (float22 < 0.0F || float22 >= 8.0F) {
							return false;
						}

						if (czb21 == czb.DAMAGE_FIRE || czb21 == czb.DANGER_FIRE || czb21 == czb.DAMAGE_OTHER) {
							return false;
						}
					}
				}
			}

			return true;
		}
	}

	protected boolean a(czb czb) {
		if (czb == czb.WATER) {
			return false;
		} else {
			return czb == czb.LAVA ? false : czb != czb.OPEN;
		}
	}

	private boolean b(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, dem dem, double double8, double double9) {
		for (fu fu14 : fu.a(new fu(integer1, integer2, integer3), new fu(integer1 + integer4 - 1, integer2 + integer5 - 1, integer3 + integer6 - 1))) {
			double double15 = (double)fu14.u() + 0.5 - dem.b;
			double double17 = (double)fu14.w() + 0.5 - dem.d;
			if (!(double15 * double8 + double17 * double9 < 0.0) && !this.b.d_(fu14).a(this.b, fu14, czg.LAND)) {
				return false;
			}
		}

		return true;
	}

	public void a(boolean boolean1) {
		this.o.b(boolean1);
	}

	public boolean f() {
		return this.o.c();
	}

	public void c(boolean boolean1) {
		this.p = boolean1;
	}
}
