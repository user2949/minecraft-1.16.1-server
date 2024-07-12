import java.util.List;

public class bfz extends bfs implements cdz {
	private boolean d = true;
	private int e = -1;
	private final fu f = fu.b;

	public bfz(aoq<? extends bfz> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	public bfz(bqb bqb, double double2, double double3, double double4) {
		super(aoq.X, double2, double3, double4, bqb);
	}

	@Override
	public bfr.a o() {
		return bfr.a.HOPPER;
	}

	@Override
	public cfj q() {
		return bvs.fy.n();
	}

	@Override
	public int s() {
		return 1;
	}

	@Override
	public int ab_() {
		return 5;
	}

	@Override
	public void a(int integer1, int integer2, int integer3, boolean boolean4) {
		boolean boolean6 = !boolean4;
		if (boolean6 != this.u()) {
			this.o(boolean6);
		}
	}

	public boolean u() {
		return this.d;
	}

	public void o(boolean boolean1) {
		this.d = boolean1;
	}

	@Override
	public bqb v() {
		return this.l;
	}

	@Override
	public double x() {
		return this.cC();
	}

	@Override
	public double z() {
		return this.cD() + 0.5;
	}

	@Override
	public double A() {
		return this.cG();
	}

	@Override
	public void j() {
		super.j();
		if (!this.l.v && this.aU() && this.u()) {
			fu fu2 = this.cA();
			if (fu2.equals(this.f)) {
				this.e--;
			} else {
				this.m(0);
			}

			if (!this.C()) {
				this.m(0);
				if (this.B()) {
					this.m(4);
					this.Z_();
				}
			}
		}
	}

	public boolean B() {
		if (cea.a((cdz)this)) {
			return true;
		} else {
			List<bbg> list2 = this.l.a(bbg.class, this.cb().c(0.25, 0.0, 0.25), aop.a);
			if (!list2.isEmpty()) {
				cea.a(this, (bbg)list2.get(0));
			}

			return false;
		}
	}

	@Override
	public void a(anw anw) {
		super.a(anw);
		if (this.l.S().b(bpx.g)) {
			this.a(bvs.fy);
		}
	}

	@Override
	protected void b(le le) {
		super.b(le);
		le.b("TransferCooldown", this.e);
		le.a("Enabled", this.d);
	}

	@Override
	protected void a(le le) {
		super.a(le);
		this.e = le.h("TransferCooldown");
		this.d = le.e("Enabled") ? le.q("Enabled") : true;
	}

	public void m(int integer) {
		this.e = integer;
	}

	public boolean C() {
		return this.e > 0;
	}

	@Override
	public bgi a(int integer, beb beb) {
		return new bhd(integer, beb, this);
	}
}
