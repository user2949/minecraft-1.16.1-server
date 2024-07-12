public class bev extends bem {
	public bev(aoq<? extends bev> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bev(bqb bqb, aoy aoy, double double3, double double4, double double5) {
		super(aoq.ax, aoy, double3, double4, double5, bqb);
	}

	public bev(bqb bqb, double double2, double double3, double double4, double double5, double double6, double double7) {
		super(aoq.ax, double2, double3, double4, double5, double6, double7, bqb);
	}

	@Override
	protected void a(dei dei) {
		super.a(dei);
		if (!this.l.v) {
			aom aom3 = dei.a();
			if (!aom3.az()) {
				aom aom4 = this.v();
				int integer5 = aom3.ag();
				aom3.f(5);
				boolean boolean6 = aom3.a(anw.a((bem)this, aom4), 5.0F);
				if (!boolean6) {
					aom3.g(integer5);
				} else if (aom4 instanceof aoy) {
					this.a((aoy)aom4, aom3);
				}
			}
		}
	}

	@Override
	protected void a(deh deh) {
		super.a(deh);
		if (!this.l.v) {
			aom aom3 = this.v();
			if (aom3 == null || !(aom3 instanceof aoz) || this.l.S().b(bpx.b)) {
				fu fu5 = deh.a().a(deh.b());
				if (this.l.w(fu5)) {
					this.l.a(fu5, bvh.a((bpg)this.l, fu5));
				}
			}
		}
	}

	@Override
	protected void a(dej dej) {
		super.a(dej);
		if (!this.l.v) {
			this.aa();
		}
	}

	@Override
	public boolean aQ() {
		return false;
	}

	@Override
	public boolean a(anw anw, float float2) {
		return false;
	}
}
