public class bew extends bey {
	public bew(aoq<? extends bew> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bew(bqb bqb, aoy aoy) {
		super(aoq.az, aoy, bqb);
	}

	public bew(bqb bqb, double double2, double double3, double double4) {
		super(aoq.az, double2, double3, double4, bqb);
	}

	@Override
	protected bke h() {
		return bkk.lQ;
	}

	@Override
	protected void a(dei dei) {
		super.a(dei);
		aom aom3 = dei.a();
		int integer4 = aom3 instanceof bbl ? 3 : 0;
		aom3.a(anw.b(this, this.v()), (float)integer4);
	}

	@Override
	protected void a(dej dej) {
		super.a(dej);
		if (!this.l.v) {
			this.l.a(this, (byte)3);
			this.aa();
		}
	}
}
