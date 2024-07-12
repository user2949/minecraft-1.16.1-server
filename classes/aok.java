import javax.annotation.Nullable;

public abstract class aok extends apg {
	private static final tq<Boolean> bv = tt.a(aok.class, ts.i);
	protected int b;
	protected int c;
	protected int d;

	protected aok(aoq<? extends aok> aoq, bqb bqb) {
		super(aoq, bqb);
	}

	@Override
	public apo a(bqc bqc, ane ane, apb apb, @Nullable apo apo, @Nullable le le) {
		if (apo == null) {
			apo = new aok.a();
		}

		aok.a a7 = (aok.a)apo;
		if (a7.c() && a7.a() > 0 && this.J.nextFloat() <= a7.d()) {
			this.c_(-24000);
		}

		a7.b();
		return super.a(bqc, ane, apb, apo, le);
	}

	@Nullable
	public abstract aok a(aok aok);

	@Override
	protected void e() {
		super.e();
		this.S.a(bv, false);
	}

	public boolean f() {
		return false;
	}

	public int i() {
		if (this.l.v) {
			return this.S.a(bv) ? -1 : 1;
		} else {
			return this.b;
		}
	}

	public void a(int integer, boolean boolean2) {
		int integer4 = this.i();
		integer4 += integer * 20;
		if (integer4 > 0) {
			integer4 = 0;
		}

		int integer6 = integer4 - integer4;
		this.c_(integer4);
		if (boolean2) {
			this.c += integer6;
			if (this.d == 0) {
				this.d = 40;
			}
		}

		if (this.i() == 0) {
			this.c_(this.c);
		}
	}

	public void a(int integer) {
		this.a(integer, false);
	}

	public void c_(int integer) {
		int integer3 = this.b;
		this.b = integer;
		if (integer3 < 0 && integer >= 0 || integer3 >= 0 && integer < 0) {
			this.S.b(bv, integer < 0);
			this.m();
		}
	}

	@Override
	public void b(le le) {
		super.b(le);
		le.b("Age", this.i());
		le.b("ForcedAge", this.c);
	}

	@Override
	public void a(le le) {
		super.a(le);
		this.c_(le.h("Age"));
		this.c = le.h("ForcedAge");
	}

	@Override
	public void a(tq<?> tq) {
		if (bv.equals(tq)) {
			this.y_();
		}

		super.a(tq);
	}

	@Override
	public void k() {
		super.k();
		if (this.l.v) {
			if (this.d > 0) {
				if (this.d % 4 == 0) {
					this.l.a(hh.E, this.d(1.0), this.cE() + 0.5, this.g(1.0), 0.0, 0.0, 0.0);
				}

				this.d--;
			}
		} else if (this.aU()) {
			int integer2 = this.i();
			if (integer2 < 0) {
				this.c_(++integer2);
			} else if (integer2 > 0) {
				this.c_(--integer2);
			}
		}
	}

	protected void m() {
	}

	@Override
	public boolean x_() {
		return this.i() < 0;
	}

	@Override
	public void a(boolean boolean1) {
		this.c_(boolean1 ? -24000 : 0);
	}

	public static class a implements apo {
		private int a;
		private boolean b = true;
		private float c = 0.05F;

		public int a() {
			return this.a;
		}

		public void b() {
			this.a++;
		}

		public boolean c() {
			return this.b;
		}

		public void a(boolean boolean1) {
			this.b = boolean1;
		}

		public float d() {
			return this.c;
		}

		public void a(float float1) {
			this.c = float1;
		}
	}
}
