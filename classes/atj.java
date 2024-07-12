public class atj extends atm {
	private final int i;
	private final boolean j;

	public atj(aoz aoz, int integer, boolean boolean3) {
		super(aoz);
		this.i = integer;
		this.j = boolean3;
	}

	@Override
	public void a() {
		if (this.h == atm.a.MOVE_TO) {
			this.h = atm.a.WAIT;
			this.a.e(true);
			double double2 = this.b - this.a.cC();
			double double4 = this.c - this.a.cD();
			double double6 = this.d - this.a.cG();
			double double8 = double2 * double2 + double4 * double4 + double6 * double6;
			if (double8 < 2.5000003E-7F) {
				this.a.r(0.0F);
				this.a.q(0.0F);
				return;
			}

			float float10 = (float)(aec.d(double6, double2) * 180.0F / (float)Math.PI) - 90.0F;
			this.a.p = this.a(this.a.p, float10, 90.0F);
			float float11;
			if (this.a.aj()) {
				float11 = (float)(this.e * this.a.b(apx.d));
			} else {
				float11 = (float)(this.e * this.a.b(apx.e));
			}

			this.a.n(float11);
			double double12 = (double)aec.a(double2 * double2 + double6 * double6);
			float float14 = (float)(-(aec.d(double4, double12) * 180.0F / (float)Math.PI));
			this.a.q = this.a(this.a.q, float14, (float)this.i);
			this.a.r(double4 > 0.0 ? float11 : -float11);
		} else {
			if (!this.j) {
				this.a.e(false);
			}

			this.a.r(0.0F);
			this.a.q(0.0F);
		}
	}
}
