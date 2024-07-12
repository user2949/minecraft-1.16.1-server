import java.util.EnumSet;

public class auw extends aug {
	private final apg a;
	private aoy b;
	private double c;
	private double d;
	private double e;
	private final double f;
	private final float g;

	public auw(apg apg, double double2, float float3) {
		this.a = apg;
		this.f = double2;
		this.g = float3;
		this.a(EnumSet.of(aug.a.MOVE));
	}

	@Override
	public boolean a() {
		this.b = this.a.A();
		if (this.b == null) {
			return false;
		} else if (this.b.h(this.a) > (double)(this.g * this.g)) {
			return false;
		} else {
			dem dem2 = axu.b(this.a, 16, 7, this.b.cz());
			if (dem2 == null) {
				return false;
			} else {
				this.c = dem2.b;
				this.d = dem2.c;
				this.e = dem2.d;
				return true;
			}
		}
	}

	@Override
	public boolean b() {
		return !this.a.x().m() && this.b.aU() && this.b.h(this.a) < (double)(this.g * this.g);
	}

	@Override
	public void d() {
		this.b = null;
	}

	@Override
	public void c() {
		this.a.x().a(this.c, this.d, this.e, this.f);
	}
}
