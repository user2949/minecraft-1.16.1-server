import java.util.EnumSet;

public class auq extends aug {
	protected final apg a;
	private final double b;
	private final boolean c;
	private czf d;
	private double e;
	private double f;
	private double g;
	private int h;
	private int i;
	private final int j = 20;
	private long k;

	public auq(apg apg, double double2, boolean boolean3) {
		this.a = apg;
		this.b = double2;
		this.c = boolean3;
		this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
	}

	@Override
	public boolean a() {
		long long2 = this.a.l.Q();
		if (long2 - this.k < 20L) {
			return false;
		} else {
			this.k = long2;
			aoy aoy4 = this.a.A();
			if (aoy4 == null) {
				return false;
			} else if (!aoy4.aU()) {
				return false;
			} else {
				this.d = this.a.x().a(aoy4, 0);
				return this.d != null ? true : this.a(aoy4) >= this.a.g(aoy4.cC(), aoy4.cD(), aoy4.cG());
			}
		}
	}

	@Override
	public boolean b() {
		aoy aoy2 = this.a.A();
		if (aoy2 == null) {
			return false;
		} else if (!aoy2.aU()) {
			return false;
		} else if (!this.c) {
			return !this.a.x().m();
		} else {
			return !this.a.a(aoy2.cA()) ? false : !(aoy2 instanceof bec) || !aoy2.a_() && !((bec)aoy2).b_();
		}
	}

	@Override
	public void c() {
		this.a.x().a(this.d, this.b);
		this.a.s(true);
		this.h = 0;
		this.i = 0;
	}

	@Override
	public void d() {
		aoy aoy2 = this.a.A();
		if (!aop.e.test(aoy2)) {
			this.a.i(null);
		}

		this.a.s(false);
		this.a.x().o();
	}

	@Override
	public void e() {
		aoy aoy2 = this.a.A();
		this.a.t().a(aoy2, 30.0F, 30.0F);
		double double3 = this.a.g(aoy2.cC(), aoy2.cD(), aoy2.cG());
		this.h = Math.max(this.h - 1, 0);
		if ((this.c || this.a.z().a(aoy2))
			&& this.h <= 0
			&& (this.e == 0.0 && this.f == 0.0 && this.g == 0.0 || aoy2.g(this.e, this.f, this.g) >= 1.0 || this.a.cX().nextFloat() < 0.05F)) {
			this.e = aoy2.cC();
			this.f = aoy2.cD();
			this.g = aoy2.cG();
			this.h = 4 + this.a.cX().nextInt(7);
			if (double3 > 1024.0) {
				this.h += 10;
			} else if (double3 > 256.0) {
				this.h += 5;
			}

			if (!this.a.x().a(aoy2, this.b)) {
				this.h += 15;
			}
		}

		this.i = Math.max(this.i - 1, 0);
		this.a(aoy2, double3);
	}

	protected void a(aoy aoy, double double2) {
		double double5 = this.a(aoy);
		if (double2 <= double5 && this.i <= 0) {
			this.g();
			this.a.a(anf.MAIN_HAND);
			this.a.B(aoy);
		}
	}

	protected void g() {
		this.i = 20;
	}

	protected boolean h() {
		return this.i <= 0;
	}

	protected int j() {
		return this.i;
	}

	protected int k() {
		return 20;
	}

	protected double a(aoy aoy) {
		return (double)(this.a.cx() * 2.0F * this.a.cx() * 2.0F + aoy.cx());
	}
}
