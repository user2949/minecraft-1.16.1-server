public class bex extends beg {
	private int f = 200;

	public bex(aoq<? extends bex> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bex(bqb bqb, aoy aoy) {
		super(aoq.aA, aoy, bqb);
	}

	public bex(bqb bqb, double double2, double double3, double double4) {
		super(aoq.aA, double2, double3, double4, bqb);
	}

	@Override
	public void j() {
		super.j();
		if (this.l.v && !this.b) {
			this.l.a(hh.H, this.cC(), this.cD(), this.cG(), 0.0, 0.0, 0.0);
		}
	}

	@Override
	protected bki m() {
		return new bki(bkk.qj);
	}

	@Override
	protected void a(aoy aoy) {
		super.a(aoy);
		aog aog3 = new aog(aoi.x, this.f, 0);
		aoy.c(aog3);
	}

	@Override
	public void a(le le) {
		super.a(le);
		if (le.e("Duration")) {
			this.f = le.h("Duration");
		}
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("Duration", this.f);
	}
}
