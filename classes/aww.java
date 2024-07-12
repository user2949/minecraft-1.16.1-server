public class aww extends awu {
	private fu p;

	public aww(aoz aoz, bqb bqb) {
		super(aoz, bqb);
	}

	@Override
	public czf a(fu fu, int integer) {
		this.p = fu;
		return super.a(fu, integer);
	}

	@Override
	public czf a(aom aom, int integer) {
		this.p = aom.cA();
		return super.a(aom, integer);
	}

	@Override
	public boolean a(aom aom, double double2) {
		czf czf5 = this.a(aom, 0);
		if (czf5 != null) {
			return this.a(czf5, double2);
		} else {
			this.p = aom.cA();
			this.d = double2;
			return true;
		}
	}

	@Override
	public void c() {
		if (!this.m()) {
			super.c();
		} else {
			if (this.p != null) {
				if (!this.p.a(this.a.cz(), (double)this.a.cx())
					&& (!(this.a.cD() > (double)this.p.v()) || !new fu((double)this.p.u(), this.a.cD(), (double)this.p.w()).a(this.a.cz(), (double)this.a.cx()))) {
					this.a.u().a((double)this.p.u(), (double)this.p.v(), (double)this.p.w(), this.d);
				} else {
					this.p = null;
				}
			}
		}
	}
}
