public abstract class azj extends apg {
	protected azj(aoq<? extends azj> aoq, bqb bqb) {
		super(aoq, bqb);
		this.a(czb.WATER, 0.0F);
	}

	@Override
	public boolean cL() {
		return true;
	}

	@Override
	public apc dB() {
		return apc.e;
	}

	@Override
	public boolean a(bqd bqd) {
		return bqd.i(this);
	}

	@Override
	public int D() {
		return 120;
	}

	@Override
	protected int d(bec bec) {
		return 1 + this.l.t.nextInt(3);
	}

	protected void a(int integer) {
		if (this.aU() && !this.aD()) {
			this.j(integer - 1);
			if (this.bE() == -20) {
				this.j(0);
				this.a(anw.h, 2.0F);
			}
		} else {
			this.j(300);
		}
	}

	@Override
	public void ad() {
		int integer2 = this.bE();
		super.ad();
		this.a(integer2);
	}

	@Override
	public boolean bU() {
		return false;
	}

	@Override
	public boolean a(bec bec) {
		return false;
	}
}
