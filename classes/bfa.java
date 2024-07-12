public class bfa extends bey {
	public bfa(aoq<? extends bfa> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bfa(bqb bqb, aoy aoy) {
		super(aoq.aF, aoy, bqb);
	}

	public bfa(bqb bqb, double double2, double double3, double double4) {
		super(aoq.aF, double2, double3, double4, bqb);
	}

	@Override
	protected void a(dei dei) {
		super.a(dei);
		dei.a().a(anw.b(this, this.v()), 0.0F);
	}

	@Override
	protected void a(dej dej) {
		super.a(dej);
		if (!this.l.v) {
			if (this.J.nextInt(8) == 0) {
				int integer3 = 1;
				if (this.J.nextInt(32) == 0) {
					integer3 = 4;
				}

				for (int integer4 = 0; integer4 < integer3; integer4++) {
					ayn ayn5 = aoq.j.a(this.l);
					ayn5.c_(-24000);
					ayn5.b(this.cC(), this.cD(), this.cG(), this.p, 0.0F);
					this.l.c(ayn5);
				}
			}

			this.l.a(this, (byte)3);
			this.aa();
		}
	}

	@Override
	protected bke h() {
		return bkk.mg;
	}
}
