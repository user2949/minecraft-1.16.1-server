public class bfv extends bfr {
	public bfv(aoq<?> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bfv(bqb bqb, double double2, double double3, double double4) {
		super(aoq.T, bqb, double2, double3, double4);
	}

	@Override
	public ang a(bec bec, anf anf) {
		if (bec.ep()) {
			return ang.PASS;
		} else if (this.bo()) {
			return ang.PASS;
		} else if (!this.l.v) {
			return bec.m(this) ? ang.CONSUME : ang.PASS;
		} else {
			return ang.SUCCESS;
		}
	}

	@Override
	public void a(int integer1, int integer2, int integer3, boolean boolean4) {
		if (boolean4) {
			if (this.bo()) {
				this.ba();
			}

			if (this.m() == 0) {
				this.d(-this.n());
				this.c(10);
				this.a(50.0F);
				this.aP();
			}
		}
	}

	@Override
	public bfr.a o() {
		return bfr.a.RIDEABLE;
	}
}
