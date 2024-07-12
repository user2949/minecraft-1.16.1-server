import java.util.EnumSet;

public class aum extends aug {
	private final aoz a;
	private aoy b;
	private final float c;

	public aum(aoz aoz, float float2) {
		this.a = aoz;
		this.c = float2;
		this.a(EnumSet.of(aug.a.JUMP, aug.a.MOVE));
	}

	@Override
	public boolean a() {
		if (this.a.bo()) {
			return false;
		} else {
			this.b = this.a.A();
			if (this.b == null) {
				return false;
			} else {
				double double2 = this.a.h(this.b);
				if (double2 < 4.0 || double2 > 16.0) {
					return false;
				} else {
					return !this.a.aj() ? false : this.a.cX().nextInt(5) == 0;
				}
			}
		}
	}

	@Override
	public boolean b() {
		return !this.a.aj();
	}

	@Override
	public void c() {
		dem dem2 = this.a.cB();
		dem dem3 = new dem(this.b.cC() - this.a.cC(), 0.0, this.b.cG() - this.a.cG());
		if (dem3.g() > 1.0E-7) {
			dem3 = dem3.d().a(0.4).e(dem2.a(0.2));
		}

		this.a.m(dem3.b, (double)this.c, dem3.d);
	}
}
