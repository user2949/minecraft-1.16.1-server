public class beq extends bem {
	public int e = 1;

	public beq(aoq<? extends beq> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public beq(bqb bqb, aoy aoy, double double3, double double4, double double5) {
		super(aoq.N, aoy, double3, double4, double5, bqb);
	}

	@Override
	protected void a(dej dej) {
		super.a(dej);
		if (!this.l.v) {
			boolean boolean3 = this.l.S().b(bpx.b);
			this.l.a(null, this.cC(), this.cD(), this.cG(), (float)this.e, boolean3, boolean3 ? bpt.a.DESTROY : bpt.a.NONE);
			this.aa();
		}
	}

	@Override
	protected void a(dei dei) {
		super.a(dei);
		if (!this.l.v) {
			aom aom3 = dei.a();
			aom aom4 = this.v();
			aom3.a(anw.a((bem)this, aom4), 6.0F);
			if (aom4 instanceof aoy) {
				this.a((aoy)aom4, aom3);
			}
		}
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("ExplosionPower", this.e);
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.c("ExplosionPower", 99)) {
			this.e = le.h("ExplosionPower");
		}
	}
}
