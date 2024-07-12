public class bfc extends bey {
	public bfc(aoq<? extends bfc> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bfc(bqb bqb, aoy aoy) {
		super(aoq.aH, aoy, bqb);
	}

	public bfc(bqb bqb, double double2, double double3, double double4) {
		super(aoq.aH, double2, double3, double4, bqb);
	}

	@Override
	protected bke h() {
		return bkk.oQ;
	}

	@Override
	protected float k() {
		return 0.07F;
	}

	@Override
	protected void a(dej dej) {
		super.a(dej);
		if (!this.l.v) {
			this.l.c(2002, this.cA(), bmd.a(bme.b));
			int integer3 = 3 + this.l.t.nextInt(5) + this.l.t.nextInt(5);

			while (integer3 > 0) {
				int integer4 = aos.a(integer3);
				integer3 -= integer4;
				this.l.c(new aos(this.l, this.cC(), this.cD(), this.cG(), integer4));
			}

			this.aa();
		}
	}
}
