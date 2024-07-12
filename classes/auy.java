import java.util.EnumSet;

public class auy extends aug {
	private final bpg a;
	private final aoz b;
	private aoy c;
	private int d;

	public auy(aoz aoz) {
		this.b = aoz;
		this.a = aoz.l;
		this.a(EnumSet.of(aug.a.MOVE, aug.a.LOOK));
	}

	@Override
	public boolean a() {
		aoy aoy2 = this.b.A();
		if (aoy2 == null) {
			return false;
		} else {
			this.c = aoy2;
			return true;
		}
	}

	@Override
	public boolean b() {
		if (!this.c.aU()) {
			return false;
		} else {
			return this.b.h(this.c) > 225.0 ? false : !this.b.x().m() || this.a();
		}
	}

	@Override
	public void d() {
		this.c = null;
		this.b.x().o();
	}

	@Override
	public void e() {
		this.b.t().a(this.c, 30.0F, 30.0F);
		double double2 = (double)(this.b.cx() * 2.0F * this.b.cx() * 2.0F);
		double double4 = this.b.g(this.c.cC(), this.c.cD(), this.c.cG());
		double double6 = 0.8;
		if (double4 > double2 && double4 < 16.0) {
			double6 = 1.33;
		} else if (double4 < 225.0) {
			double6 = 0.6;
		}

		this.b.x().a(this.c, double6);
		this.d = Math.max(this.d - 1, 0);
		if (!(double4 > double2)) {
			if (this.d <= 0) {
				this.d = 20;
				this.b.B(this.c);
			}
		}
	}
}
