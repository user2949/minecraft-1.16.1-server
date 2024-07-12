import javax.annotation.Nullable;

public class bbh extends aom {
	private static final tq<Integer> b = tt.a(bbh.class, ts.b);
	@Nullable
	private aoy c;
	private int d = 80;

	public bbh(aoq<? extends bbh> aoq, bqb bqb) {
		super(aoq, bqb);
		this.i = true;
	}

	public bbh(bqb bqb, double double2, double double3, double double4, @Nullable aoy aoy) {
		this(aoq.al, bqb);
		this.d(double2, double3, double4);
		double double10 = bqb.t.nextDouble() * (float) (Math.PI * 2);
		this.m(-Math.sin(double10) * 0.02, 0.2F, -Math.cos(double10) * 0.02);
		this.a(80);
		this.m = double2;
		this.n = double3;
		this.o = double4;
		this.c = aoy;
	}

	@Override
	protected void e() {
		this.S.a(b, 80);
	}

	@Override
	protected boolean ax() {
		return false;
	}

	@Override
	public boolean aQ() {
		return !this.y;
	}

	@Override
	public void j() {
		if (!this.aw()) {
			this.e(this.cB().b(0.0, -0.04, 0.0));
		}

		this.a(apd.SELF, this.cB());
		this.e(this.cB().a(0.98));
		if (this.t) {
			this.e(this.cB().d(0.7, -0.5, 0.7));
		}

		this.d--;
		if (this.d <= 0) {
			this.aa();
			if (!this.l.v) {
				this.k();
			}
		} else {
			this.aG();
			if (this.l.v) {
				this.l.a(hh.S, this.cC(), this.cD() + 0.5, this.cG(), 0.0, 0.0, 0.0);
			}
		}
	}

	private void k() {
		float float2 = 4.0F;
		this.l.a(this, this.cC(), this.e(0.0625), this.cG(), 4.0F, bpt.a.BREAK);
	}

	@Override
	protected void b(le le) {
		le.a("Fuse", (short)this.i());
	}

	@Override
	protected void a(le le) {
		this.a(le.g("Fuse"));
	}

	@Nullable
	public aoy g() {
		return this.c;
	}

	@Override
	protected float a(apj apj, aon aon) {
		return 0.15F;
	}

	public void a(int integer) {
		this.S.b(b, integer);
		this.d = integer;
	}

	@Override
	public void a(tq<?> tq) {
		if (b.equals(tq)) {
			this.d = this.h();
		}
	}

	public int h() {
		return this.S.a(b);
	}

	public int i() {
		return this.d;
	}

	@Override
	public ni<?> O() {
		return new nm(this);
	}
}
